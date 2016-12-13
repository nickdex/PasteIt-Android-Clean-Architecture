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
import rx.subscriptions.CompositeSubscription;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a {@link rx.Subscriber}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<REQUEST_DATA, RESPONSE_DATA, REPOSITORY extends Repository> {

    protected final REPOSITORY repository;

    protected final Messenger messenger;

    private final Scheduler threadScheduler;

    private final Scheduler postExecutionScheduler;

    private CompositeSubscription subscription = new CompositeSubscription();

    public UseCase(REPOSITORY repository,
                   Messenger messenger,
                   @Named("Thread") Scheduler threadScheduler,
                   @Named("PostExecution") Scheduler postExecutionScheduler) {
        this.repository = repository;
        this.messenger = messenger;
        this.threadScheduler = threadScheduler;
        this.postExecutionScheduler = postExecutionScheduler;
    }

    public abstract Observable<RESPONSE_DATA> buildObservable(REQUEST_DATA requestData);

    public void execute(REQUEST_DATA requestData, Subscriber<RESPONSE_DATA> useCaseSubscriber) {
        this.subscription.add(this.buildObservable(requestData)
                .subscribeOn(threadScheduler)
                .observeOn(postExecutionScheduler)
                .subscribe(useCaseSubscriber));
        repository.register(this);
    }

    public boolean isUnSubscribed() {
        return !subscription.hasSubscriptions();
    }

    /**
     * Clears all subscriptions and removes this use case from repository.
     */
    public void unSubscribe() {
        if (!isUnSubscribed()) {
            subscription.clear();
        }
        repository.unregister(this);
    }
}