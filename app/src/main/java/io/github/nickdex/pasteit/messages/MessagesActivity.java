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

package io.github.nickdex.pasteit.messages;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.copy.ClipBoardManager;
import io.github.nickdex.pasteit.databinding.ActivityMessageBinding;
import io.github.nickdex.pasteit.framework.core.di.components.ViewComponent;
import io.github.nickdex.pasteit.framework.data.mapper.MapperUtil;
import io.github.nickdex.pasteit.framework.domain.model.Device;
import io.github.nickdex.pasteit.framework.presentation.BaseDaggerActivity;
import io.github.nickdex.pasteit.login.LoginActivity;
import io.github.nickdex.pasteit.messages.model.MessageModel;
import io.github.nickdex.pasteit.messages.view.MessagesView;
import io.github.nickdex.pasteit.messages.view.MessagesViewImpl;
import io.github.nickdex.pasteit.settings.SettingsActivity;

/**
 * Activity that connects MessagesView, MessagesPresenter and Layout.
 * It handles initialization operations.
 * It also acts as end point to switch activities.
 */
public class MessagesActivity extends BaseDaggerActivity<MessagesView, MessagesPresenter, ActivityMessageBinding> {

    public static final String KEY_DEVICE_TYPE = "device_type";
    public final static String COPY_ACTION = "paste.action.copy";
    @Inject
    ClipBoardManager clipboard;
    @Inject
    Lazy<MessagesPresenter> messagesPresenter;
    private BroadcastReceiver notificationListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(COPY_ACTION)) {

            }
        }
    };
    private MessageAdapter messageAdapter;

    /**
     * Method to launch this activity from context.
     *  @param context The caller to this activity.
     *
     */
    public static void launch(Context context) {
        Intent intent = getBaseStartIntent(context, MessagesActivity.class, false);
        intent.putExtra(KEY_DEVICE_TYPE, Device.CHROME);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        registerReceiver(notificationListener, new IntentFilter(COPY_ACTION));
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(notificationListener);
        super.onPause();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                presenter.signOut();
                break;
            case R.id.settings:
                SettingsActivity.launch(this);
                break;
        }
        return super.onOptionsItemSelected(item);
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
            public void copyText(MessageModel message) {
                clipboard.setClip(message.getText());
                view.showMessage(getString(R.string.copy_message));
            }

            @Override
            public void showMessage(String message) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void showNotification(MessageModel model) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                Intent intent = new Intent();
                intent.setAction(COPY_ACTION);

                PendingIntent copyIntent = PendingIntent.getActivity(MessagesActivity.this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new Notification.Builder(MessagesActivity.this)
                        .setSmallIcon(R.drawable.ic_paste_black)
                        .setContentTitle(MapperUtil.getStringForDevice(model.getDeviceType()))
                        .setContentText(model.getText())
                        .addAction(R.mipmap.ic_launcher, "Copy", copyIntent)
                        .setAutoCancel(true)
                        .build();

                notificationManager.notify(123, notification);
            }

            @Override
            public void clearInput() {
                binding.inputText.getText().clear();
            }

            @Override
            public void clearMessageNotification() {
            }

            @Override
            public void copyLatestClip(String text) {
                clipboard.setClip(text);
            }

            @Override
            public void showProgress() {
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideProgress() {
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void launchLogin() {
                LoginActivity.launch(MessagesActivity.this);
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
        Animation scaleInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_in);
        Animation scaleOutAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_out);
        binding.sendButton.startAnimation(scaleInAnim);
        binding.sendButton.setImageResource(R.drawable.ic_paste_black);
        binding.sendButton.startAnimation(scaleOutAnim);
        binding.sendButton.setOnClickListener(v -> {
            String message = clipboard.getClip();
            if (!TextUtils.isEmpty(message)) {
                binding.inputText.setText(message);
            }
        });
    }

    private void switchToSend() {
        Animation scaleInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_in);
        Animation scaleOutAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_out);
        binding.sendButton.startAnimation(scaleInAnim);
        binding.sendButton.setImageResource(R.drawable.ic_send_black);
        binding.sendButton.startAnimation(scaleOutAnim);
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
                if (s.length() > 0 && before == 0) {
                    switchToSend();
                } else if (before > 0 && s.length() == 0) {
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
