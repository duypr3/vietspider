/* -*- Mode: C; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 2 -*-
 *
 * The contents of this file are subject to the Netscape Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/NPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * The Original Code is mozilla.org code.
 *
 * The Initial Developer of the Original Code is Netscape
 * Communications Corporation.  Portions created by Netscape are
 * Copyright (C) 1998 Netscape Communications Corporation. All
 * Rights Reserved.
 *
 * Contributor(s): 
 */
/* 
 * DO NOT EDIT THIS DOCUMENT MANUALLY !!!
 * THIS FILE IS AUTOMATICALLY GENERATED BY THE TOOLS UNDER
 *    AutoDetect/tools/
 */

package org.vietspider.chars.chardet.bak ;

public class GB18030Verifier extends Verifier {

  public GB18030Verifier() {

    cclass = new int[256/8] ;

    cclass[0] = ((((  ((((  (((( 1) << 4) | (1)))  ) << 8) | (((((1) << 4) | ( 1))) ))) ) << 16) | (  ((((  ((((1) << 4) | (1))) ) << 8) | (   ((((1) << 4) | (1))) )))))) ;
    cclass[1] = ((((  ((((  (((( 0) << 4) | (0)))  ) << 8) | (((((1) << 4) | ( 1))) ))) ) << 16) | (  ((((  ((((1) << 4) | (1))) ) << 8) | (   ((((1) << 4) | (1))) )))))) ;
    cclass[2] = ((((  ((((  (((( 1) << 4) | (1)))  ) << 8) | (((((1) << 4) | ( 1))) ))) ) << 16) | (  ((((  ((((1) << 4) | (1))) ) << 8) | (   ((((1) << 4) | (1))) )))))) ;
    cclass[3] = ((((  ((((  (((( 1) << 4) | (1)))  ) << 8) | (((((1) << 4) | ( 1))) ))) ) << 16) | (  ((((  ((((0) << 4) | (1))) ) << 8) | (   ((((1) << 4) | (1))) )))))) ;
    cclass[4] = ((((  ((((  (((( 1) << 4) | (1)))  ) << 8) | (((((1) << 4) | ( 1))) ))) ) << 16) | (  ((((  ((((1) << 4) | (1))) ) << 8) | (   ((((1) << 4) | (1))) )))))) ;
    cclass[5] = ((((  ((((  (((( 1) << 4) | (1)))  ) << 8) | (((((1) << 4) | ( 1))) ))) ) << 16) | (  ((((  ((((1) << 4) | (1))) ) << 8) | (   ((((1) << 4) | (1))) )))))) ;
    cclass[6] = ((((  ((((  (((( 3) << 4) | (3)))  ) << 8) | (((((3) << 4) | ( 3))) ))) ) << 16) | (  ((((  ((((3) << 4) | (3))) ) << 8) | (   ((((3) << 4) | (3))) )))))) ;
    cclass[7] = ((((  ((((  (((( 1) << 4) | (1)))  ) << 8) | (((((1) << 4) | ( 1))) ))) ) << 16) | (  ((((  ((((1) << 4) | (1))) ) << 8) | (   ((((3) << 4) | (3))) )))))) ;
    cclass[8] = ((((  ((((  (((( 2) << 4) | (2)))  ) << 8) | (((((2) << 4) | ( 2))) ))) ) << 16) | (  ((((  ((((2) << 4) | (2))) ) << 8) | (   ((((2) << 4) | (2))) )))))) ;
    cclass[9] = ((((  ((((  (((( 2) << 4) | (2)))  ) << 8) | (((((2) << 4) | ( 2))) ))) ) << 16) | (  ((((  ((((2) << 4) | (2))) ) << 8) | (   ((((2) << 4) | (2))) )))))) ;
    cclass[10] = ((((  ((((  (((( 2) << 4) | (2)))  ) << 8) | (((((2) << 4) | ( 2))) ))) ) << 16) | (  ((((  ((((2) << 4) | (2))) ) << 8) | (   ((((2) << 4) | (2))) )))))) ;
    cclass[11] = ((((  ((((  (((( 2) << 4) | (2)))  ) << 8) | (((((2) << 4) | ( 2))) ))) ) << 16) | (  ((((  ((((2) << 4) | (2))) ) << 8) | (   ((((2) << 4) | (2))) )))))) ;
    cclass[12] = ((((  ((((  (((( 2) << 4) | (2)))  ) << 8) | (((((2) << 4) | ( 2))) ))) ) << 16) | (  ((((  ((((2) << 4) | (2))) ) << 8) | (   ((((2) << 4) | (2))) )))))) ;
    cclass[13] = ((((  ((((  (((( 2) << 4) | (2)))  ) << 8) | (((((2) << 4) | ( 2))) ))) ) << 16) | (  ((((  ((((2) << 4) | (2))) ) << 8) | (   ((((2) << 4) | (2))) )))))) ;
    cclass[14] = ((((  ((((  (((( 2) << 4) | (2)))  ) << 8) | (((((2) << 4) | ( 2))) ))) ) << 16) | (  ((((  ((((2) << 4) | (2))) ) << 8) | (   ((((2) << 4) | (2))) )))))) ;
    cclass[15] = ((((  ((((  (((( 4) << 4) | (2)))  ) << 8) | (((((2) << 4) | ( 2))) ))) ) << 16) | (  ((((  ((((2) << 4) | (2))) ) << 8) | (   ((((2) << 4) | (2))) )))))) ;
    cclass[16] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (5))) )))))) ;
    cclass[17] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[18] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[19] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[20] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[21] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[22] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[23] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[24] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[25] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[26] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[27] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[28] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[29] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[30] = ((((  ((((  (((( 6) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;
    cclass[31] = ((((  ((((  (((( 0) << 4) | (6)))  ) << 8) | (((((6) << 4) | ( 6))) ))) ) << 16) | (  ((((  ((((6) << 4) | (6))) ) << 8) | (   ((((6) << 4) | (6))) )))))) ;



    states = new int[6] ;

    states[0] = ((((  ((((  (((( eError) << 4) | (     3)))  ) << 8) | (((((eStart) << 4) | ( eStart))) ))) ) << 16) | (  ((((  ((((eStart) << 4) | (eStart))) ) << 8) | (   ((((eStart) << 4) | (eError))) )))))) ;
    states[1] = ((((  ((((  (((( eItsMe) << 4) | (eItsMe)))  ) << 8) | (((((eError) << 4) | ( eError))) ))) ) << 16) | (  ((((  ((((eError) << 4) | (eError))) ) << 8) | (   ((((eError) << 4) | (eError))) )))))) ;
    states[2] = ((((  ((((  (((( eStart) << 4) | (eError)))  ) << 8) | (((((eError) << 4) | ( eItsMe))) ))) ) << 16) | (  ((((  ((((eItsMe) << 4) | (eItsMe))) ) << 8) | (   ((((eItsMe) << 4) | (eItsMe))) )))))) ;
    states[3] = ((((  ((((  (((( eError) << 4) | (eError)))  ) << 8) | (((((eError) << 4) | ( eError))) ))) ) << 16) | (  ((((  ((((eStart) << 4) | (eStart))) ) << 8) | (   ((((eError) << 4) | (     4))) )))))) ;
    states[4] = ((((  ((((  (((( eError) << 4) | (eItsMe)))  ) << 8) | (((((eError) << 4) | ( eError))) ))) ) << 16) | (  ((((  ((((eError) << 4) | (     5))) ) << 8) | (   ((((eError) << 4) | (eError))) )))))) ;
    states[5] = ((((  ((((  (((( eStart) << 4) | (eStart)))  ) << 8) | (((((eStart) << 4) | ( eStart))) ))) ) << 16) | (  ((((  ((((eStart) << 4) | (eStart))) ) << 8) | (   ((((eError) << 4) | (eError))) )))))) ;

    charset =  "GB18030";
    stFactor =  7;

  }

}