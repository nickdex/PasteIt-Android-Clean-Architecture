/*
 * Copyright © 2017 Nikhil Warke
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

package io.github.nickdex.pasteit.framework.data.mapper;

import android.text.TextUtils;

import io.github.nickdex.pasteit.framework.data.entity.MessageEntity;
import io.github.nickdex.pasteit.framework.domain.model.Device;

/**
 * Utility class that provide helper methods for easy mappings.
 */
public class MapperUtil {

    /**
     * Returns equivalent string of device, in uppercase.
     *
     * @param device The enum value that needs to be converted.
     * @return The string equivalent for device in uppercase.
     */
    public static String getStringForDevice(Device device) {
        return device.name().toUpperCase();
    }

    /**
     * Returns Device enum value equivalent to the deviceType.
     * In case of a invalid input, Device.GHOST is returned.
     *
     * @param deviceType The string to be converted.
     * @return Device enum value equivalent to the deviceType.
     */
    public static Device getDeviceForString(String deviceType) {
        if (TextUtils.isEmpty(deviceType)) {
            return Device.GHOST;
        }
        if (deviceType.toUpperCase().contains(MessageEntity.CHROME)) {
            return Device.CHROME;
        }
        if (deviceType.toUpperCase().contains(MessageEntity.PHONE)) {
            return Device.PHONE;
        }
        return Device.GHOST;
    }

}
