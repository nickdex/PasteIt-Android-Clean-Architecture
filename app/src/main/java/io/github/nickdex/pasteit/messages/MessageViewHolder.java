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

package io.github.nickdex.pasteit.messages;

import android.content.Context;

import io.github.nickdex.pasteit.databinding.ItemMessageBinding;
import io.github.nickdex.pasteit.framework.core.presentation.ui.viewholder.BaseViewHolder;
import io.github.nickdex.pasteit.framework.presentation.util.TimeAgo;
import io.github.nickdex.pasteit.messages.model.MessageModel;
import io.github.nickdex.pasteit.messages.view.MessagesView;

/**
 * View Holder for messages.
 */
class MessageViewHolder extends BaseViewHolder<ItemMessageBinding, MessageModel> {

    private Context context;

    private MessagesView messagesView;

    MessageViewHolder(MessagesView messagesView, Context context,
                      ItemMessageBinding binding) {
        super(binding);
        this.context = context;
        this.messagesView = messagesView;
    }

    /**
     * Binds model data to the xml components.
     *
     * @param messageModel The presentation layer model object being used for binding.
     * @param position     The position of model object in RecyclerView.
     */
    @Override
    public void bind(MessageModel messageModel, int position) {
//        CardView root = (CardView) binding.getRoot();
//         phones on left side
//        if (messageModel.getDeviceType() == Device.PHONE) {
//            root.setGravity(Gravity.END);
//        } else {
//            root.setGravity(Gravity.START);
//        }
        binding.rippleLayout.setOnClickListener(v -> messagesView.copyText(messageModel, position));
        binding.deviceImageView.setImageResource(messageModel.getDeviceResId());
        binding.itemTextView.setText(messageModel.getText());
        binding.timeTextView.setText(TimeAgo.getPrettyTime(messageModel.getTimestamp(), context));
    }
}
