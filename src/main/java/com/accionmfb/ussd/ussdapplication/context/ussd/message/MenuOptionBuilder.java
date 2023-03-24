package com.accionmfb.ussd.ussdapplication.context.ussd.message;

import java.util.ArrayList;
import java.util.List;

public class MenuOptionBuilder
{
    private final List<String> menuOptions = new ArrayList<>();
    public void addOption(String optionNo, String option){
        String combinedOptionMessage = optionNo.trim().concat(". ").concat(option.trim());
        menuOptions.add(combinedOptionMessage);
    }

    public MenuOptionBuilder addOption(int optionNo, String option){
        String optionNoString = String.valueOf(optionNo);
        addOption(optionNoString, option);
        return this;
    }

    public String toString() {
        if(menuOptions.isEmpty())
            return "";
        return String.join("\n", menuOptions);
    }
}
