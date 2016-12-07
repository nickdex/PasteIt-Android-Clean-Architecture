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

package io.github.nickdex.pasteit.data.store.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

import javax.inject.Inject;

import io.github.nickdex.pasteit.data.entity.MessageEntity;
import io.github.nickdex.pasteit.data.manager.AuthManager;
import io.github.nickdex.pasteit.data.store.MessageEntityStore;
import rx.Observable;

/**
 * A class that performs operations on messages in firebase.
 */
public class FirebaseMessageEntityStore extends FirebaseEntityStore implements MessageEntityStore {

    private static final String ROOT_MESSAGES = "clip_items";

    private AuthManager authManager;

    @Inject
    public FirebaseMessageEntityStore(AuthManager authManager) {
        this.authManager = authManager;
    }

    /**
     * Returns an observable which will emit a list of all messages present inside the given user's node in firebase.
     *
     * @param userId Id of the user for whom messages need to be fetched.
     * @return The observable which will emit a list of all messages for the userId.
     */
    @Override
    public Observable<List<MessageEntity>> getMessages(String userId) {
        Query query = database
                .child(ROOT_MESSAGES)
                .child(authManager.getCurrentUserId());
        return getList(query, MessageEntity.class, false);
    }

    /**
     * Creates a message inside the current user's node in firebase.
     *
     * @param message The item to be created.
     * @return The observable that doesn't emit anything.
     */
    @Override
    public Observable<Void> postMessage(MessageEntity message) {
        DatabaseReference reference = database
                .child(ROOT_MESSAGES)
                .child(authManager.getCurrentUserId());
        return create(reference, message, (Void) new Object());
    }
}
