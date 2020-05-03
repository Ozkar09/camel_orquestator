
package com.aes.camel.pojo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "user",
    "password",
    "channel",
    "invoice"
})
public class Payment {

    @JsonProperty("user")
    private String user;
    @JsonProperty("password")
    private String password;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("invoice")
    private Invoice invoice;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("channel")
    public String getChannel() {
        return channel;
    }

    @JsonProperty("channel")
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @JsonProperty("invoice")
    public Invoice getInvoice() {
        return invoice;
    }

    @JsonProperty("invoice")
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", channel='" + channel + '\'' +
                ", invoice=" + invoice +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
