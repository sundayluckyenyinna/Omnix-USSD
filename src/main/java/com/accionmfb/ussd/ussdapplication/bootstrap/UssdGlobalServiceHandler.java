package com.accionmfb.ussd.ussdapplication.bootstrap;

import com.accionmfb.ussd.ussdapplication.context.ContextManager;
import com.accionmfb.ussd.ussdapplication.context.Item;
import com.accionmfb.ussd.ussdapplication.context.exception.NoUssdMappingFoundException;
import com.accionmfb.ussd.ussdapplication.context.helper.GenericHelper;
import com.accionmfb.ussd.ussdapplication.context.model.UssdSession;
import com.accionmfb.ussd.ussdapplication.context.repo.UssdSessionRepository;
import com.accionmfb.ussd.ussdapplication.context.ussd.UssdContext;
import com.accionmfb.ussd.ussdapplication.context.ussd.UssdPayload;
import com.accionmfb.ussd.ussdapplication.context.ussd.message.HalloTagMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class UssdGlobalServiceHandler
{
    @Autowired
    private Environment environment;

    @Autowired
    private UssdSessionRepository ussdSessionRepository;
    private static final String SPLIT_REGEX = "\\*";

    public HalloTagMessage submitForContinuation(UssdPayload ussdPayload) throws Exception {
        String exceptionMessage = Strings.EMPTY;

        // Prepare the full ussd code
        String baseCodeWithoutHash = this.cleanBaseCode(this.getUssdBaseCode());
        UssdSession session = this.getUssdSession(ussdPayload);
        String contextData = session.getContextData();
        contextData = getPreviousContextData(contextData);
        String relativeCode = this.getRelativeCodeFromContextData(contextData);
        String continuationInput = ussdPayload.getInput();
        String fullUssdCode;
        if(relativeCode.isBlank() || relativeCode.isEmpty()){
            if(continuationInput == null){
                fullUssdCode = baseCodeWithoutHash.concat("#");
            }else{
                fullUssdCode = baseCodeWithoutHash.concat("*").concat(continuationInput).concat("#");
            }
        }else{
            if(continuationInput == null){
                fullUssdCode = baseCodeWithoutHash.concat("*").concat(relativeCode).concat("#");
            }else{
                fullUssdCode = baseCodeWithoutHash.concat("*").concat(relativeCode).concat("*").concat(continuationInput).concat("#");
            }
        }

        log.info("Full USSD Code: {} <==> Originating context data: {}", fullUssdCode, contextData);

        // Build the UssdContext object
        UssdContext ussdContext = this.buildUssdContext(contextData, ussdPayload);

        // Get the method name that has this mapping of full code defined from the Ussd Context
        String invocableMethodName = ContextManager.getInvocableMethodNameByMapping(fullUssdCode);
        if(invocableMethodName == null){
            String methodName = ContextManager.getInvocableMethodNameByMappingLength(fullUssdCode);
            if(methodName != null){
                String annotatedMapping = ContextManager.getMappingFromMethodName(methodName);
                Object reflectObject = ContextManager.getActionableObjectByMappingAndMethodName(annotatedMapping, methodName);
                assert reflectObject != null;
                List<String> paramHolders = GenericHelper.getPlaceHoldersNamesFromMapping(annotatedMapping);
                List<Integer> paramIndices = GenericHelper.getIndicesOfPlaceholdersFromMapping(annotatedMapping);
                List<String> paramValues = GenericHelper.getListOfParamValuesReplacingPlaceholders(fullUssdCode, paramIndices);

                List<Class<?>> classList = new ArrayList<>();
                for(int i = 0; i < paramIndices.size(); i++){
                    classList.add(String.class);
                }
                classList.add(0, UssdContext.class);
                Method method = reflectObject.getClass().getDeclaredMethod(methodName, classList.toArray(new Class[]{}));

                List<Object> methodParameters = new ArrayList<>();
                methodParameters.add(ussdContext);
                methodParameters.addAll(paramValues);

                // Update the context data of the session
                updateUssdSession(contextData, session, ussdPayload);

                return (HalloTagMessage) method.invoke(reflectObject, methodParameters.toArray(new Object[]{}));
            }else{
                exceptionMessage = String.format("No Ussd mapping found for %s provided", fullUssdCode);
                throw new NoUssdMappingFoundException(exceptionMessage, ussdContext);
            }
        }

        // Get the object that have this method name and the mapping as unique
        Object reflectObject = ContextManager.getActionableObjectByMappingAndMethodName(fullUssdCode, invocableMethodName);

        // Invoke the method and get Ussd response message.
        assert reflectObject != null;
        Method method = reflectObject.getClass().getDeclaredMethod(invocableMethodName, UssdContext.class);
        HalloTagMessage message = (HalloTagMessage) method.invoke(reflectObject, ussdContext);

        // Update the context data of the session
        updateUssdSession(contextData, session, ussdPayload);

        return message;
    }

    private void updateUssdSession(String workingContextData, UssdSession session, UssdPayload ussdPayload){
        if(ussdPayload.getInput() != null &&
                !ussdPayload.getInput().isEmpty() &&
                !ussdPayload.getInput().isBlank()
        )
        {
            String newContextData = workingContextData.concat("*").concat(ussdPayload.getInput());
            session.setContextData(newContextData);
            ussdSessionRepository.saveAndFlush(session);
        }
    }

    private UssdContext buildUssdContext(String contextData, UssdPayload ussdPayload){
        return UssdContext.builder()
                .originatingContextData(contextData)
                .input(ussdPayload.getInput())
                .mobileNumber(ussdPayload.getMobileNumber())
                .telco(ussdPayload.getTelco())
                .sessionOperation(ussdPayload.getSessionOperation())
                .sessionId(ussdPayload.getSessionId())
                .sessionType(ussdPayload.getSessionType())
                .build();
    }
    private String getActionableContextData(UssdSession ussdSession){
        String potentialContextData = ussdSession.getContextData();
        String contextDataPrefix = String.join("*", ussdSession.getMobileNumber(), ussdSession.getTelco());
        return (potentialContextData == null || potentialContextData.equalsIgnoreCase(Strings.EMPTY)) ? contextDataPrefix : potentialContextData;
    }
    private UssdSession getUssdSession(UssdPayload ussdPayload){
        UssdSession ussdSession = ussdSessionRepository.findBySessionId(ussdPayload.getSessionId());
        if(ussdSession == null){
            ussdSession = new UssdSession();
            ussdSession.setSessionId(ussdPayload.getSessionId());
            ussdSession.setSessionStartDate(new Date());
            ussdSession.setTelco(ussdPayload.getTelco());
            ussdSession.setMobileNumber(ussdPayload.getMobileNumber());
            ussdSession.setContextData(this.getActionableContextData(ussdSession));
            return ussdSessionRepository.saveAndFlush(ussdSession);
        }
        return ussdSession;
    }

    private String cleanBaseCode(String baseCode){
        String result = baseCode;
        if(baseCode.endsWith("#")){
            result = baseCode.substring(0, baseCode.lastIndexOf("#"));
        }
        return result;
    }

    private String getRelativeCodeFromContextData(String contextData){
        String[] contextTokens = contextData.split(SPLIT_REGEX);
        List<String> codeTokens = new ArrayList<>(Arrays.asList(contextTokens).subList(2, contextTokens.length));
        return String.join("*", codeTokens);
    }

    private String getUssdBaseCode(){
        return ContextManager.getItem(Item.DEFAULT_USSD_BASE_CODE, String.class);
    }

    private String getPreviousContextData(String contextData){
        String goBackOption = ContextManager.getItem(Item.DEFAULT_USSD_GO_BACK_OPTION, String.class);
        if(contextData.endsWith(goBackOption)){
          int stopIndex = GenericHelper.getSecondToLastIndex(contextData);
          return contextData.substring(0, stopIndex);
        }
        return contextData;
    }


}
