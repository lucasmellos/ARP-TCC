
/*
	RoboCore XBee API Library
		(v1.4 - 07/08/2013)

  Library to use the XBEE in API mode
    (tested with Arduino 0022, 0023 and 1.0.1)

  Copyright 2013 RoboCore (François) ( http://www.RoboCore.net )
  
  ------------------------------------------------------------------------------
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  ------------------------------------------------------------------------------
  
  
  NOTE: uses the Pointer List in XBeeMaster::Listen()
        (but can be changed by undefining
        USE_POINTER_LIST in <Memory.h>)
  
  NOTE: this library can work with the SoftwareSerial
        library in Arduino versions 0022 and 0023, but
        is disabled by default.

  NOTE: the API operation of the master isn't set to
	use escape characters, because at this moment
	none of the escape characters will be sent to
	the slave (messagens include addresses and
	set/unset IO, so uses just numbers and letters)

  NOTES for versions:
	. Configure functions are general, they only change
	  the network ID, Channel and Baudrate. The only
	  difference is that Slaves are in AT mode whereas
	  Masters are in API mode. This means that version,
	  both for Slaves and Masters, do not apply to these
	  functions.
	. Version specific code must be implemented in the
	  parent code.
*/


//*****************************************************************************************************************************
//*****************************************************************************************************************************
// during testing with the Sparkfun XBee Shield, use pins (2,3) for comunication //or (6,7) if also using Ethernet shield
// during testing with the RoboCore XBee Master Shield, use pins Serial1 (19,18)
//*****************************************************************************************************************************
//*****************************************************************************************************************************


#include "XBee_API.h"

#define XBEE_API_DEBUG

//user defined constants
#define BAUDRATE_PC 9600
#define BAUDRATE_XBEE 19200 //1200 (0), 2400 (1), 4800 (2), 9600 (3), 19200 (4), 38400 (5), 57600 (6), 115200 (7)

//other constants
#define AT_TIMEOUT 11000

#define EMPTY_CHAR '#'
#define CONTROL_CHAR '#'



//-------------------------------------------------------------------------------------------------

// Constructor - default
//   NOTE: NOT to be used, because does not set the XBee
XBeeMaster::XBeeMaster(void){
  _initialized = false; //set to false to call Initialize method
  _use_computer = false;
  _xbee = NULL; // BLOCKS the use of the object in Initialize()
  _network_id = NETWORK_ID;
  _network_channel = NETWORK_CHANNEL;
}

#ifdef USE_SOFTWARE_SERIAL
// Constructor for SoftwareSerial
XBeeMaster::XBeeMaster(SoftwareSerial* xbee){
  _initialized = false; //set to false to call Initialize method
  _use_computer = false;
  _xbee = xbee;
  _network_id = NETWORK_ID;
  _network_channel = NETWORK_CHANNEL;
}
#else
// Constructor for HardwareSerial
XBeeMaster::XBeeMaster(HardwareSerial* xbee){
  _initialized = false; //set to false to call Initialize method
  _use_computer = false;
  _xbee = xbee;
  _network_id = NETWORK_ID;
  _network_channel = NETWORK_CHANNEL;
}
#endif

//-------------------------------------------------------------------------------------------------

// Destructor
XBeeMaster::~XBeeMaster(void){
  Destroy();
}

//-------------------------------------------------------------------------------------------------

// Assign a Byte Array to the XBee Master
boolean XBeeMaster::AssignByteArray(ByteArray* barray){
  if(!_initialized)
    return false;
  
  _is_SerialNumber = false; //reset because assigning new values
  
  _barray.ptr = barray->ptr;
  _barray.length = barray->length;
  
  //point no NULL to prevent further modifications
      //DO NOT use FreeByteArray, because don't want to free the data, only reassign
  barray->ptr = NULL;
  barray->length = 0;
  delay(10); //give time to apply changes
  return true;
}
    
//-------------------------------------------------------------------------------------------------

// Calculates the CheckSum of the message
//    NOTE: the result is in BYTE
byte XBeeMaster::CheckSum(ByteArray* barray_ptr){
  long checksum = 0;
  
  for(int i=0 ; i < barray_ptr->length ; i++)
    checksum += barray_ptr->ptr[i];
  
  checksum &= 0xFF;
  
  return (0xFF - checksum);
}

//-------------------------------------------------------------------------------------------------

// Configure current XBee as Master (API mode)
//    (returns 1 when succesful, 0 if not initialized, 13 if timeout of '+++', 14 if other timeout, 23 if number of tries exeeded, 33 if invalid user Baudrate)
//    NOTE: if an error as occured, the previous transmission cannot be used, so must restart the transmission with a valid baudrate
byte XBeeMaster::ConfigureAsMaster(long baudrate){
  return ConfigureXBee(baudrate, true);
}

//-------------------------------------------------------------------------------------------------

// Configure current XBee as Slave (AT mode)
//    (returns 1 when succesful, 0 if not initialized, 13 if timeout of '+++', 14 if other timeout, 23 if number of tries exeeded, 33 if invalid user Baudrate)
//    NOTE: if an error as occured, the previous transmission cannot be used, so must restart the transmission with a valid baudrate
byte XBeeMaster::ConfigureAsSlave(long baudrate){
  return ConfigureXBee(baudrate, false);
}

//-------------------------------------------------------------------------------------------------

// Configure current XBee as Master (API mode)
//    (returns 1 when succesful, 0 if not initialized, 13 if timeout of '+++', 14 if other timeout, 23 if number of tries exeeded, 33 if invalid user Baudrate)
//    NOTE: if an error as occured, the previous transmission cannot be used, so must restart the transmission with a valid baudrate
byte XBeeMaster::ConfigureXBee(long baudrate, boolean master){
  if(!_initialized)
    return 0;
  
  // Procedure:
  //    1) enter command mode
  //    2) set the network ID
  //    3) set the network Channel
  //    4) set the 16-bit address (slave only)
  //    5) set the Baudrate
  //    6) set de API mode
  //    7) write changes
  //    8.1) read SH
  //    8.2) read SL
  //        OBS: address is stored in ByteArray, must read it BEFORE calling other function (might change data in the Byte Array)
  //    9) exit command mode
  
  _xbee->end(); //end previous connection
  _xbee->begin(baudrate); //begin connection
  
#define BUFFER_SIZE 15
  char c[BUFFER_SIZE]; //add more than 3 to be sure
  byte count;
  unsigned long start_time, current_time;
  byte tries;
  char* serial_number = "################"; //16 characters
  //-----------------------------------
  // 1) ENTER COMMAND MODE
  tries = 0;
resend_EnterAT:
  //check number of tries
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("+++");
  tries++;
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 13;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> +++");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_EnterAT;
  //-----------------------------------
  // 2) SET NETWORK ID
  tries = 0;
resend_ID:
  //check number of tries
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("ATID");
  _xbee->write(ASCIIByteToHexByte((_network_id & 0xF000) >> 12));
  _xbee->write(ASCIIByteToHexByte((_network_id & 0x0F00) >> 8));
  _xbee->write(ASCIIByteToHexByte((_network_id & 0x00F0) >> 4));
  _xbee->write(ASCIIByteToHexByte(_network_id & 0x000F));
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATID");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_ID;
  //-----------------------------------
  // 3) SET NETWORK CHANNEL
  tries = 0;
resend_CH:
  //check number of tries
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("ATCH");
  _xbee->write(ASCIIByteToHexByte((_network_channel & 0x00F0) >> 4));
  _xbee->write(ASCIIByteToHexByte(_network_channel & 0x000F));
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATCH");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_CH;
  //-----------------------------------
  // 4) SET 16-bit ADDRESS
  if(!master){
    tries = 0;
resend_MY:
    //check number of tries
    if(tries > 3)
      return 23;
    //reset buffer
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      c[i] = EMPTY_CHAR;
    _xbee->write("ATMY");
    _xbee->write("FFFF"); // 0xFFFF
    _xbee->write(0x0D); //carriage return
    tries++; //add
    //read response - 'OK\0'
    count = 0;
    start_time = millis(); //get the current time
    current_time = start_time;
    while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
      if(_xbee->available()){
        c[count] = _xbee->read();
        count++;
      }
      current_time = millis();
    }
    //check if timeout
    if((current_time - start_time) >= AT_TIMEOUT)
      return 14;
#ifdef XBEE_API_DEBUG
    //display on computer
    if(_use_computer){
      _computer->println(">> ATMY");
      for(int i=0 ; i < BUFFER_SIZE ; i++)
        _computer->print(c[i]);
      _computer->println();
    }
#endif
    if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
      goto resend_MY;
  }
  //-----------------------------------
  // 5) SET NETWORK BAUDRATE
  byte bd;
  switch(BAUDRATE_XBEE){
    case 1200: bd = 0; break;
    case 2400: bd = 1; break;
    case 4800: bd = 2; break;
    case 9600: bd = 3; break;
    case 19200: bd = 4; break;
    case 38400: bd = 5; break;
    case 57600: bd = 6; break;
    case 115200: bd = 7; break;
    default: bd = 13;
  }
  //check if valid baudrate
  if(bd == 13)
    return 33;
  //continue
  tries = 0;
resend_BD:
  //check number of tries
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("ATBD");
  _xbee->write(ASCIIByteToHexByte(bd));
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATBD");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_BD;
  //-----------------------------------
  // 6) SET API MODE
  tries = 0;
resend_AP:
  //check number of tries
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  if(master)
    _xbee->write("ATAP1"); //mode 1 (mode 2 not yet implemented in XBeeMessages - see README)
  else
    _xbee->write("ATAP0");
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATPn");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_AP;
  //-----------------------------------
  // 7) WRITE CHANGES TO XBEE
  tries = 0;
resend_WR:
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("ATWR");
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATWR");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_WR;
  //-----------------------------------
  // 8.1) READ SH
  tries = 0;
resend_SH:
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("ATSH");
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 9) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++; //careful for next commands!
    }
    current_time = millis();
    //exit if found carriage return before end (meaning first bytes are 0)
    if(c[count - 1] == 0x0D){
      //ignore next characters
      while(_xbee->available()){
        _xbee->read();
      }
      break;
    }
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATSH");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if(c[count - 1] != 0x0D) //invalid response
    goto resend_SH;
  else { //store in serial_number MSB
    byte leading_zeros = 9 - count;
    for(int i=0 ; i < leading_zeros ; i++)
      serial_number[i] = '0';
    for(int i=leading_zeros ; i < 8 ; i++)
      serial_number[i] = c[i - leading_zeros];
  }
  //-----------------------------------
  // 8.2) READ SL
  tries = 0;
resend_SL:
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("ATSL");
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 9) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++; //careful for next commands!
    }
    current_time = millis();
    //exit if found carriage return before end (meaning first bytes are 0)
    if(c[count - 1] == 0x0D){
      //ignore next characters
      while(_xbee->available()){
        _xbee->read();
      }
      break;
    }
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATSL");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if(c[count - 1] != 0x0D) //invalid response
    goto resend_SL;
  else { //store in serial_number LSB (see offset of 8)
    byte leading_zeros = 9 - count;
    for(int i=0 ; i < leading_zeros ; i++)
      serial_number[8 + i] = '0';
    for(int i=leading_zeros ; i < 8 ; i++)
      serial_number[8 + i] = c[i - leading_zeros];
  }
  //-----------------------------------
  // 9) EXIT COMMAND MODE
  _xbee->write("ATCN"); //leave command mode - doesn't need to verify 'ok' back, leaves with timeout
  _xbee->write(0x0D); //carriage return
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer)
    _computer->println(">> ATCN");
#endif
#undef BUFFER_SIZE
  //-----------------------------------
  // return
  delay(10);
  _xbee->flush();
  _xbee->end();
  _xbee->begin(BAUDRATE_XBEE); //start new connection
  
  //store in ByteArray
  HexStringToByteArray(serial_number, &_barray);
  _is_SerialNumber = true; //set
  
  return 1;
}

//-------------------------------------------------------------------------------------------------

// Configure the pins
//    (returns 1 when succesful, 0 if not initialized, 13 if timeout of '+++', 14 if other timeout,
//      23 if number of tries exeeded, 30 if invalid number of pins
//    NOTE: if an error as occured, the previous transmission cannot be used, so must restart the transmission with a valid baudrate
byte XBeeMaster::ConfigurePins(XBeePin *pins, byte num_pins){
  if(!_initialized)
    return 0;
  
  //check number of pins
  if((num_pins == 0) || (num_pins > 9))
    return 30;
  
  // Procedure:
  //    1) enter command mode
  //    2) configure
  //    3) write changes (if aplicable)
  //    4) exit command mode
  
  boolean wr = false; //TRUE if has to write the changes
  
#define BUFFER_SIZE 15
  char c[BUFFER_SIZE]; //add more than 3 to be sure
  byte count;
  unsigned long start_time, current_time;
  byte tries;
  //-----------------------------------
  // 1) ENTER COMMAND MODE
  tries = 0;
resend_EnterAT:
  //check number of tries
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("+++");
  tries++;
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 13;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> +++");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_EnterAT;
  //-----------------------------------
  // 2) CONFIGURE
  for(int current=0 ; current < num_pins ; current++){
    tries = 0;
resend_Dn:
    //check number of tries
    if(tries > 3)
      return 23;
    //reset buffer
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      c[i] = EMPTY_CHAR;
  
    _xbee->write('A');
    _xbee->write('T');
    _xbee->write(pins[current].pin[0]);
    _xbee->write(pins[current].pin[1]);
    _xbee->write(pins[current].value + 48); //use '0' instead of 0
    
    _xbee->write(0x0D); //carriage return
    tries++; //add
    //read response - 'OK\0'
    count = 0;
    start_time = millis(); //get the current time
    current_time = start_time;
    while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
      if(_xbee->available()){
        c[count] = _xbee->read();
        count++;
      }
      current_time = millis();
    }
    //check if timeout
    if((current_time - start_time) >= AT_TIMEOUT)
      return 14;
#ifdef XBEE_API_DEBUG
    //display on computer
    if(_use_computer){
      _computer->print(">> AT");
      _computer->println(pins[current].pin);
      for(int i=0 ; i < BUFFER_SIZE ; i++)
        _computer->print(c[i]);
      _computer->println();
    }
#endif
    if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
      goto resend_Dn;
    
    wr = true; //one pin has been configured successfully
  }
  //-----------------------------------
  // 3) WRITE CHANGES TO XBEE
  if(wr){
    tries = 0;
resend_WR:
    if(tries > 3)
      return 23;
    //reset buffer
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      c[i] = EMPTY_CHAR;
    _xbee->write("ATWR");
    _xbee->write(0x0D); //carriage return
    tries++; //add
    //read response - 'OK\0'
    count = 0;
    start_time = millis(); //get the current time
    current_time = start_time;
    while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
      if(_xbee->available()){
        c[count] = _xbee->read();
        count++;
      }
      current_time = millis();
    }
    //check if timeout
    if((current_time - start_time) >= AT_TIMEOUT)
      return 14;
#ifdef XBEE_API_DEBUG
    //display on computer
    if(_use_computer){
      _computer->println(">> ATWR");
      for(int i=0 ; i < BUFFER_SIZE ; i++)
        _computer->print(c[i]);
      _computer->println();
    }
#endif
    if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
      goto resend_WR;
  }
  //-----------------------------------
  // 4) EXIT COMMAND MODE
  _xbee->write("ATCN"); //leave command mode - doesn't need to verify 'ok' back, leaves with timeout
  _xbee->write(0x0D); //carriage return
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer)
    _computer->println(">> ATCN");
#endif
#undef BUFFER_SIZE
  //-----------------------------------
  
  return 1;
}

//-------------------------------------------------------------------------------------------------

// Create the message
boolean XBeeMaster::CreateFrame(char* message, boolean is_hex){
  if(!_initialized)
    return false;
  
  //convert message to Byte Array
  ByteArray temp;
  InitializeByteArray(&temp);
  if(is_hex)
    HexStringToByteArray(message, &temp);
  else
    StringToByteArray(message, &temp);
  
  //calculate the check sum
  byte check_sum = CheckSum(&temp);
  
  //free Byte Array if contains the Serial Number
  if(_is_SerialNumber)
    FreeByteArray(&_barray);
  
  //store in own Byte Array
  ResizeByteArray(&_barray, 3);
  JoinByteArray(&_barray, &temp); //add to own byte array
  FreeByteArray(&temp); //free memory
  
  //change message
  ResizeByteArray(&_barray, (_barray.length + 1)); //+1 to insert the check sum
  _barray.ptr[0] = FRAME_DELIMITER;
  _barray.ptr[1] = ((_barray.length - 4) & 0xFF00);
  _barray.ptr[2] = ((_barray.length - 4) & 0x00FF);
  _barray.ptr[_barray.length - 1] = check_sum;
  
  return true;
}

//------------------------------------------

// Create the message
boolean XBeeMaster::CreateFrame(ByteArray* message){
  if(!_initialized)
    return false;
  
  //calculate the check sum
  byte check_sum = CheckSum(message);
  
  //free Byte Array if contains the Serial Number
  if(_is_SerialNumber)
    FreeByteArray(&_barray);
  
  //store in own Byte Array
  ResizeByteArray(&_barray, 3);
  JoinByteArray(&_barray, message); //add to own byte array
  FreeByteArray(message); //free memory
  
  //change message
  ResizeByteArray(&_barray, (_barray.length + 1)); //+1 to insert the check sum
  _barray.ptr[0] = FRAME_DELIMITER;
  _barray.ptr[1] = ((_barray.length - 4) & 0xFF00);
  _barray.ptr[2] = ((_barray.length - 4) & 0x00FF);
  _barray.ptr[_barray.length - 1] = check_sum;
  
  return true;
}

//-------------------------------------------------------------------------------------------------

// Destroy the XBeeMaster
void XBeeMaster::Destroy(void){
  if(!_initialized)
    return;
  
  _computer->end(); //end communication
  _xbee->end(); //end communication
  
  FreeByteArray(&_barray);
  _is_SerialNumber = false; //reset
  _use_computer = false;
  _computer = NULL;
  _initialized = false; //reset
}

//-------------------------------------------------------------------------------------------------

// Get the network Channel
//  (returns 0 if not initialized)
byte XBeeMaster::GetNetworkChannel(void){
  if(!_initialized)
    return 0;
  
  return _network_channel;
}

//-------------------------------------------------------------------------------------------------

// Get the network ID
//  (returns 0 if not initialized)
word XBeeMaster::GetNetworkID(void){
  if(!_initialized)
    return 0;
  
  return _network_id;
}


//-------------------------------------------------------------------------------------------------

// Get the defined PC baudrate
long XBeeMaster::GetPCbaudrate(void){
  return BAUDRATE_PC;
}

// Get the defined XBee baudrate
long XBeeMaster::GetXBeebaudrate(void){
  return BAUDRATE_XBEE;
}

//-------------------------------------------------------------------------------------------------

// Get the serial number of the last configured XBee
char* XBeeMaster::GetSerialNumber(void){
  if(!_initialized)
    return "";
  
  //check if is a serial number
  if(!_is_SerialNumber)
    return "";
  
  char* res = ByteArrayToHexString(&_barray);
  FreeByteArray(&_barray); //to prevent misuse
  _is_SerialNumber = false; //reset
  
  return res;
}

//-------------------------------------------------------------------------------------------------

// Initialize the XBeeMaster
void XBeeMaster::Initialize(void){
  if(!_initialized && (_xbee != NULL)){ //must have a serial port assigned
    _xbee->begin(BAUDRATE_XBEE); //begin transmission
    InitializeByteArray(&_barray); //initialize Byte Array
    _is_SerialNumber = false; //set
    _initialized = true; //set
  }
}


// Initialize the XBeeMaster
void XBeeMaster::Initialize(HardwareSerial* computer){
  if(!_initialized && (_xbee != NULL)){ //must have a serial port assigned
    if((word)computer != (word)_xbee){ //assign only if not used by XBee (because both are pointers, cast to word to compare even for different types)
      _computer = computer;
      _computer->begin(BAUDRATE_PC);
      _use_computer = true;
    }
    _xbee->begin(BAUDRATE_XBEE); //begin transmission
    InitializeByteArray(&_barray); //initialize Byte Array
    _is_SerialNumber = false; //set
    _initialized = true; //set
  }
}

//-------------------------------------------------------------------------------------------------

// Listen the response of the XBee Slave
//   (returns -1 if not initialized, 1 on message listened,
//      10 on Timeout, 11 on buffer overflow, 12 if frame delimiter not found,
//      20 if invalid length,
//      30 if invalid checksum)
//     NOTE: if 'str' was created using malloc(), 'free_str' must be TRUE
int XBeeMaster::Listen(char** str, boolean free_str, unsigned long timeout, unsigned long pause_time){
  //NOTE: with char* the result isn't correctly stored, but with char** is

  if(!_initialized)
    return -1;

  // has to free the string, otherwise will consume memory
  // verify that: because Listen() uses ByteArrayToHexString(), which uses malloc(), the block/string
  //                      must be manually freed.
  if(free_str){
#ifdef USE_POINTER_LIST
    Mfree(*str);
#else
    free(*str);
#endif
  }

#ifdef USE_SOFTWARE_SERIAL
  _xbee->listen();
#endif
  
  //wait for response or timeout
  unsigned long start_time;
  start_time = millis();
  while(!_xbee->available() && (millis() < (start_time + timeout))){ /* wait */ }
  if(millis() >= (start_time + timeout))
    return 10; //should not enter here, because the XBee has its own timeout (API frame 0x97 + status 04)
  
  //insert a pause for the serial buffer fill completely
  if(pause_time != 0){
    start_time = millis();
    while(millis() <= (start_time + pause_time)){ /* wait */ }
  }

#define BUFFER_SIZE 150
  byte buffer[BUFFER_SIZE];
  
  //read from buffer
  int i = 0;
  unsigned int length = 0;
  while(_xbee->available() && (i < BUFFER_SIZE)){
    buffer[i] = (byte)_xbee->read();
    i++;
    //begin storage if have found start of frame
    if(buffer[0] != FRAME_DELIMITER){
      i=0;
    } else {
      if(i >= 2){
        length = (buffer[1] << 8) | buffer[2]; //MSB and LSB
        //exit loop if has exceeded the length of the frame
        if(i > (length + 3)) //(+3) for the frame header & (+1) for the CheckSum byte & (-1) because 0 based vector
          break;
      }
    }
  }
  //NOTE: i should be equal do (length + 4) because it is increased by 1 after reading the last byte (checksum)
  
  //parse message
  if(length >= BUFFER_SIZE - 3){ //message too long for buffer (100 - 3 bytes for Frame, Length_H and Length_L)
    return 11;
  } else if(i == 0){ //frame delimiter not found
    return 12;
  } else if(length != (i-4)){ //invalid length - see previous note
    return 20;
  }
  
  //use Byte Array because a NULL character (value of 0) returns an invalid string
  ByteArray temp;
  InitializeByteArray(&temp);
  ResizeByteArray(&temp, length);
  for(int i=0 ; i < length ; i++)
    temp.ptr[i] = buffer[3+i];
#undef BUFFER_SIZE
  
  int res = 30;
  if(CheckSum(&temp) == buffer[length + 3]){ //(+3) for the frame header & (+1) for the CheckSum byte & (-1) because 0 based vector
    *str = ByteArrayToHexString(&temp);
    res = 1;
  }

  FreeByteArray(&temp);
  
  return res;
}

//-------------------------------------------------------------------------------------------------

// Restore the XBee's parameters to their factory settings
//    (returns 1 when succesful, 0 if not initialized, 13 if timeout of '+++', 14 if other timeout, 23 if number of tries exeeded)
//    NOTE: assumes that the XBee is currently configured with BAUDRATE_XBEE
//    NOTE: if an error as occured, the previous transmission cannot be used, so must restart the transmission with a valid baudrate
byte XBeeMaster::Restore(void){
  return Restore(BAUDRATE_XBEE);
}
  

// Restore the XBee's parameters to their factory settings given a baudrate
//    (returns 1 when succesful, 0 if not initialized, 13 if timeout of '+++', 14 if other timeout, 23 if number of tries exeeded)
//    NOTE: if an error as occured, the previous transmission cannot be used, so must restart the transmission with a valid baudrate
byte XBeeMaster::Restore(long baudrate){
  if(!_initialized)
    return 0;
  
  // Procedure:
  //    1) enter command mode
  //    2) restore defaults
  //    3) write changes
  //    4) exit command mode
  
  _xbee->end(); //end previous connection
  _xbee->begin(baudrate); //begin connection
  
#define BUFFER_SIZE 15
  char c[BUFFER_SIZE]; //add more than 3 to be sure
  byte count;
  unsigned long start_time, current_time;
  byte tries;
  char* serial_number = "################"; //16 characters
  //-----------------------------------
  // 1) ENTER COMMAND MODE
  tries = 0;
resend_EnterAT:
  //check number of tries
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("+++");
  tries++;
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 13;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> +++");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_EnterAT;
  //-----------------------------------
  // 2) RESTORE DEFAULTS
  tries = 0;
resend_RE:
  //check number of tries
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("ATRE");
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATRE");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_RE;
  //-----------------------------------
  // 3) WRITE CHANGES TO XBEE
  tries = 0;
resend_WR:
  if(tries > 3)
    return 23;
  //reset buffer
  for(int i=0 ; i < BUFFER_SIZE ; i++)
    c[i] = EMPTY_CHAR;
  _xbee->write("ATWR");
  _xbee->write(0x0D); //carriage return
  tries++; //add
  //read response - 'OK\0'
  count = 0;
  start_time = millis(); //get the current time
  current_time = start_time;
  while((count < 3) && ((current_time - start_time) < AT_TIMEOUT)){
    if(_xbee->available()){
      c[count] = _xbee->read();
      count++;
    }
    current_time = millis();
  }
  //check if timeout
  if((current_time - start_time) >= AT_TIMEOUT)
    return 14;
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer){
    _computer->println(">> ATWR");
    for(int i=0 ; i < BUFFER_SIZE ; i++)
      _computer->print(c[i]);
    _computer->println();
  }
#endif
  if((c[0] != 0x4F) || (c[1] != 0x4B) || (c[2] != 0x0D)) //not 'OK' resend
    goto resend_WR;
  //-----------------------------------
  // 4) EXIT COMMAND MODE
  _xbee->write("ATCN"); //leave command mode - doesn't need to verify 'ok' back, leaves with timeout
  _xbee->write(0x0D); //carriage return
#ifdef XBEE_API_DEBUG
  //display on computer
  if(_use_computer)
    _computer->println(">> ATCN");
#endif
#undef BUFFER_SIZE
  //-----------------------------------
  // return
  delay(10);
  _xbee->flush();
  _xbee->end();
  _xbee->begin(9600); //start new connection with default value
  
  return 1;
}

//-------------------------------------------------------------------------------------------------

// Send the message
boolean XBeeMaster::Send(void){
  if(!_initialized)
    return false;
  
  if(_barray.length <= 0)
    return false;
  
  //send data
  for(int i=0 ; i < _barray.length ; i++){
    _xbee->write(_barray.ptr[i]);
  }
  delay(10);
  
  FreeByteArray(&_barray); //free memory
  
  return true;
}

//-------------------------------------------------------------------------------------------------

// Set the computer serial
boolean XBeeMaster::SetComputer(HardwareSerial* computer){
  boolean res = false;
  if(_initialized){
    if((word)computer != (word)_xbee){ //assign only if not used by XBee (because both are pointers, cast to word to compare even for different types)
      _computer = computer;
      _computer->begin(BAUDRATE_PC);
      _use_computer = true;
      res = true;
    }
  }
  return res;
}

//-------------------------------------------------------------------------------------------------

// Set the XBee network Channel variable
//  (returns TRUE if changed successfully)
//    NOTE: range is defined for both XBee and XBee PRO
boolean XBeeMaster::SetNetworkChannel(byte channel){
  boolean res = false;
  if(_initialized){
    if((channel >= 0x0C) && (channel <= 0x17)){ //XBee: 0x0B to 0x1A || XBee PRO: 0x0C to 0x17
      _network_channel = channel;
      res = true;
    }
  }
  
  return res;
}

//-------------------------------------------------------------------------------------------------

// Set the XBee network ID variable
//  (returns FALSE if not initialized)
boolean XBeeMaster::SetNetworkID(word id){
  if(!_initialized)
    return false;
  
  _network_id = id;
  return true;
}

//-------------------------------------------------------------------------------------------------

// Unset the computer serial
//  (returns FALSE if not initialized)
boolean XBeeMaster::UnsetComputer(void){
  if(!_initialized)
    return false;
  
  _computer->end(); //end communication
  _computer = NULL;
  _use_computer = false; //reset
  return true;
}

//-------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------

// Create message to send a remote AT command
//    (returns the string to pass to the XBee)
//    NOTE: if the 16bit_address is invalid or the destination address, the mode is overridden to BROADCAST
//  !!! ALL strings in HEX format, EXCEPT for 'command_name'
boolean XBeeMessages::CreateRemoteATRequest(ByteArray* barray_ptr, char* destination_address_64bit, char* destination_address_16bit, byte transmission_type, char* command_name, char* command_values){
  //free if exists
  if(barray_ptr->ptr != NULL)
    FreeByteArray(barray_ptr);
  
  //check if valid command
  if(StrLength(command_name) != 2) //not hex string
    return false;
  
  //check if there are values
  if(StrLength(command_values) <= 0)
    return false;
  
  //resize and store constant values
  ResizeByteArray(barray_ptr, 15);
  barray_ptr->ptr[0] = API_REMOTE_AT_COMMAND_REQUEST;
  barray_ptr->ptr[1] = 0x05; //change here to have a response
  
  //store addresses
  ByteArray temp64;
  InitializeByteArray(&temp64);
  HexStringToByteArray(destination_address_64bit, &temp64); //convert data
  if(temp64.length == 8){
    for(int i=0 ; i < 8 ; i++)
      barray_ptr->ptr[2+i] = temp64.ptr[i];
    switch(transmission_type){
      case USE_64_BIT_ADDRESS:
                barray_ptr->ptr[10] = 0xFF;
                barray_ptr->ptr[11] = 0xFE;
                break;
      case USE_16_BIT_ADDRESS:
                ByteArray temp16;
                InitializeByteArray(&temp16);
                HexStringToByteArray(destination_address_16bit, &temp16); //convert data
                if(temp16.length == 2){
                  barray_ptr->ptr[10] = temp16.ptr[0];
                  barray_ptr->ptr[11] = temp16.ptr[1];
                } else { //broadcast
                  barray_ptr->ptr[10] = 0xFF;
                  barray_ptr->ptr[11] = 0xFF;
                }
                FreeByteArray(&temp16);
                break;
      default: //broadcast
                barray_ptr->ptr[10] = 0xFF;
                barray_ptr->ptr[11] = 0xFF;
                break;
    }
  } else {
    for(int i=0 ; i < 8 ; i++)
      barray_ptr->ptr[2+i] = 0;
    barray_ptr->ptr[10] = 0xFF; //broadcast
    barray_ptr->ptr[11] = 0xFF; //broadcast
  }
  FreeByteArray(&temp64);
  
  barray_ptr->ptr[12] = 0x02; //apply changes
  
  //store command
  ByteArray temp_command;
  InitializeByteArray(&temp_command);
  StringToByteArray(command_name, &temp_command); //convert data (NOT HEX string)
  barray_ptr->ptr[13] = temp_command.ptr[0];
  barray_ptr->ptr[14] = temp_command.ptr[1];
  FreeByteArray(&temp_command);

  //add values
  HexStringToByteArray(command_values, &temp_command);
  JoinByteArray(barray_ptr, &temp_command);
  FreeByteArray(&temp_command);
  
  return true;
}

//-------------------------------------------------------------------------------------------------

// Implemented (1):
//    - API_REMOTE_AR_COMMAND_REQUEST (doesn't validade response data)

// Validate the response of a given message
//  (returns 1 if OK, 10 if error, 40 if no response)
byte XBeeMessages::ResponseStatus(byte sent_message_type, char* response){
  byte res = 0;
  char* temp = "##";
  
  switch(sent_message_type){
    case API_REMOTE_AT_COMMAND_REQUEST: {
      //check if correct response identifier
      temp[0] = response[0];
      temp[1] = response[1];
      if(HexCharToByte(temp) != API_REMOTE_COMMAND_RESPONSE)
        break;
      //check response
      temp[0] = response[28];
      temp[1] = response[29];
      //check the status
      byte status = HexCharToByte(temp);
      switch(status){
        case 0: res = 1;  break;
        case 1: res = 10; break;
        case 4: res = 40; break;
      }
      break;
    }
    
    default:
#ifdef XBEE_API_DEBUG
      Serial.println("ERROR in ResponseStatus: type not yet implemented!");
#endif
      break; //must have for when XBEE_API_DEBUG ISN'T defined
  }
  
  return res;
}


// Validate the response of a given message
//  (returns 1 if OK, 10 if error, 40 if no response)
byte XBeeMessages::ResponseStatus(byte sent_message_type, ByteArray* barray){
  byte res = 0;
  
  switch(sent_message_type){
    case API_REMOTE_AT_COMMAND_REQUEST:
      //check length
      if(barray->length != 15)
        break;
      //check if correct response identifier
      if(barray->ptr[0] != API_REMOTE_COMMAND_RESPONSE)
        break;
      //check response
      switch(barray->ptr[14]){
        case 0: res = 1;  break;
        case 1: res = 10; break;
        case 4: res = 40; break;
      }
      break;
    
    default:
#ifdef XBEE_API_DEBUG
      Serial.println("ERROR in ResponseStatus: type not yet implemented!");
#endif
      break; //must have for when XBEE_API_DEBUG ISN'T defined
  }
  
  return res;
}

//-------------------------------------------------------------------------------------------------



