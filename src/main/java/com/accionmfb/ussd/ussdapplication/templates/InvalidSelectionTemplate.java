package com.accionmfb.ussd.ussdapplication.templates;

import com.accionmfb.ussd.ussdapplication.context.ussd.message.MenuOptionBuilder;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.MessageLineBuilder;

public class InvalidSelectionTemplate
{
    public static String getInvalidSelectionTemplate(){
        return new MessageLineBuilder()
                .addLine("Invalid option selected")
                .toString();
    }
}
