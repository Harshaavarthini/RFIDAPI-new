package org.ups.rfidtrack.utils;

import java.util.Random;

public class AWBclass {

    public static String generateAWB(){
        Random r=new Random();
        return "1Z"+r.nextInt(10000,99999)+(char)r.nextInt(65,91)+r.nextInt(1000000000,1999999999);
    }
}
