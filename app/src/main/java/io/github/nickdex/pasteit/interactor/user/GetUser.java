package io.github.nickdex.pasteit.interactor.user;

import javax.inject.Inject;
import javax.inject.Named;

import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.listener.OnUserChangedListener;
import io.github.nickdex.pasteit.domain.model.User;
import io.github.nickdex.pasteit.domain.repository.UserRepository;
import io.github.nickdex.pasteit.interactor.UseCase;
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
