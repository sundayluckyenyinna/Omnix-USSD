package com.accionmfb.ussd.ussdapplication.controller;

import com.accionmfb.ussd.ussdapplication.bootstrap.UssdGlobalServiceHandler;
import com.accionmfb.ussd.ussdapplication.context.ussd.UssdPayload;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.HalloTagMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/ussd/ussdSessionEvent")
@Slf4j
public class UssdHollatagController
{
    @Autowired
    private UssdGlobalServiceHandler ussdGlobalServiceHandler;

    @GetMapping(value = "/new")
    public HalloTagMessage testContext(@RequestParam Map<String, String> params) throws Exception {
        UssdPayload ussdPayload = buildUssdPayload(params);
        return ussdGlobalServiceHandler.submitForContinuation(ussdPayload);
    }

    @GetMapping(value = "/continue")
    public HalloTagMessage continueUssd(@RequestParam Map<String, String> params) throws Exception {
        UssdPayload ussdPayload = buildUssdPayload(params);
        return ussdGlobalServiceHandler.submitForContinuation(ussdPayload);
    }

    @GetMapping(value = "/abort")
    public HalloTagMessage abortUssdSession(@RequestParam Map<String, String> params) throws Exception{
        return null;
    }

    private UssdPayload buildUssdPayload(Map<String, String> params){
        return UssdPayload.builder()
                .input(params.get("session_msg"))
                .telco(params.get("session_mno"))
                .sessionOperation(params.get("session_operation"))
                .sessionId(params.get("session_id"))
                .sessionType(params.get("session_type"))
                .mobileNumber(params.get("session_msisdn"))
                .build();
    }

}
