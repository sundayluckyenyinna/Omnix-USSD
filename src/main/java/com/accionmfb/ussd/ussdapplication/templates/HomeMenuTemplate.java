package com.accionmfb.ussd.ussdapplication.templates;

import com.accionmfb.ussd.ussdapplication.context.ussd.message.MenuOptionBuilder;

public class HomeMenuTemplate
{
    public static String getHomeTemplate(){
        MenuOptionBuilder builder = new MenuOptionBuilder();
        builder.addOption(1, "Account service")
                .addOption(2, "Self service")
                .addOption(3, "Funds transfer")
                .addOption(4, "Airtime and Data");
        return builder.toString();
    }

}
