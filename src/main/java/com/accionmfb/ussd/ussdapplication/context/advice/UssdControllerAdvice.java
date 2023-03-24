package com.accionmfb.ussd.ussdapplication.context.advice;

import com.accionmfb.ussd.ussdapplication.context.exception.NoUssdMappingFoundException;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.HalloTagMessage;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.HalloTagMessageGenerator;
import com.accionmfb.ussd.ussdapplication.controller.UssdHollatagController;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

@RestControllerAdvice(basePackageClasses = { UssdHollatagController.class })
public class UssdControllerAdvice
{

    @Autowired
    private Gson gson;

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NoUssdMappingFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleNoUssdMappingException(HttpServletResponse response, NoUssdMappingFoundException e) throws IOException {
        PrintWriter writer = response.getWriter();
        String errorMessage = this.messageSource.getMessage("ussd.option.invalidSelection", null, Locale.ENGLISH);
        HalloTagMessage responseMessage = HalloTagMessageGenerator
                .generateViewOnlyMessageForSessionAbortion(errorMessage, e.getUssdContext());

        String responseJson = new Gson().toJson(responseMessage);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        writer.write(responseJson);
    }
}
