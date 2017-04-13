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

package io.github.nickdex.pasteit.framework.data.store.cache.realm;

import java.util.List;

import io.github.nickdex.pasteit.framework.data.entity.MessageEntity;
import io.github.nickdex.pasteit.framework.data.entity.realm.RealmMessageEntity;
import io.github.nickdex.pasteit.framework.data.mapper.realm.RealmMessageEntityMapper;
import io.github.nickdex.pasteit.framework.data.store.cache.MessageCache;
import io.realm.Realm;
import rx.Observable;

/**
 * Class that performs operations on realm database and acts as local cache for messages.
 */
public class RealmMessageCache implements MessageCache {

    private Realm realm;

    private RealmMessageEntityMapper mapper;

    public RealmMessageCache(RealmMessageEntityMapper realmMessageEntityMapper) {
        realm = Realm.getDefaultInstance();
        this.mapper = realmMessageEntityMapper;
    }

    /**
     * Save messages in cache by deleting all old object and then copying it to realm.
     *
     * @param messageEntities List of messages to be cached.
     */
    @Override
    public void saveMessages(List<MessageEntity> messageEntities) {
        realm.executeTransaction(realm1 -> {
            realm1.delete(RealmMessageEntity.class);
            realm1.copyToRealm(mapper.mapToFirst(messageEntities));
        });
    }

    /**
     * Returns list of all messages found in Realm.
     *
     * @return List of all messages found in Realm.
     */
    @Override
    public Observable<List<MessageEntity>> getMessages() {
        List<MessageEntity> messageEntities = mapper.mapToSecond(
                realm.where(RealmMessageEntity.class).findAll()
        );
        return Observable.just(messageEntities);
    }

    @Override
    public Observable<Void> postMessage(MessageEntity message) {
        // TODO: 4/1/17 add this feature
        return null;
    }
}
