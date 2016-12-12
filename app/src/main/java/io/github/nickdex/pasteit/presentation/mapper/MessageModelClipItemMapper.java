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

package io.github.nickdex.pasteit.presentation.mapper;

import javax.inject.Inject;

import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.core.data.mapper.BaseMapper;
import io.github.nickdex.pasteit.data.manager.AuthManager;
import io.github.nickdex.pasteit.domain.model.ClipItem;
import io.github.nickdex.pasteit.domain.model.Device;
import io.github.nickdex.pasteit.presentation.model.MessageModel;

/**
 * Mapper to convert a message model in presentation layer into a clip item in domain layer and vice versa.
 */
public class MessageModelClipItemMapper extends BaseMapper<MessageModel, ClipItem> {

    private AuthManager authManager;

    @Inject
    public MessageModelClipItemMapper(AuthManager manager) {
        this.authManager = manager;
    }

    /**
     * Method which converts clipItem to a message model.
     *
     * @param clipItem The clip item that contains some data.
     * @return Message model mapped with data from clipItem.
     */
    @Override
    public MessageModel mapToFirst(ClipItem clipItem) {
        if (clipItem == null) return null;

        MessageModel messageModel = new MessageModel();

        messageModel.setText(clipItem.getText());
        messageModel.setDeviceType(clipItem.getDeviceType());

        if (clipItem.getDeviceType() == Device.PHONE)
            messageModel.setDeviceResId(R.drawable.chip_smartphone);
        else if (clipItem.getDeviceType() == Device.CHROME)
            messageModel.setDeviceResId(R.drawable.chip_chrome);
        else
            messageModel.setDeviceResId(R.drawable.chip_desktop);

        messageModel.setTimestamp(clipItem.getTimestamp());
        return messageModel;
    }

    /**
     * Method which converts messageModel to a clip item.
     *
     * @param messageModel The message model that contains some data.
     * @return Message model mapped with data from clip item.
     */
    @Override
    public ClipItem mapToSecond(MessageModel messageModel) {
        if (messageModel == null) return null;

        ClipItem clipItem = new ClipItem();

        clipItem.setText(messageModel.getText());
        clipItem.setDeviceType(messageModel.getDeviceType());
        clipItem.setSenderEmail(authManager.getCurrentUserEmail());
        clipItem.setTimestamp(messageModel.getTimestamp());
        return clipItem;
    }

}
