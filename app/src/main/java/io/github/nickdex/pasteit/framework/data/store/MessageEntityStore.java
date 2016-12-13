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

package io.github.nickdex.pasteit.framework.data.store;

import java.util.List;

import io.github.nickdex.pasteit.framework.core.data.store.EntityStore;
import io.github.nickdex.pasteit.framework.data.entity.MessageEntity;
import rx.Observable;

/**
 * Interface that represents a data store where messages are stored.
 */
public interface MessageEntityStore extends EntityStore {

    /**
     * Returns an observable which will emit a list of all messages for the userId.
     *
     * @param userId Id of the user for whom messages need to be fetched.
     * @return The observable which will emit a list of all messages for the userId.
     */
    Observable<List<MessageEntity>> getMessages(String userId);

    /**
     * Posts a message to the data store.
     *
     * @param message The item to post.
     * @return The observable that doesn't emit anything.
     */
    Observable<Void> postMessage(MessageEntity message);
}
