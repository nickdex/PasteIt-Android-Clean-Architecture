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

package io.github.nickdex.pasteit.data.mapper;

import javax.inject.Inject;

import io.github.nickdex.pasteit.core.data.mapper.BaseMapper;
import io.github.nickdex.pasteit.data.entity.UserEntity;
import io.github.nickdex.pasteit.domain.model.User;

/**
 * Mapper to convert a user entity from data layer into a user in domain layer and vice versa.
 */
public class UserEntityUserDmMapper extends BaseMapper<UserEntity, User> {

    @Inject
    public UserEntityUserDmMapper() {
    }

    /**
     * Method which converts user to a user entity.
     * Default value is used when corresponding data is not found in user.
     *
     * @param user A User that contains some data.
     * @return The UserEntity mapped with data from user.
     */
    @Override
    public UserEntity mapToFirst(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }

    /**
     * Method which converts userEntity to a user.
     * Default value is used when corresponding data is not found in userEntity.
     *
     * @param userEntity A UserEntity that contains some data.
     * @return The User mapped with data from userEntity.
     */
    @Override
    public User mapToSecond(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        return user;
    }
}
