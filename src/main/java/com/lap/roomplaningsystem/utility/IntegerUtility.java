package com.lap.roomplaningsystem.utility;

public class IntegerUtility {

    public static Integer getInt(String s) {
        try{
            return Integer.parseInt(s);
        } catch (Exception e){
            return null;
        }
    }
}
