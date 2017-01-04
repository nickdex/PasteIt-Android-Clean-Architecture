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

package io.github.nickdex.pasteit.framework.data.store.cache;

import java.util.List;

import io.github.nickdex.pasteit.framework.core.data.store.cache.Cache;
import io.github.nickdex.pasteit.framework.data.entity.MessageEntity;
import io.github.nickdex.pasteit.framework.data.store.MessageEntityStore;

/**
 * Interface for cache of messages.
 */
public interface MessageCache extends MessageEntityStore, Cache {

    /**
     * Saves the messages to cache.
     *
     * @param messageEntities List of messages to be cached.
     */
    void saveMessages(List<MessageEntity> messageEntities);

}
