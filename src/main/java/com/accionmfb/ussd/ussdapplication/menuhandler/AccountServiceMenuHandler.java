package com.accionmfb.ussd.ussdapplication.menuhandler;

import com.accionmfb.ussd.ussdapplication.annotation.UssdMenuHandler;
import com.accionmfb.ussd.ussdapplication.annotation.UssdParam;
import com.accionmfb.ussd.ussdapplication.annotation.UssdSubMenuHandler;
import com.accionmfb.ussd.ussdapplication.context.ContextManager;
import com.accionmfb.ussd.ussdapplication.context.Item;
import com.accionmfb.ussd.ussdapplication.context.ussd.UssdContext;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.HalloTagMessage;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.HalloTagMessageGenerator;
import com.accionmfb.ussd.ussdapplication.templates.AccountMenuTemplate;
import com.accionmfb.ussd.ussdapplication.templates.HomeMenuTemplate;
import com.accionmfb.ussd.ussdapplication.templates.InvalidSelectionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@UssdMenuHandler("1")
@Slf4j
public class AccountServiceMenuHandler
{

    private static String goBackOption = ContextManager.getItem(Item.DEFAULT_USSD_GO_BACK_OPTION, String.class);

    @Bean
    public String goBackOption(){
        goBackOption = ContextManager.getItem(Item.DEFAULT_USSD_GO_BACK_OPTION, String.class);
        return "";
    }

    // Show account menu.
    @UssdSubMenuHandler
    public HalloTagMessage showAccountMenu(UssdContext context){
        String accountMenuMsg = AccountMenuTemplate.getAccountMenuTemplate();
        return HalloTagMessageGenerator.generateRequiredInputMessage(accountMenuMsg, context);
    }

    // Handle the selection of how to open account (either with option of BVN or no BVN)
    @UssdSubMenuHandler("* { input } #")
    public HalloTagMessage handleOpenAccountMenuSelection(UssdContext context, String input){
        // Here user chose to open account with BVN
        if(input.equals("1")) {
            String enterBvnMsg = AccountMenuTemplate.getEnterBVNMsg();
            return HalloTagMessageGenerator.generateRequiredInputMessage(enterBvnMsg, context);
        }
        // Here user chose to open account without BVN
        else if(input.equals("2")){
            String credentialsMsg = AccountMenuTemplate.getEnterYourNamesAndMobileMsg();
            return HalloTagMessageGenerator.generateRequiredInputMessage(credentialsMsg, context);
        }
        // Here the user chose to go back to the home menus
        else if(input.equals(goBackOption)){
            String home = HomeMenuTemplate.getHomeTemplate();
            return HalloTagMessageGenerator.generateRequiredInputMessage(home, context);
        }
        // Here the user entered a wrong input.
        else{
            String error = InvalidSelectionTemplate.getInvalidSelectionTemplate();
            return HalloTagMessageGenerator.generateViewOnlyMessageForSessionAbortion(error, context);
        }
    }

    // Handle the input for user who chose to open account with BVN (i.e Option 1 and an input of BVN)
    @UssdSubMenuHandler("* 1 * { input } #")
    public HalloTagMessage handleAccountOpeningWithBVN(UssdContext context, @UssdParam("input") String input){
        // Here the user chose to go back to the account menu.
        if(input.equals(goBackOption)){
            return this.showAccountMenu(context);
        }
        // Here, whatever the user enters is the user's BVN. Call omnix to open the account with this BVN
        log.info("BVN entered is {}", input);

        // Assuming response from omnix is success
        String successMsg = AccountMenuTemplate.getAccountCreationSuccessTemplate();
        return HalloTagMessageGenerator.generateViewOnlyMessageForSessionAbortion(successMsg, context);
    }

    // Handle the input for user who chose to open account without BVN (i.e option 2 and an input of details)
    @UssdSubMenuHandler("* 2 * { input } #")
    public HalloTagMessage handleAccountOpeningWithoutBVN(UssdContext context, @UssdParam("input") String input){
        // Here the user chose to go back to the account menu.
        if(input.equals(goBackOption)){
            return this.showAccountMenu(context);
        }
        // Here, whatever the user enters is the user's details. Call omnix to open the account with this details
        log.info("Details entered is {}", input);

        // Assuming response from omnix is success
        String successMsg = AccountMenuTemplate.getAccountCreationSuccessTemplate();
        return HalloTagMessageGenerator.generateViewOnlyMessageForSessionAbortion(successMsg, context);
    }
}
