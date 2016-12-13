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

package io.github.nickdex.pasteit.framework.data.mapper;

import android.text.TextUtils;

import javax.inject.Inject;

import io.github.nickdex.pasteit.framework.core.data.mapper.BaseMapper;
import io.github.nickdex.pasteit.framework.data.entity.MessageEntity;
import io.github.nickdex.pasteit.framework.domain.model.ClipItem;
import io.github.nickdex.pasteit.framework.domain.model.Device;

/**
 * Mapper to convert a message entity in data layer into a clip item in domain layer and vice versa.
 */
public class MessageEntityClipItemMapper extends BaseMapper<MessageEntity, ClipItem> {

    @Inject
    public MessageEntityClipItemMapper() {
    }

    /**
     * Method which converts clipItem to a message entity.
     *
     * @param clipItem The clip item that contains some data.
     * @return Message entity mapped with data from clipItem.
     */
    @Override
    public MessageEntity mapToFirst(ClipItem clipItem) {
        if (clipItem == null) return null;

        MessageEntity entity = new MessageEntity();

        entity.setText(clipItem.getText());
        Device device = clipItem.getDeviceType();
        if (device != null) {
            entity.setDeviceType(getStringForDevice(device));
        }
        entity.setSenderEmail(clipItem.getSenderEmail());
        // null is handled by entity store.
        entity.setId(null);
        entity.setTimestamp(clipItem.getTimestamp());
        return entity;
    }

    /**
     * Method which converts messageEntity to a clip item.
     *
     * @param messageEntity The message entity that contains data.
     * @return The clip item mapped with data from messageEntity.
     */
    @Override
    public ClipItem mapToSecond(MessageEntity messageEntity) {
        if (messageEntity == null) return null;

        ClipItem clipItem = new ClipItem();

        clipItem.setText(messageEntity.getText());
        clipItem.setDeviceType(getDeviceForString(messageEntity.getDeviceType()));
        clipItem.setSenderEmail(messageEntity.getSenderEmail());
        clipItem.setTimestamp(messageEntity.getTimestamp());
        return clipItem;
    }

    /**
     * Returns equivalent string of device, in uppercase.
     *
     * @param device The enum value that needs to be converted.
     * @return The string equivalent for device in uppercase.
     */
    public String getStringForDevice(Device device) {
        return device.name().toUpperCase();
    }

    /**
     * Returns Device enum value equivalent to the deviceType.
     * In case of a invalid input, Device.GHOST is returned.
     *
     * @param deviceType The string to be converted.
     * @return Device enum value equivalent to the deviceType.
     */
    private Device getDeviceForString(String deviceType) {
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
