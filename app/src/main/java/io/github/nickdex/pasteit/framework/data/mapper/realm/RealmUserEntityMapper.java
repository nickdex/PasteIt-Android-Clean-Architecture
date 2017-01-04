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

package io.github.nickdex.pasteit.framework.data.mapper.realm;

import javax.inject.Inject;

import io.github.nickdex.pasteit.framework.core.data.mapper.BaseMapper;
import io.github.nickdex.pasteit.framework.data.entity.UserEntity;
import io.github.nickdex.pasteit.framework.data.entity.realm.RealmUserEntity;

/**
 * Mapper class to convert messages in Realm into UserEntity and vice versa.
 */
public class RealmUserEntityMapper extends BaseMapper<RealmUserEntity, UserEntity> {

    @Inject
    public RealmUserEntityMapper() {
    }

    /**
     * Method which converts userEntity to a Realm user entity.
     *
     * @param userEntity The user entity that contains some data.
     * @return Realm user entity mapped with data from userEntity.
     */
    @Override
    public RealmUserEntity mapToFirst(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        RealmUserEntity realmEntity = new RealmUserEntity();
        realmEntity.setId(userEntity.getId());
        realmEntity.setName(userEntity.getName());
        realmEntity.setEmail(userEntity.getEmail());
        realmEntity.setChrome(userEntity.hasChrome());
        realmEntity.setPhone(userEntity.hasPhone());
        return realmEntity;
    }

    /**
     * Method which converts Realm user entity to a clip item.
     *
     * @param realmEntity The user entity that contains data.
     * @return The user entity mapped with data from Realm user entity.
     */
    @Override
    public UserEntity mapToSecond(RealmUserEntity realmEntity) {
        if (realmEntity == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(realmEntity.getId());
        userEntity.setName(realmEntity.getName());
        userEntity.setEmail(realmEntity.getEmail());
        userEntity.setChrome(realmEntity.hasChrome());
        userEntity.setPhone(realmEntity.hasPhone());
        return userEntity;
    }
}
