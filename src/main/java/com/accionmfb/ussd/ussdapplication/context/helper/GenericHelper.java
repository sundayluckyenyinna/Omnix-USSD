package com.accionmfb.ussd.ussdapplication.context.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericHelper
{
    public static List<Integer> getIndicesOfPlaceholdersFromMapping(String mapping){
        List<Integer> result = new ArrayList<Integer>();
        mapping = stripMapping(mapping);
        List<String> tokens = Stream.of(mapping.split("\\*"))
                .filter(t -> !t.isBlank())
                .filter(t -> !t.isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());
        for(String token : tokens){
            token = token.trim();
            if(token.startsWith("{") && token.endsWith("}")){
                result.add(tokens.indexOf(token));
            }
        }
        return result;
    }

    public static List<String> getListOfParamValuesReplacingPlaceholders(String mapping, List<Integer> placeholdersIndices){
        List<String> params = new ArrayList<String>();
        mapping = stripMapping(mapping);
        List<String> tokens = List.of(mapping.split("\\*"));
        for(Integer index : placeholdersIndices){
            params.add(tokens.get(index).trim());
        }
        return params;
    }

    public static List<String> getPlaceHoldersNamesFromMapping(String mapping){
        List<String> result = new ArrayList<String>();
        List<Integer> placeHoldersIndices = getIndicesOfPlaceholdersFromMapping(mapping);
        mapping = stripMapping(mapping);
        List<String> tokens = List.of(mapping.split("\\*"));
        placeHoldersIndices.forEach(index -> {
            String placeholder = tokens.get(index).trim();
            String exactName = placeholder.replace("{", "").replace("}", "").trim();
            result.add(exactName.trim());
        });
        return result;
    }

    public static List<String> tokenizeMapping(String mapping){
        mapping = stripMapping(mapping);
        List<String> tokens = List.of(mapping.split("\\*"));
        return tokens.stream()
                .filter(token -> !token.isBlank())
                .filter(token -> !token.isEmpty())
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static int getCountOfPlaceHoldersInMapping(String mapping){
        return (int) normalizePlaceHolderMapping(mapping).stream()
                .filter(t -> t.equalsIgnoreCase("{n}")).count();
    }

    public static List<String> tokenizeAndCleanOutPlaceHoldersFromMapping(String mapping){
        return tokenizeMapping(mapping).stream()
                .filter(t -> !t.startsWith("{") && !t.endsWith("}"))
                .collect(Collectors.toList());
    }

    public static List<String> normalizePlaceHolderMapping(String mapping){
        return tokenizeMapping(mapping).stream()
                .map(t -> {
                    if(t.startsWith("{") && t.endsWith("}"))
                        return "{n}";
                    return t;
                })
                .collect(Collectors.toList());
    }

    public static String getNormalizeMappingString(String mapping){
        String joined = String.join("*", normalizePlaceHolderMapping(mapping));
        return "*".concat(joined).concat("#");
    }

    public static String stripMapping(String mapping){
        mapping = mapping.trim();
        if(mapping.startsWith("*"))
            mapping = mapping.substring(1);
        if(mapping.endsWith("#")){
            mapping = mapping.substring(0, mapping.lastIndexOf("#"));
        }
        return mapping;
    }

    public static boolean isMappingContainsPlaceHolders(String mapping){
        return normalizePlaceHolderMapping(mapping).contains("{n}");
    }

    public static List<Integer> getIndicesOfStar(String word){
        List<Integer> result = new ArrayList<Integer>();
        String[] tokens = word.split("");
        for(int i = 0; i < tokens.length; i++){
            String token = tokens[i];
            if(token.equalsIgnoreCase("*"))
                result.add(i);
        }
        return result;
    }
    public static int getSecondToLastIndex(String word){
        List<Integer> indices = getIndicesOfStar(word);
        return indices.get(indices.size() - 2);
    }
    public static String cleanMapping(String mapping){
        mapping = mapping.trim();
        String trimmed = mapping;
        if(trimmed.endsWith("#"))
            trimmed = trimmed.substring(0, trimmed.lastIndexOf("#"));
        List<String> tokens = Stream.of(trimmed.split("\\*"))
                .filter(t -> !t.isEmpty()).filter(t -> !t.isBlank())
                .map(String::trim)
                .collect(Collectors.toList());
        String joined = String.join("*", tokens);
        String result = joined;
        if(mapping.startsWith("*"))
            result = "*".concat(joined);
        if(mapping.endsWith("#"))
            result = result.concat("#");
        return result;
    }

    public static String getNoOfTimes(String word, int times){
        return "" + String.valueOf(word).repeat(Math.max(0, times));
    }

    public static void printUssdMappingLogHeader(){
        String header = "\n<==".concat(getNoOfTimes("=", 68))
                .concat(" USSD Mappings ")
                .concat(getNoOfTimes("=",68))
                .concat("==>");
        System.out.println(header);
    }

    public static void printUssdMappingFooter(){
        String footer = "<==".concat(getNoOfTimes("=", 150)).concat("==>\n");
        System.out.println(footer);
    }
}
