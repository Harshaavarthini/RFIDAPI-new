package org.ups.rfidtrack.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String reason){
        super(reason);
    }
}
