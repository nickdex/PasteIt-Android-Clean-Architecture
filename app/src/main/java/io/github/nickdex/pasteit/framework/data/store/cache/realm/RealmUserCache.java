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

import io.github.nickdex.pasteit.framework.data.entity.UserEntity;
import io.github.nickdex.pasteit.framework.data.entity.realm.RealmUserEntity;
import io.github.nickdex.pasteit.framework.data.mapper.realm.RealmUserEntityMapper;
import io.github.nickdex.pasteit.framework.data.store.cache.UserCache;
import io.realm.Realm;
import rx.Observable;

/**
 * Class that performs operations on realm database and acts as local cache for users.
 */
public class RealmUserCache implements UserCache {

    private Realm realm;

    private RealmUserEntityMapper mapper;

    public RealmUserCache(RealmUserEntityMapper realmUserEntityMapper) {
        realm = Realm.getDefaultInstance();
        this.mapper = realmUserEntityMapper;
    }

    @Override
    public Observable<String> createUserIfNotExists(UserEntity userEntity) {
        // Not supported
        return null;
    }

    @Override
    public Observable<UserEntity> getUser(String userId) {
        RealmUserEntity realmUserEntity = realm.where(RealmUserEntity.class).equalTo("id", userId).findFirst();
        return Observable.just(mapper.mapToSecond(realmUserEntity));
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(mapper.mapToFirst(userEntity)));
    }
}
