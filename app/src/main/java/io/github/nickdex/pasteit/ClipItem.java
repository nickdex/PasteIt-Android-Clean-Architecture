package io.github.nickdex.pasteit;

/**
 * Model class to represent a single ClipItem
 */

class ClipItem {
    private String text;
    private String deviceName;
    private String deviceType;


    public ClipItem() {
    }

    ClipItem(String text, String deviceName, String deviceType) {
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

    String getDeviceName() {
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

