package com.lap.roomplaningsystem.app;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    public static String regex_pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,24}$";
    public static String salt = BCrypt.gensalt();



    public Password() {

    }

    public static boolean validate(String password){

        Pattern pattern = Pattern.compile(regex_pattern);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }


    public static String hash(String password) {
        //TODO: Passwort hash and verify
        return password;

    }

    public static String verify(String password) {
        return null;
    }
}
