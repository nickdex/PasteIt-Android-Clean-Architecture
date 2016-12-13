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

package io.github.nickdex.pasteit.framework.core.di.components;

import dagger.Subcomponent;
import io.github.nickdex.pasteit.framework.core.di.ViewScope;
import io.github.nickdex.pasteit.framework.core.di.modules.ViewModule;
import io.github.nickdex.pasteit.login.LoginActivity;
import io.github.nickdex.pasteit.messages.MessagesActivity;
import io.github.nickdex.pasteit.splash.SplashActivity;

/**
 * Sub-component representing classes in dagger that are limited to a {@link ViewScope}.
 */
@ViewScope
@Subcomponent(modules = ViewModule.class)
public interface ViewComponent {

    void inject(MessagesActivity activity);

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);
}
