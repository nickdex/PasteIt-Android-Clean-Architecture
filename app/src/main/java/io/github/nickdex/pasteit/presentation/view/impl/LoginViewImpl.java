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

package io.github.nickdex.pasteit.presentation.view.impl;

import io.github.nickdex.pasteit.core.presentation.ui.activity.BaseActivity;
import io.github.nickdex.pasteit.core.presentation.view.impl.ViewImpl;
import io.github.nickdex.pasteit.presentation.view.LoginView;

/**
 * A class that combines ViewImpl and SplashView.
 */
public abstract class LoginViewImpl extends ViewImpl implements LoginView {

    public LoginViewImpl(BaseActivity activity) {
        super(activity);
    }
}
