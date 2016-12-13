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

package io.github.nickdex.pasteit.splash;

import javax.inject.Inject;

import io.github.nickdex.pasteit.framework.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.framework.core.di.ViewScope;
import io.github.nickdex.pasteit.framework.core.presentation.presenter.BasePresenter;
import io.github.nickdex.pasteit.framework.data.manager.AuthManager;
import io.github.nickdex.pasteit.splash.view.SplashView;

/**
 * Presenter that launch LoginActivity if no user is signed in, MessageActivity otherwise.
 */
@ViewScope
class SplashPresenter extends BasePresenter<SplashView> {

    private AuthManager authManager;

    @Inject
    public SplashPresenter(NetworkManager networkManager, AuthManager authManager) {
        super(networkManager);
        this.authManager = authManager;
    }

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        refreshData();
    }

    @Override
    public void refreshData() {
        chooseNavigation();
    }

    /**
     * Launches LoginActivity if no user is signed in, MessagesActivity otherwise
     */
    private void chooseNavigation() {
        if (authManager.isSignedIn()) {
            view.goToMessages();
        } else {
            view.goToLogin();
        }
    }
}