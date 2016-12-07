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

import io.github.nickdex.pasteit.core.data.store.EntityStore;
import io.github.nickdex.pasteit.data.entity.UserEntity;
import rx.Observable;

/**
 * Interface that represents a data store which can perform operations related to {@link UserEntity}.
 */
public interface UserEntityStore extends EntityStore {

    /**
     * Creates a {@link UserEntity}.
     *
     * @param userEntity {@link UserEntity} to be created.
     * @return {@link rx.Observable} which will emit unique id of the userEntity that was created.
     */
    Observable<String> createUserIfNotExists(UserEntity userEntity);

    /**
     * Fetches a {@link UserEntity} by its id.
     *
     * @param userId The id to retrieve user data.
     * @return {@link rx.Observable} which will emit a {@link UserEntity} for the given userId.
     */
    Observable<UserEntity> getUser(String userId);
}
