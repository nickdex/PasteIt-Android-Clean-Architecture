package io.github.nickdex.pasteit.interactor;

import javax.inject.Named;

import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.repository.Repository;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

/**
 * Special use case for just retrieving data
 *
 * @author Nikhil Warke
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

