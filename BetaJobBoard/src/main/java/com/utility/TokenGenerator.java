package com.utility;

import java.util.UUID;

/**
 * Created by michal on 04.07.15.
 */
public class TokenGenerator {

    public static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
