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

import java.util.List;

import javax.inject.Inject;

import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.framework.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.framework.core.di.ViewScope;
import io.github.nickdex.pasteit.framework.core.presentation.DefaultSubscriber;
import io.github.nickdex.pasteit.framework.core.presentation.presenter.BasePresenter;
import io.github.nickdex.pasteit.framework.data.manager.AuthManager;
import io.github.nickdex.pasteit.framework.domain.model.ClipItem;
import io.github.nickdex.pasteit.framework.domain.model.Device;
import io.github.nickdex.pasteit.framework.usecase.message.GetMessages;
import io.github.nickdex.pasteit.framework.usecase.message.PasteClip;
import io.github.nickdex.pasteit.messages.model.MessageModel;
import io.github.nickdex.pasteit.messages.model.MessageModelClipItemMapper;
import io.github.nickdex.pasteit.messages.view.MessagesView;
import rx.Subscriber;

/**
 * Contain simple methods to do operations on message.
 * Ties data to the lifecycle of the view.
 */
@ViewScope
class MessagesPresenter extends BasePresenter<MessagesView> {

    private GetMessages getMessages;
    private PasteClip pasteClip;

    private MessageModelClipItemMapper mapper;

    private Device device;

    private Subscriber<String> signOutSubscriber;

    private AuthManager authManager;

    @Inject
    public MessagesPresenter(NetworkManager networkManager,
                             GetMessages getMessages,
                             PasteClip pasteClip,
                             MessageModelClipItemMapper mapper,
                             AuthManager authManager) {
        super(networkManager);
        this.getMessages = getMessages;
        this.pasteClip = pasteClip;
        this.mapper = mapper;
        this.authManager = authManager;
    }

    void sendMessage(String message) {
        MessageModel model = new MessageModel();
        model.setDeviceType(Device.PHONE);
        model.setText(message);
        model.setDeviceResId(R.drawable.chip_smartphone_black);
        model.setTimestamp(System.currentTimeMillis());
        pasteClip.execute(mapper.mapToSecond(model), new DefaultSubscriber<Void>(view) {
            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
            }

            @Override
            public void onNext(Void aVoid) {
                view.showMessage(R.string.message_sent);
                view.clearInput();
            }
        });
    }

    private void getMessages() {
        view.showProgress();
        getMessages.execute(getDevice(), new DefaultSubscriber<List<ClipItem>>(view) {

            @Override
            public void onError(Throwable e) {
                view.showMessage(e.getMessage());
                view.hideProgress();
            }

            @Override
            public void onNext(List<ClipItem> clipItems) {
                List<MessageModel> modelList = mapper.mapToFirst(clipItems);
                view.renderMessages(modelList);
                view.showNotification(modelList.get(modelList.size() - 1));
                view.copyLatestClip(clipItems.get(clipItems.size() - 1).getText());
                view.hideProgress();
            }
        });
    }

    private void setTitle() {
        view.setTitle(getDevice().name());
    }

    private void copyFromNotification(String text) {
        view.copyLatestClip(text);
    }

    @Override
    public void refreshData() {
        getMessages();
        setTitle();
    }

    @Override
    protected void onViewDetached() {
        super.onViewDetached();
        getMessages.unSubscribe();
        pasteClip.unSubscribe();

        if (signOutSubscriber != null) {
            signOutSubscriber.unsubscribe();
            signOutSubscriber = null;
        }
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        refreshData();
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    void signOut() {
        signOutSubscriber = new DefaultSubscriber<String>(view) {
            @Override
            public void onNext(String userId) {
                view.launchLogin();
            }

            @Override
            public void onError(Throwable e) {
                view.showMessage("can't sign out");
            }
        };
        authManager.signOut(signOutSubscriber);
    }
}
