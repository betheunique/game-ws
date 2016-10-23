package com.game.ws.game.beans;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by abhishekrai on 20/10/2016.
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class DefaultResponseBean {

    protected int errorCode;
    protected String errorMessage;

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "[errorCode="+ errorCode + "errorMessage=" + errorMessage + "]";
    }
}
