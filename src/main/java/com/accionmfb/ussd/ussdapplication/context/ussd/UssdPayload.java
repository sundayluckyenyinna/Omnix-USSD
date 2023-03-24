package com.accionmfb.ussd.ussdapplication.context.ussd;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UssdPayload
{
    String input;
    String mobileNumber;
    String telco;
    String sessionId;
    String sessionType;
    String sessionOperation;
}
