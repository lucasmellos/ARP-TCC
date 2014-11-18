#ifndef XBEE_API_AT_COMMANDS_H
#define XBEE_API_AT_COMMANDS_H

/*
	RoboCore XBee API Library - AT Commands
		(v1.0 - 30/07/2013)

  Library to use the AT Commands of the XBEE in API mode

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
  
*/


//use with StringToByteArray()

#define A1 "A1" // End Device Association
#define A2 "A2" // Coordinator Association
#define AC "AC" // Apply Changes
#define AI "AI" // Association Indication
#define AP "AP" // API Enable
#define AS "AS" // Active Scan
#define BD "BD" // Interface Data Rate
#define CA "CA" // CCA Threshold
#define CC "CC" // Command Sequence Character
#define CE "CE" // Coordinator Enable
#define CH "CH" // Channel
#define CN "CN" // Exit Command Mode
#define CT "CT" // Command Mode Timeout
#define D0 "D0" // DIOn Configuration
#define D1 "D1" // ""
#define D2 "D2" // ""
#define D3 "D3" // ""
#define D4 "D4" // ""
#define D5 "D5" // DIO5 Configuration
#define D6 "D6" // DIO6 Configuration
#define D7 "D7" // DIO7 Configuration
#define D8 "D8" // DI8 Configuration
#define DA "DA" // Force Disassociation
#define DB "DB" // Received Signal Strength
#define DH "DH" // Destination Address HIGH
#define DL "DL" // Destination Address LOW
#define DN "DN" // Destination Node
#define DP "DP" // Disassociation Cyclic Sleep Period
#define EA "EA" // ACK Failures
#define EC "EC" // CCA Failures
#define ED "ED" // Energy Scan
#define EE "EE" // AES Encryption Enable
#define FP "FP" // Force Poll
#define FR "FR" // Software Reset
#define GT "GT" // Guard Times
#define HV "HV" // Hardware Version
#define IA "IA" // I/O Input Address
#define IC "IC" // DIO Change Detect
#define ID "ID" // Pand ID
#define IO "IO" // Digital Output Level
#define IR "IR" // Sample Rate
#define IS "IS" // Force Sample
#define IT "IT" // Samples before TX
#define IU "IU" // I/O Output Enable
#define KY "KY" // AES Encryption Key
#define M0 "M0" // PWM0 Output Level
#define M1 "M1" // PWM1 Output Level
#define MM "MM" // MAC Mode
#define MY "MY" // 16-bit Source Address
#define NB "NB" // Parity
#define ND "ND" // Node Discover
#define NI "NI" // Node Identifier
#define NO "NO" // Node Discover Options
#define NT "NT" // Node Discover Timer
#define P0 "P0" // PWM0 Configuration
#define P1 "P1" // PWM1 Configuration
#define PL "PL" // Power Level
#define PR "PR" // Pull-up Resistor
#define PT "PT" // PWM Output Timeout
#define RE "RE" // Restore Defaults
#define RN "RN" // Random Delay Slots
#define RO "RO" // Packetization Timeout
#define RP "RP" // RSSI PWM Timer
#define RR "RR" // XBee Retries
#define SC "SC" // Scan Channels
#define SD "SD" // Scan Duration
#define SH "SH" // Serial Number HIGH
#define SL "SL" // Serial Number LOW
#define SM "SM" // Sleep Mode
#define SO "SO" // Sleep Mode Command
#define SP "SP" // Cyclic Sleep Period
#define ST "ST" // Time before Sleep
#define T0 "T0" // D0-D7 Output Timeout
#define T1 "T1" // ""
#define T2 "T2" // ""
#define T3 "T3" // ""
#define T4 "T4" // ""
#define T5 "T5" // ""
#define T6 "T6" // ""
#define T7 "T7" // ""
#define VL "VL" // Firmware Version - Verbose
#define VR "VR" // Firmware Version
#define WR "WR" // Write



#define XBEE_PIN_DISABLED 0
#define XBEE_PIN_ADC      2
#define XBEE_PIN_DI       3
#define XBEE_PIN_DO_LOW   4
#define XBEE_PIN_DO_HIGH  5


#endif // XBEE_API_AT_COMMANDS_H
