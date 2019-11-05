package com.cnebrera.uc3.tech.lesson3.app;

import java.io.Serializable;

class AppMessage implements Serializable
{    
    private static final long serialVersionUID = 1L;

    private String message;
    private long firstSend;
    private long firstReceived;
    private long SecondSend;
    private long SecondReceived;


    AppMessage(){
        super();
    }
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the firstSend
     */
    public long getFirstSend() {
        return firstSend;
    }

    /**
     * @return the firstReceived
     */
    public long getFirstReceived() {
        return firstReceived;
    }

    /**
     * @return the secondSend
     */
    public long getSecondSend() {
        return SecondSend;
    }

    /**
     * @return the secondReceived
     */
    public long getSecondReceived() {
        return SecondReceived;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param firstSend the firstSend to set
     */
    public void setFirstSend(long firstSend) {
        this.firstSend = firstSend;
    }

    /**
     * @param firstReceived the firstReceived to set
     */
    public void setFirstReceived(long firstReceived) {
        this.firstReceived = firstReceived;
    }

    /**
     * @param secondSend the secondSend to set
     */
    public void setSecondSend(long secondSend) {
        SecondSend = secondSend;
    }
    /**
     * @param secondReceived the secondReceived to set
     */
    public void setSecondReceived(long secondReceived) {
        SecondReceived = secondReceived;
    }

    
}