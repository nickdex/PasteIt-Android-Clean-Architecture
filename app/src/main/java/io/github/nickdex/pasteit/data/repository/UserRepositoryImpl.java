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

package io.github.nickdex.pasteit.data.repository;

import java.util.Collection;

import javax.inject.Inject;

import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.repository.RepositoryImpl;
import io.github.nickdex.pasteit.data.entity.UserEntity;
import io.github.nickdex.pasteit.data.mapper.UserEntityUserDmMapper;
import io.github.nickdex.pasteit.data.store.UserEntityStore;
import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.listener.OnUserChangedListener;
import io.github.nickdex.pasteit.domain.model.User;
import io.github.nickdex.pasteit.domain.repository.UserRepository;
import io.github.nickdex.pasteit.interactor.UseCase;
import rx.Observable;

/**
 * A Class that performs operations on User.
 */
public class UserRepositoryImpl extends RepositoryImpl<UserEntityStore, UserEntityUserDmMapper> implements UserRepository, OnUserChangedListener {

    @Inject
    public UserRepositoryImpl(NetworkManager networkManager,
                              UserEntityStore cloudStore,
                              UserEntityUserDmMapper userEntityUserDmMapper) {
        super(networkManager, cloudStore, userEntityUserDmMapper);
    }


    /**
     * A method to create the user, if it doesn't already exists in repository.
     *
     * @param user      The user that has to be created.
     * @param messenger Shows 'No Internet Connection' message to the user.
     * @return The unique userId of the user created or found in repository.
     */
    @Override
    public Observable<String> createUserIfNotExists(User user, Messenger messenger) {
        if (networkManager.isNetworkAvailable()) {
            return cloudStore.createUserIfNotExists(entityDmMapper.mapToFirst(user));
        } else {
            return Observable.<String>empty().doOnCompleted(messenger::showNoNetworkMessage);
        }
    }

    /**
     * Fetches a User by its userId.
     *
     * @param userId    The unique id of the User to be returned.
     * @param messenger Doesn't show any messages yet.
     * @return The User that was found.
     */
    @Override
    public Observable<User> getUser(String userId, Messenger messenger) {
        Observable<UserEntity> entityObservable = cloudStore.getUser(userId);
        return entityObservable.map(userEntity -> entityDmMapper.mapToSecond(userEntity));
    }


    @Override
    public void onDataChanged(String userId) {
        Collection<UseCase> useCases = useCasesMap.values();
        for (UseCase useCase : useCases) {
            if (useCase instanceof OnUserChangedListener) {
                ((OnUserChangedListener) useCase).onDataChanged(userId);
            }
        }
    }
}
