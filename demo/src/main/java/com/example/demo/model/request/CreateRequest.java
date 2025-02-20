package com.example.demo.model.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "CreateRequest")
public class CreateRequest {

    @JacksonXmlProperty(localName = "index")
    private String index;

    @JacksonXmlProperty(localName = "data")
    private String data;

    @JacksonXmlProperty(localName = "accessId")
    private String accessId;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }
}
