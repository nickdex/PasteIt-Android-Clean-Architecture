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

package io.github.nickdex.pasteit.framework.core.di.modules;

import dagger.Module;
import dagger.Provides;
import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.framework.core.presentation.view.View;
import io.github.nickdex.pasteit.framework.domain.Messenger;

/**
 * Dagger module that holds reference to associated view and provides the messenger to app.
 * It shows message to user such as no network or showing from cache.
 */
@Module
public class ViewModule {

    private View view;

    public ViewModule(View view) {
        this.view = view;
    }

    @Provides
    Messenger providesMessenger() {
        return new Messenger() {
            @Override
            public void showNoNetworkMessage() {
                view.showMessage(R.string.no_internet_connection);
            }

            @Override
            public void showFromCacheMessage() {
                view.showMessage(R.string.data_from_cache);
            }
        };
    }
}
