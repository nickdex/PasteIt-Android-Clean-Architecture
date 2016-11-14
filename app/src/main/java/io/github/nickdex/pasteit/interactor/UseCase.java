package io.github.nickdex.pasteit.interactor;

import javax.inject.Named;

import io.github.nickdex.pasteit.domain.repository.Repository;
import io.github.nickdex.pasteit.domain.Messenger;
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
 *
 * @author Nikhil Warke
 * @version 1.0
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

    public void unSubscribe() {
        if (!isUnSubscribed()) {
            subscription.clear();
        }
        repository.unregister(this);
    }
}