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
import io.github.nickdex.pasteit.framework.core.di.AppScope;
import io.github.nickdex.pasteit.framework.data.manager.AuthManager;
import io.github.nickdex.pasteit.framework.data.store.MessageEntityStore;
import io.github.nickdex.pasteit.framework.data.store.UserEntityStore;
import io.github.nickdex.pasteit.framework.data.store.firebase.FirebaseMessageEntityStore;
import io.github.nickdex.pasteit.framework.data.store.firebase.FirebaseUserEntityStore;

/**
 * Dagger module that provides Entity stores to app.
 */
@Module
public class EntityStoreModule {

    @Provides
    @AppScope
    UserEntityStore providesUserEntityStore() {
        return new FirebaseUserEntityStore();
    }

    @Provides
    @AppScope
    MessageEntityStore providesMessageEntityStore(AuthManager manager) {
        return new FirebaseMessageEntityStore(manager);
    }

}
