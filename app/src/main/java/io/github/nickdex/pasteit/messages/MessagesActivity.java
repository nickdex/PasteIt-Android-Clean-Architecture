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

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.copy.ClipBoardManager;
import io.github.nickdex.pasteit.databinding.ActivityMessageBinding;
import io.github.nickdex.pasteit.framework.core.di.components.ViewComponent;
import io.github.nickdex.pasteit.framework.domain.model.Device;
import io.github.nickdex.pasteit.framework.presentation.BaseDaggerActivity;
import io.github.nickdex.pasteit.messages.model.MessageModel;
import io.github.nickdex.pasteit.messages.view.MessagesView;
import io.github.nickdex.pasteit.messages.view.MessagesViewImpl;

/**
 * Activity that connects MessagesView, MessagesPresenter and Layout.
 * It handles initialization operations.
 * It also acts as end point to switch activities.
 */
public class MessagesActivity extends BaseDaggerActivity<MessagesView, MessagesPresenter, ActivityMessageBinding> {

    public static final String KEY_DEVICE_TYPE = "device_type";

    @Inject
    ClipBoardManager clipboard;

    @Inject
    Lazy<MessagesPresenter> messagesPresenter;

    private MessageAdapter messageAdapter;

    /**
     * Method to launch this activity from context.
     *  @param context The caller to this activity.
     *
     */
    public static void launch(Context context) {
        Intent intent = getBaseStartIntent(context, MessagesActivity.class, false);
        // TODO: 12/12/16 Get device from navigation drawer
        intent.putExtra(KEY_DEVICE_TYPE, Device.CHROME);
        context.startActivity(intent);
    }

    @Override
    public void onLoadFinished() {
        super.onLoadFinished();
        initUi();
        initDevice();
    }

    private void initDevice() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Device device = (Device) bundle.get(KEY_DEVICE_TYPE);
            presenter.setDevice(device);
        }
    }

    @Override
    protected MessagesView initView() {
        return new MessagesViewImpl(this) {
            @Override
            public void renderMessages(List<MessageModel> messages) {
                messageAdapter.initItems(messages);
                if (messageAdapter.getItemCount() > 0) {
                    binding.messagesList.scrollToPosition(messageAdapter.getItemCount() - 1);
                }
            }

            @Override
            public void setTitle(String title) {
                MessagesActivity.this.setTitle(title);
            }

            @Override
            public void copyText(MessageModel message, int position) {
                // TODO: 12/12/16 Copy the text to clipboard
                view.showMessage("Copy N/A");
            }

            @Override
            public void clearInput() {
                binding.inputText.getText().clear();
            }

            @Override
            public void clearMessageNotification() {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                // TODO: 12/12/16 Add logic to cancel notification
                // notificationManager.cancel(message.hashCode());
                view.showMessage("Clear Notif N/A");
            }

            @Override
            public void showProgress() {
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideProgress() {
                binding.progressBar.setVisibility(View.GONE);
            }
        };
    }

    private void initUi() {
        switchToPaste();
        initMessageRecyclerView();
        initInputTextField();

        view.clearMessageNotification();
    }

    private void initMessageRecyclerView() {
        messageAdapter = new MessageAdapter(view);
        binding.messagesList.setAdapter(messageAdapter);
        binding.messagesList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void switchToPaste() {
        binding.sendButton.setImageResource(R.drawable.ic_paste_black);
        binding.sendButton.setOnClickListener(v -> {
            String message = clipboard.getClip();
            if (!TextUtils.isEmpty(message)) {
                binding.inputText.setText(message);
            }
        });
    }

    private void switchToSend() {
        binding.sendButton.setImageResource(R.drawable.ic_send_black);
        binding.sendButton.setOnClickListener(v -> {
            String message = binding.inputText.getText().toString();
            if (!TextUtils.isEmpty(message)) {
                presenter.sendMessage(message);
            }
        });
    }

    private void initInputTextField() {
        binding.inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    switchToSend();
                } else {
                    switchToPaste();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void injectViewComponent(ViewComponent viewComponent) {
        viewComponent.inject(this);
    }

    @Override
    protected Lazy<MessagesPresenter> initPresenter() {
        return messagesPresenter;
    }

    @Override
    protected ActivityMessageBinding initBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_message);
    }
}
