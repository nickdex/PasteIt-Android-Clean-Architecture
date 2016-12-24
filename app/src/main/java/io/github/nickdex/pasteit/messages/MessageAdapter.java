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

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.databinding.LeftItemContentBinding;
import io.github.nickdex.pasteit.databinding.RightItemContentBinding;
import io.github.nickdex.pasteit.framework.core.presentation.ui.viewholder.BaseViewHolder;
import io.github.nickdex.pasteit.framework.domain.model.Device;
import io.github.nickdex.pasteit.messages.model.MessageModel;
import io.github.nickdex.pasteit.messages.view.MessagesView;
import io.github.nickdex.pasteit.messages.viewholder.LeftMessageViewHolder;
import io.github.nickdex.pasteit.messages.viewholder.RightMessageViewHolder;

/**
 * Adapter for messages.
 */
class MessageAdapter extends RecyclerView.Adapter<BaseViewHolder<ViewDataBinding, MessageModel>> {

    private static final int LEFT = 1;
    private static final int RIGHT = 2;

    private List<MessageModel> items = new ArrayList<>();

    private MessagesView messagesView;

    MessageAdapter(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getDeviceType() == Device.PHONE) {
            return RIGHT;
        } else {
            return LEFT;
        }
    }

    @Override
    public BaseViewHolder<ViewDataBinding, MessageModel> onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == RIGHT) {
            RightItemContentBinding rightItemContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.right_item_content, parent, false);
            return new RightMessageViewHolder(messagesView, parent.getContext(), rightItemContentBinding);
        } else {
            LeftItemContentBinding leftItemContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.left_item_content, parent, false);
            return new LeftMessageViewHolder(messagesView, parent.getContext(), leftItemContentBinding);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //noinspection unchecked
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void initItems(List<MessageModel> messages) {
        items.clear();
        items.addAll(messages);
        notifyDataSetChanged();
    }
}
