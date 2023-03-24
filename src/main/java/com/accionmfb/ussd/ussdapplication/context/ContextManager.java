package com.accionmfb.ussd.ussdapplication.context;

import com.accionmfb.ussd.ussdapplication.context.helper.GenericHelper;

import java.util.*;

public class ContextManager
{
    public final static Map<String, String> optionMappings = new HashMap<>();   // Mapping string first, then method name
    public static Map<List<String>, Object> parentObjectMappings = new HashMap<>();
    public static Map<Item, Object> itemStorage = new HashMap<>();
    public static void saveMapping(String mapping, String methodName){
        optionMappings.put(mapping, methodName);
    }

    public static String getInvocableMethodNameByMapping(String mapping){
        return optionMappings.get(mapping);
    }

    public static String getInvocableMethodNameByMappingLength(String fullCode){
        String methodName = null;
        Collection<String> mappingList = optionMappings.keySet();
        for(String mapping : mappingList){
            if(mapping.split("\\*").length == fullCode.split("\\*").length){
                List<Integer> indices = GenericHelper.getIndicesOfPlaceholdersFromMapping(mapping);
                if(!indices.isEmpty()){
                    int firstIndex = indices.get(0);
                    String prefixCode = mapping.substring(0, mapping.indexOf("{"));
                    String fullCodePrefix = fullCode.substring(0, mapping.indexOf("{"));
                    if(prefixCode.equalsIgnoreCase(fullCodePrefix)){
                        methodName = optionMappings.get(mapping);
                        break;
                    }
                }
            }
        }
        return  methodName;
    }

    public static String getMappingFromMethodName(String methodName){
        Collection<String> mappingNames = optionMappings.keySet();
        String mapping = null;
        for(String m : mappingNames){
            if(optionMappings.get(m).equalsIgnoreCase(methodName)){
                mapping = m;
                break;
            }
        }
        return mapping;
    }

    public static Object getActionableObjectByMappingAndMethodName(String mapping, String methodName){
        String methodNameAndMapping = mapping.concat(methodName);
        Collection<List<String>> specificUssdHandlerMethodNames = parentObjectMappings.keySet();
        for(List<String> methodNames : specificUssdHandlerMethodNames){
            if(methodNames.contains(methodNameAndMapping)){
                return parentObjectMappings.get(methodNames);
            }
        }
        return null;
    }

    public static boolean isMappingAlreadyExists(String mapping){
        boolean isAlreadyExists = false;
        Collection<String> alreadyExistMappings = optionMappings.keySet();
        if(alreadyExistMappings.contains(mapping)){
            isAlreadyExists = true;
        }
        return isAlreadyExists;
    }

    public static void setItem(Item item, Object value){
        itemStorage.put(item, value);
    }

    public static <T> T getItem(Item item, Class<T> clazz){
        Object value = itemStorage.get(item);
        return (T)value;
    }
}
