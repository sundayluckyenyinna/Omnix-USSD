package com.accionmfb.ussd.ussdapplication.menuhandler;

import com.accionmfb.ussd.ussdapplication.annotation.UssdMenuHandler;
import com.accionmfb.ussd.ussdapplication.annotation.UssdSubMenuHandler;
import com.accionmfb.ussd.ussdapplication.context.ussd.UssdContext;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.HalloTagMessage;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.HalloTagMessageGenerator;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.MenuOptionBuilder;

@UssdMenuHandler
public class HomeMenuHandler
{
    @UssdSubMenuHandler
    public HalloTagMessage showHomeMenu(UssdContext context){
        MenuOptionBuilder builder = new MenuOptionBuilder();
        builder.addOption(1, "Account service");
        builder.addOption(2, "Self service");
        builder.addOption(3, "Funds transfer");
        builder.addOption(4, "Airtime and Data");
        return HalloTagMessageGenerator.generateRequiredInputMessage(builder, context);
    }
}
