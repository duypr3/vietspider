/***************************************************************************
 * Copyright 2001-2009 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package org.vietspider.common.text;

import java.util.Comparator;

/** 
 * Author : Le Bien Thuy
 * Dec 18, 2007  
 */
public class VietComparator implements Comparator<String> {
  
  private int compare(char c1, char c2) {
    if(c1 == c2) return 0;
    int enc1 = encode(c1);
    int enc2 = encode(c2);
    return (enc1 < 0 || enc2 < 0) ? c1 - c2 : enc1- enc2;
  }
  
  public  int compare(String c1, String c2) {
    int min = Math.min(c2.length(), c1.length());
    for(int i = 0; i < min; i++){
      int k = compare(c1.charAt(i), c2.charAt(i));
      if(k != 0) return k;
    }
    return c1.length() - c2.length();
  }
  
  private int encode(char c){
    switch (c) {
    case 'A': return 0;
    case 'a': return 1;
    case 'À': return 2;
    case 'à': return 3;
    case 'Á': return 4;
    case 'á': return 5;
    case 'Ả': return 6;
    case 'ả': return 7;
    case 'Ã': return 8;
    case 'ã': return 9;
    case 'Ạ': return 10;
    case 'ạ': return 11;
    case 'Ă': return 12;
    case 'ă': return 13;
    case 'Ằ': return 14;
    case 'ằ': return 15;
    case 'Ắ': return 16;
    case 'ắ': return 17;
    case 'Ẳ': return 18;
    case 'ẳ': return 19;
    case 'Ẵ': return 20;
    case 'ẵ': return 21;
    case 'Ặ': return 22;
    case 'ặ': return 23;
    case 'Â': return 24;
    case 'â': return 25;
    case 'Ầ': return 26;
    case 'ầ': return 27;
    case 'Ấ': return 28;
    case 'ấ': return 29;
    case 'Ẩ': return 30;
    case 'ẩ': return 31;
    case 'Ẫ': return 32;
    case 'ẫ': return 33;
    case 'Ậ': return 34;
    case 'ậ': return 35;
    case 'B': return 36;
    case 'b': return 37;
    case 'C': return 38;
    case 'c': return 39;
    case 'D': return 40;
    case 'd': return 41;
    case 'Đ': return 42;
    case 'đ': return 43;
    case 'E': return 44;
    case 'e': return 45;
    case 'È': return 46;
    case 'è': return 47;
    case 'É': return 48;
    case 'é': return 49;
    case 'Ẻ': return 50;
    case 'ẻ': return 51;
    case 'Ẽ': return 52;
    case 'ẽ': return 53;
    case 'Ẹ': return 54;
    case 'ẹ': return 55;
    case 'Ê': return 56;
    case 'ê': return 57;
    case 'Ề': return 58;
    case 'ề': return 59;
    case 'Ế': return 60;
    case 'ế': return 61;
    case 'Ể': return 62;
    case 'ể': return 63;
    case 'Ễ': return 64;
    case 'ễ': return 65;
    case 'Ệ': return 66;
    case 'ệ': return 67;
    case 'F': return 68;
    case 'f': return 69;
    case 'G': return 70;
    case 'g': return 71;
    case 'H': return 72;
    case 'h': return 73;
    case 'I': return 74;
    case 'i': return 75;
    case 'Ì': return 76;
    case 'ì': return 77;
    case 'Í': return 78;
    case 'í': return 79;
    case 'Ỉ': return 80;
    case 'ỉ': return 81;
    case 'Ĩ': return 82;
    case 'ĩ': return 83;
    case 'Ị': return 84;
    case 'ị': return 85;
    case 'J': return 86;
    case 'j': return 87;
    case 'K': return 88;
    case 'k': return 89;
    case 'L': return 90;
    case 'l': return 91;
    case 'M': return 92;
    case 'm': return 93;
    case 'N': return 94;
    case 'n': return 95;
    case 'O': return 96;
    case 'o': return 97;
    case 'Ò': return 98;
    case 'ò': return 99;
    case 'Ó': return 100;
    case 'ó': return 101;
    case 'Ỏ': return 102;
    case 'ỏ': return 103;
    case 'Õ': return 104;
    case 'õ': return 105;
    case 'Ọ': return 106;
    case 'ọ': return 107;
    case 'Ô': return 108;
    case 'ô': return 109;
    case 'Ồ': return 110;
    case 'ồ': return 111;
    case 'Ố': return 112;
    case 'ố': return 113;
    case 'Ổ': return 114;
    case 'ổ': return 115;
    case 'Ỗ': return 116;
    case 'ỗ': return 117;
    case 'Ộ': return 118;
    case 'ộ': return 119;
    case 'Ơ': return 120;
    case 'ơ': return 121;
    case 'Ờ': return 122;
    case 'ờ': return 123;
    case 'Ớ': return 124;
    case 'ớ': return 125;
    case 'Ở': return 126;
    case 'ở': return 127;
    case 'Ỡ': return 128;
    case 'ỡ': return 129;
    case 'Ợ': return 130;
    case 'ợ': return 131;
    case 'P': return 132;
    case 'p': return 133;
    case 'Q': return 134;
    case 'q': return 135;
    case 'R': return 136;
    case 'r': return 137;
    case 'S': return 138;
    case 's': return 139;
    case 'T': return 140;
    case 't': return 141;
    case 'U': return 142;
    case 'u': return 143;
    case 'Ù': return 144;
    case 'ù': return 145;
    case 'Ú': return 146;
    case 'ú': return 147;
    case 'Ủ': return 148;
    case 'ủ': return 149;
    case 'Ũ': return 150;
    case 'ũ': return 151;
    case 'Ụ': return 152;
    case 'ụ': return 153;
    case 'Ư': return 154;
    case 'ư': return 155;
    case 'Ừ': return 156;
    case 'ừ': return 157;
    case 'Ứ': return 158;
    case 'ứ': return 159;
    case 'Ử': return 160;
    case 'ử': return 161;
    case 'Ữ': return 162;
    case 'ữ': return 163;
    case 'Ự': return 164;
    case 'ự': return 165;
    case 'V': return 166;
    case 'v': return 167;
    case 'W': return 168;
    case 'w': return 169;
    case 'X': return 170;
    case 'x': return 171;
    case 'Y': return 172;
    case 'y': return 173;
    case 'Ỳ': return 174;
    case 'ỳ': return 175;
    case 'Ý': return 176;
    case 'ý': return 177;
    case 'Ỷ': return 178;
    case 'ỷ': return 179;
    case 'Ỹ': return 180;
    case 'ỹ': return 181;
    case 'Ỵ': return 182;
    case 'ỵ': return 183;
    case 'Z': return 184;
    case 'z': return 185;
    default: return -1;
  }
  }
}