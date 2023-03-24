package com.accionmfb.ussd.ussdapplication.context.ussd.message;

import com.accionmfb.ussd.ussdapplication.context.session.SessionOperations;
import com.accionmfb.ussd.ussdapplication.context.session.SessionTypes;
import com.accionmfb.ussd.ussdapplication.context.ussd.UssdContext;

public class HalloTagMessageGenerator
{
    public static HalloTagMessage generateRequiredInputMessage(MenuOptionBuilder builder, UssdContext context){
        return generateRequiredInputMessage(builder.toString(), context);
    }
    public static HalloTagMessage generateRequiredInputMessage(String message, UssdContext context){
        HalloTagMessage halloTagMessage = new HalloTagMessage();
        halloTagMessage.setMessage(message);
        halloTagMessage.setSessionOperation(SessionOperations.CONTINUE.value());
        halloTagMessage.setSessionType(SessionTypes.REQUIRE_INPUT_TYPE);
        halloTagMessage.setSessionId(context.getSessionId());
        return halloTagMessage;
    }

    public static HalloTagMessage generateViewOnlyMessageForSessionContinuation(String message, UssdContext context){
        HalloTagMessage halloTagMessage = new HalloTagMessage();
        halloTagMessage.setMessage(message);
        halloTagMessage.setSessionOperation(SessionOperations.CONTINUE.value());
        halloTagMessage.setSessionType(SessionTypes.VIEW_ONLY_TYPE);
        halloTagMessage.setSessionId(context.getSessionId());
        return halloTagMessage;
    }

    public static HalloTagMessage generateViewOnlyMessageForSessionAbortion(String message, UssdContext context){
        HalloTagMessage halloTagMessage = new HalloTagMessage();
        halloTagMessage.setMessage(message);
        halloTagMessage.setSessionOperation(SessionOperations.END.value());
        halloTagMessage.setSessionId(context.getSessionId());
        halloTagMessage.setSessionType(SessionTypes.END_SESSION_TYPE);
        return halloTagMessage;
    }


}
