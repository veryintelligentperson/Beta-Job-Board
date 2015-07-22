package com.utility;

/**
 * Created by michal on 03.07.15.
 */
public class EmailExistsException extends Throwable {
    public EmailExistsException(){
        super();
    }
    public EmailExistsException(String message) {
        super(message);
    }
}
