package com.hogslab.dailywages;


public class Utility {

    public static final short calculateAge(short age, long referenceDatetime) {
        long currentTimeStamp = System.currentTimeMillis() / 1000;

        short increment = (short) ((currentTimeStamp - referenceDatetime)/(60 * 60 * 24 * 365));
        return (short) (age + increment);
    }

}
