package com.accionmfb.ussd.ussdapplication.context.exception;

import java.util.List;

public class IllegalUssdHandlerMethodDefinitionException extends RuntimeException
{
    public IllegalUssdHandlerMethodDefinitionException(){
        super();
    }

    public IllegalUssdHandlerMethodDefinitionException(String message){
        super(message);
    }

    public IllegalUssdHandlerMethodDefinitionException(List<String> messages){
        super(messages.toString());
    }
}
