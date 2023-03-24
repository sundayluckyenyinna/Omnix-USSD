package com.accionmfb.ussd.ussdapplication.context.ussd.message;

import java.util.ArrayList;
import java.util.List;

public class MessageLineBuilder
{
    private final List<String> messageLine = new ArrayList<>();

    public MessageLineBuilder(){}

    public MessageLineBuilder addLine(String line){
        messageLine.add(line);
        return this;
    }

    public String toString() {
        if(messageLine.isEmpty())
            return "";
        return String.join("\n", this.messageLine);
    }
}
