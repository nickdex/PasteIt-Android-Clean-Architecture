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

package io.github.nickdex.pasteit.framework.usecase.user;

import javax.inject.Inject;
import javax.inject.Named;

import io.github.nickdex.pasteit.framework.domain.Messenger;
import io.github.nickdex.pasteit.framework.domain.listener.OnUserChangedListener;
import io.github.nickdex.pasteit.framework.domain.model.User;
import io.github.nickdex.pasteit.framework.domain.repository.UserRepository;
import io.github.nickdex.pasteit.framework.usecase.UseCase;
import rx.Observable;
import rx.Scheduler;

/**
 * Use case for getting a {@link User}.
 */
public class GetUser extends UseCase<String, User, UserRepository> implements OnUserChangedListener {

    private OnUserChangedListener userChangedListener;

    @Inject
    public GetUser(UserRepository repository,
                   Messenger messenger,
                   @Named("Thread") Scheduler threadScheduler,
                   @Named("PostExecution") Scheduler postExecutionScheduler) {
        super(repository, messenger, threadScheduler, postExecutionScheduler);
    }

    public void setUserChangedListener(OnUserChangedListener userChangedListener) {
        this.userChangedListener = userChangedListener;
    }

    @Override
    public void onDataChanged(String userId) {
        if (userChangedListener != null) {
            userChangedListener.onDataChanged(userId);
        }
    }

    @Override
    public Observable<User> buildObservable(String userId) {
        return repository.getUser(userId, messenger);
    }
}
