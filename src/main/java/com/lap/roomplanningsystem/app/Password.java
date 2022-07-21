package com.lap.roomplanningsystem.app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    public static String regex_pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,24}$";
    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public Password() {

    }

    public static boolean validate(String password){

        Pattern pattern = Pattern.compile(regex_pattern);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }


    public static String hash(String password) {
        return encoder.encode(password);
    }

    public static boolean verify(String fromDB, String password) {
        return encoder.matches(password, fromDB);

    }
}
