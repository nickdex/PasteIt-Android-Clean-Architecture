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

package io.github.nickdex.pasteit.presentation.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.di.ViewScope;
import io.github.nickdex.pasteit.core.presentation.DefaultSubscriber;
import io.github.nickdex.pasteit.core.presentation.presenter.BasePresenter;
import io.github.nickdex.pasteit.data.manager.AuthManager;
import io.github.nickdex.pasteit.interactor.user.CreateUser;
import io.github.nickdex.pasteit.presentation.view.LoginView;
import rx.Subscriber;

/**
 * Presenter that signs in user.
 */
@ViewScope
public class LoginPresenter extends BasePresenter<LoginView> {

    private AuthManager authManager;

    private CreateUser createUser;

    private Subscriber<String> signInSubscriber;

    @Inject
    public LoginPresenter(NetworkManager networkManager, AuthManager authManager, CreateUser createUser) {
        super(networkManager);
        this.authManager = authManager;
        this.createUser = createUser;
    }

    @Override
    protected void onViewDetached() {
        super.onViewDetached();
        if (signInSubscriber != null) {
            signInSubscriber.unsubscribe();
            signInSubscriber = null;
        }
        createUser.unSubscribe();
    }

    public void signInWithGoogle(GoogleSignInAccount account) {
        signInSubscriber = new DefaultSubscriber<String>(view) {
            @Override
            public void onError(Throwable e) {
                view.showMessage(R.string.auth_failed);
                view.hideProgress();
            }

            @Override
            public void onNext(String s) {
                view.goToMessages();
                view.hideProgress();
            }
        };
        authManager.signInGoogle(account, signInSubscriber, createUser);
    }

    @Override
    public void refreshData() {
    }
}
