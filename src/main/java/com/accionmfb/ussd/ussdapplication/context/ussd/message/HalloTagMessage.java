package com.accionmfb.ussd.ussdapplication.context.ussd.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class HalloTagMessage
{
    @SerializedName("session_msg")
    @JsonProperty("session_msg")
    String message;                   // the message to the provider

    @SerializedName("session_operation")
    @JsonProperty("session_operation")
    String sessionOperation;         // the session operation whether to begin, continue or end

    @SerializedName("session_type")
    @JsonProperty("session_type")
    String sessionType;              // the session type whether to collect input or not

    @SerializedName("session_id")
    @JsonProperty("session_id")
    String sessionId;
}
