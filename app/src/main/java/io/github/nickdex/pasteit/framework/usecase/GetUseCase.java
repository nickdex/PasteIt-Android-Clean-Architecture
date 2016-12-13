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

package io.github.nickdex.pasteit.framework.usecase;

import javax.inject.Named;

import io.github.nickdex.pasteit.framework.domain.Messenger;
import io.github.nickdex.pasteit.framework.domain.repository.Repository;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

/**
 * Special use case for retrieving the data without requiring a request object.
 */
public abstract class GetUseCase<RESPONSE_DATA, REPOSITORY extends Repository> extends
        UseCase<Void, RESPONSE_DATA, REPOSITORY> {

    public GetUseCase(REPOSITORY repository,
                      Messenger messenger,
                      @Named("Thread") Scheduler threadScheduler,
                      @Named("PostExecution") Scheduler postExecutionScheduler) {
        super(repository, messenger, threadScheduler, postExecutionScheduler);
    }

    @Override
    public Observable<RESPONSE_DATA> buildObservable(Void aVoid) {
        return buildObservable();
    }

    public abstract Observable<RESPONSE_DATA> buildObservable();

    public void execute(Subscriber<RESPONSE_DATA> useCaseSubscriber) {
        super.execute(null, useCaseSubscriber);
    }
}

