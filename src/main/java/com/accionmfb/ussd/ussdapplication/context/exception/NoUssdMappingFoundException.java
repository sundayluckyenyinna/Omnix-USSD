package com.accionmfb.ussd.ussdapplication.context.exception;

import com.accionmfb.ussd.ussdapplication.context.ussd.UssdContext;

public class NoUssdMappingFoundException extends RuntimeException
{
    private UssdContext ussdContext;
    public NoUssdMappingFoundException(){
        super();
    }
    public NoUssdMappingFoundException(String message){
        super(message);
    }
    public NoUssdMappingFoundException(String message, UssdContext ussdContext){
        super(message);
        this.ussdContext = ussdContext;
    }

    public UssdContext getUssdContext() {
        return ussdContext;
    }
}
