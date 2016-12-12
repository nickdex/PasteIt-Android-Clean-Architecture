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

package io.github.nickdex.pasteit.presentation.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.databinding.ItemMessageBinding;
import io.github.nickdex.pasteit.presentation.model.MessageModel;
import io.github.nickdex.pasteit.presentation.ui.viewholder.MessageViewHolder;

/**
 * Adapter for messages.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private List<MessageModel> items = new ArrayList<>();

    public MessageAdapter() {
        // TODO: 12/12/16 Create DialogView equivalent.
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMessageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_message, parent, false);
        return new MessageViewHolder(parent.getContext(), binding);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void initItems(List<MessageModel> messages) {
        items.clear();
        items.addAll(messages);
        notifyDataSetChanged();
    }
}
