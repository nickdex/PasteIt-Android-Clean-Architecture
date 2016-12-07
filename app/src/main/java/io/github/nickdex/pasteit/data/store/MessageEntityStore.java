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

package io.github.nickdex.pasteit.data.store;

import java.util.List;

import io.github.nickdex.pasteit.core.data.store.EntityStore;
import io.github.nickdex.pasteit.data.entity.MessageEntity;
import rx.Observable;

/**
 * Interface that represents a data store from where clips are retrieved.
 */
public interface MessageEntityStore extends EntityStore {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link MessageEntity}.
     */
    Observable<List<MessageEntity>> getMessages();

    /**
     * Paste a {@link MessageEntity}.
     *
     * @param message The clip item to paste.
     */
    Observable<Void> postMessage(MessageEntity message);
}
