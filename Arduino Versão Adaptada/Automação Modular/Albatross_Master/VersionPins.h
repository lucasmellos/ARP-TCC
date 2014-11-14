#ifndef RC_AMS_VERSION_PINS_H
#define RC_AMS_VERSION_PINS_H

/*
	RoboCore Albatross Master Shield Pins
                     (v1.0 - 24/05/2013)

  Pins of the Albatross Master Shield from RoboCore ( http://www.RoboCore.net )

  Copyright 2013 RoboCore (François) ( http://www.RoboCore.net )
  
  ------------------------------------------------------------------------------
  This file is part of Albatross Master.

  Albatross Master is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  Albatross Master is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with Albatross Master.  If not, see <http://www.gnu.org/licenses/>.
  ------------------------------------------------------------------------------
  
  Definition of the custom pins for each version of the Master Shield
  
  IMPORTANT: One must know what to do before changing the values for the pins
  
*/


#define AMS_VERSION_MAIN 1 //current version being used
#define AMS_VERSION_SUB 0 //current version being used


#if (AMS_VERSION_MAIN == 1) && (AMS_VERSION_SUB == 0) // for version 1.0
  #define AMS_SS_HARDWARE  53 // 10 on UNO
  
  #define AMS_SS_ETHERNET  10 // Ethernet Shield
  #define AMS_SS_SD         4 // Ethernet Shield

  #define AMS_XBEE_SERIAL  &Serial1
  #define AMS_IR_RECEIVER  48
  #define AMS_LED_RED      43
  #define AMS_LED_BLUE     45
#endif


#endif //RC_AMS_VERSION_PINS_H




