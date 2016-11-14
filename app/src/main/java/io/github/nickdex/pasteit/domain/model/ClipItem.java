package io.github.nickdex.pasteit.domain.model;

/**
 * Model class to represent a single ClipItem
 * @author Nikhil Warke
 */

public final class ClipItem {
    private String text;
    private String deviceName;
    private String deviceType;


    public ClipItem() {
    }

    public ClipItem(String text, String deviceName, String deviceType) {
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
}

