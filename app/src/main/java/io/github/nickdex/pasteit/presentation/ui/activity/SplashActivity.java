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

import javax.inject.Inject;

import dagger.Lazy;
import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.core.di.components.ViewComponent;
import io.github.nickdex.pasteit.databinding.ActivitySplashBinding;
import io.github.nickdex.pasteit.presentation.presenter.SplashPresenter;
import io.github.nickdex.pasteit.presentation.view.SplashView;
import io.github.nickdex.pasteit.presentation.view.impl.SplashViewImpl;

/**
 * Activity that connects SplashView, SplashPresenter and Layout file.
 * It just shows the splash screen and redirects to login or messages screen.
 */
public class SplashActivity extends BaseDaggerActivity<SplashView, SplashPresenter, ActivitySplashBinding> {

    @Inject
    Lazy<SplashPresenter> presenter;

    public void launch(Context context) {
        Intent intent = getBaseStartIntent(context, SplashActivity.class, true);
        context.startActivity(intent);
    }

    @Override
    protected void injectViewComponent(ViewComponent viewComponent) {
        viewComponent.inject(this);
    }

    @Override
    protected SplashView initView() {
        return new SplashViewImpl(this) {
            @Override
            public void goToMessages() {
                MessagesActivity.launch(SplashActivity.this);
            }

            @Override
            public void goToLogin() {
                // TODO: 12/12/16 Uncomment
//                LoginActivity.launch(SplashActivity.this);
            }
        };
    }

    @Override
    protected Lazy<SplashPresenter> initPresenter() {
        return presenter;
    }

    @Override
    protected ActivitySplashBinding initBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }
}
