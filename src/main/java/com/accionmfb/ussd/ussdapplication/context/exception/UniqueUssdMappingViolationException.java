package com.accionmfb.ussd.ussdapplication.context.exception;

public class UniqueUssdMappingViolationException extends RuntimeException
{
    public UniqueUssdMappingViolationException(){ super(); }
    public UniqueUssdMappingViolationException(String message){ super(message); }
}
