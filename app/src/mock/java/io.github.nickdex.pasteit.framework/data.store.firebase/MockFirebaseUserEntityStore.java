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

package io.github.nickdex.pasteit.framework.data.store.firebase;

import javax.inject.Inject;

import io.github.nickdex.pasteit.framework.data.entity.UserEntity;
import io.github.nickdex.pasteit.framework.data.store.UserEntityStore;
import rx.Observable;

/**
 * Description
 */
public class MockFirebaseUserEntityStore implements UserEntityStore {

    @Inject
    public MockFirebaseUserEntityStore() {
    }

    @Override
    public Observable<String> createUserIfNotExists(UserEntity userEntity) {
        return Observable.just("Dummy User");
    }

    @Override
    public Observable<UserEntity> getUser(String userId) {
        return null;
    }
}
