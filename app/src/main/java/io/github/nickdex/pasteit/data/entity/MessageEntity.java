package io.github.nickdex.pasteit.data.entity;

import io.github.nickdex.pasteit.core.data.entity.Entity;

/**
 * Model class to represent a message item in data layer.
 */
public final class MessageEntity implements Entity {
    private String text;
    private String deviceName;
    private String senderEmail;
    private String id;
    private long timestamp;

    public MessageEntity() {
    }

    public MessageEntity(String text, String deviceName, String senderEmail, long timestamp) {
        this.text = text;
        this.deviceName = deviceName;
        this.senderEmail = senderEmail;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

