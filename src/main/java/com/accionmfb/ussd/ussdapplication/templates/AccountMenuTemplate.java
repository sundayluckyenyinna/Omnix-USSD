package com.accionmfb.ussd.ussdapplication.templates;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.MenuOptionBuilder;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.MessageLineBuilder;

public class AccountMenuTemplate
{

    public static String getAccountMenuTemplate(){
        MenuOptionBuilder builder = new MenuOptionBuilder();
        builder.addOption(1, "Open account with BVN")
               .addOption(2, "Open account without BVN")
                .addOption(0, "Go back");
        return builder.toString();
    }

    public static String getEnterBVNMsg(){
        return new MenuOptionBuilder()
                .addOption(1, "Enter your BVN")
                .addOption(0, "Go back")
                .toString();
    }

    public static String getEnterYourNamesAndMobileMsg(){
        return new MessageLineBuilder()
                .addLine("Enter your first name, last name and mobile number separated by a dot")
                .addLine("Press 0 to go back")
                .toString();
    }
    public static String getAccountCreationSuccessTemplate(){
        MessageLineBuilder lineBuilder = new MessageLineBuilder();
        lineBuilder.addLine("Your account has been created successfully.")
                   .addLine("Details of your account will be sent to you shortly.");
        return lineBuilder.toString();
    }

    public static String getAccountCreationErrorTemplate(){
        MessageLineBuilder lineBuilder = new MessageLineBuilder();
        lineBuilder.addLine("There was an error in creating your account.")
                .addLine("Call the customer care line for further advice and necessary actions. Thank you.");
        return lineBuilder.toString();
    }
}
