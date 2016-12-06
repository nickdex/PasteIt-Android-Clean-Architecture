package io.github.nickdex.pasteit.interactor.message;

import javax.inject.Inject;
import javax.inject.Named;

import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.model.ClipItem;
import io.github.nickdex.pasteit.domain.repository.MessageRepository;
import io.github.nickdex.pasteit.interactor.UseCase;
import rx.Observable;
import rx.Scheduler;

/**
 * Sends a {@link ClipItem} message to repository.
 */
public class PostMessage extends UseCase<ClipItem, Void, MessageRepository> {

    @Inject
    public PostMessage(MessageRepository repository,
                       Messenger messenger,
                       @Named("Thread") Scheduler threadScheduler,
                       @Named("PostExecution") Scheduler postExecutionScheduler) {
        super(repository, messenger, threadScheduler, postExecutionScheduler);
    }

    @Override
    public Observable<Void> buildObservable(ClipItem clipItem) {
        return repository.postMessage(clipItem, messenger);
    }

}
