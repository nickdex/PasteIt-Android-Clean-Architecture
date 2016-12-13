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

package io.github.nickdex.pasteit.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

import dagger.Lazy;
import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.core.di.components.ViewComponent;
import io.github.nickdex.pasteit.databinding.ActivityLoginBinding;
import io.github.nickdex.pasteit.presentation.presenter.LoginPresenter;
import io.github.nickdex.pasteit.presentation.view.LoginView;
import io.github.nickdex.pasteit.presentation.view.impl.LoginViewImpl;

/**
 * A class that handle authentication operations of user.
 */
public class LoginActivity extends BaseDaggerActivity<LoginView, LoginPresenter, ActivityLoginBinding> {

    private static final int RC_SIGN_IN = 9002;

    @Inject
    Lazy<LoginPresenter> loginPresenter;

    @Inject
    GoogleApiClient googleApiClient;

    public static void launch(Context context) {
        Intent intent = getBaseStartIntent(context, LoginActivity.class, false);
        context.startActivity(intent);
    }

    public void onClickGoogleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        view.showProgress();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.googleSignIn.setOnClickListener(v -> onClickGoogleSignIn());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            view.hideProgress();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                view.showProgress(R.string.authenticating);
                GoogleSignInAccount account = result.getSignInAccount();
                presenter.signInWithGoogle(account);
            }
        }
    }

    @Override
    public void onLoadFinished() {
        super.onLoadFinished();
        googleApiClient.connect();
    }

    @Override
    public void onLoadReset() {
        super.onLoadReset();
        googleApiClient.disconnect();
    }

    @Override
    protected void injectViewComponent(ViewComponent viewComponent) {
        viewComponent.inject(this);
    }

    @Override
    protected LoginView initView() {
        return new LoginViewImpl(this) {

            @Override
            public void goToMessages() {
                MessagesActivity.launch(LoginActivity.this);
                finish();
            }
        };
    }

    @Override
    protected Lazy<LoginPresenter> initPresenter() {
        return loginPresenter;
    }

    @Override
    protected ActivityLoginBinding initBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
