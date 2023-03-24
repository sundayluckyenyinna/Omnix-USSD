package com.accionmfb.ussd.ussdapplication.context.session;

public enum SessionOperations
{
    BEGIN,
    CONTINUE,
    END;

    public String value(){
        return this.name().toLowerCase();
    }
}
