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

package io.github.nickdex.pasteit.presentation.view;

import java.util.List;

import io.github.nickdex.pasteit.core.presentation.view.View;
import io.github.nickdex.pasteit.presentation.model.MessageModel;

/**
 * Interface that have methods for all specific for to messages viewing.
 * It also has methods for handling user events on UI components.
 */
public interface MessagesView extends View {

    /**
     * Pass message to adapter and and scroll to appropriate position.
     *
     * @param messages The data to be rendered.
     */
    void renderMessages(List<MessageModel> messages);

    /**
     * Sets the title of the activity.
     *
     * @param title The title to be set.
     */
    void setTitle(String title);

    /**
     * Copies text to clipboard on a user event.
     *
     * @param message  The model that has content to be copied.
     * @param position The position of the message model.
     */
    void copyText(MessageModel message, int position);

    /**
     * Clears the input text field.
     */
    void clearInput();

    /**
     * Clears the notification(s).
     */
    void clearMessageNotification();
}
