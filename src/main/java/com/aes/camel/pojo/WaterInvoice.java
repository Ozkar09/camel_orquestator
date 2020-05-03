
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
    "idFactura",
    "valorFactura"
})
public class WaterInvoice {

    @JsonProperty("idFactura")
    private Integer idFactura;
    @JsonProperty("valorFactura")
    private Double valorFactura;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idFactura")
    public Integer getIdFactura() {
        return idFactura;
    }

    @JsonProperty("idFactura")
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    @JsonProperty("valorFactura")
    public Double getValorFactura() {
        return valorFactura;
    }

    @JsonProperty("valorFactura")
    public void setValorFactura(Double valorFactura) {
        this.valorFactura = valorFactura;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
