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

package io.github.nickdex.pasteit.framework.core.di.modules;

import dagger.Module;
import dagger.Provides;
import io.github.nickdex.pasteit.framework.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.framework.core.di.AppScope;
import io.github.nickdex.pasteit.framework.data.mapper.MessageEntityClipItemMapper;
import io.github.nickdex.pasteit.framework.data.mapper.UserEntityUserDmMapper;
import io.github.nickdex.pasteit.framework.data.repository.MessageRepositoryImpl;
import io.github.nickdex.pasteit.framework.data.repository.UserRepositoryImpl;
import io.github.nickdex.pasteit.framework.data.store.MessageEntityStore;
import io.github.nickdex.pasteit.framework.data.store.UserEntityStore;
import io.github.nickdex.pasteit.framework.data.store.cache.MessageCache;
import io.github.nickdex.pasteit.framework.data.store.cache.UserCache;
import io.github.nickdex.pasteit.framework.domain.repository.MessageRepository;
import io.github.nickdex.pasteit.framework.domain.repository.UserRepository;

/**
 * Dagger module that provides repositories to app.
 */
@Module
public class RepositoryModule {

    @Provides
    @AppScope
    UserRepository providesUserRepository(NetworkManager networkManager,
                                          UserEntityStore userEntityStore,
                                          UserCache cache,
                                          UserEntityUserDmMapper mapper) {
        return new UserRepositoryImpl(networkManager, userEntityStore, cache, mapper);
    }

    @Provides
    @AppScope
    MessageRepository providesMessageRepository(NetworkManager networkManager,
                                                MessageEntityStore messageEntityStore,
                                                MessageCache cache,
                                                MessageEntityClipItemMapper mapper) {
        return new MessageRepositoryImpl(networkManager, messageEntityStore, cache, mapper);
    }
}
