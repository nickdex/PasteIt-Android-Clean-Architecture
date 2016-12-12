/*
 * Copyright © 2016 Nikhil Warke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.nickdex.pasteit.presentation.model;

import io.github.nickdex.pasteit.domain.model.Device;

/**
 * Model Class to represent a message in presentation layer.
 */
public class MessageModel {

    private String text;
    private Device deviceType;
    private long timestamp;
    private int deviceResId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Device getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Device deviceType) {
        this.deviceType = deviceType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDeviceResId() {
        return deviceResId;
    }

    public void setDeviceResId(int deviceResId) {
        this.deviceResId = deviceResId;
    }
}
