package io.github.nickdex.pasteit.data.entity;

import io.github.nickdex.pasteit.core.data.entity.Entity;

/**
 * Model class to represent a clip item in data layer.
 */
public final class ClipItemEntity implements Entity {
    private String text;
    private String deviceName;
    private String deviceType;
    private String id;

    public ClipItemEntity() {
    }

    public ClipItemEntity(String text, String deviceName, String deviceType) {
        this.text = text;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}

