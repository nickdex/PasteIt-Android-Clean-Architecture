package io.github.nickdex.pasteit.user;

import javax.inject.Inject;
import javax.inject.Named;

import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.model.User;
import io.github.nickdex.pasteit.domain.repository.UserRepository;
import io.github.nickdex.pasteit.interactor.UseCase;
import rx.Observable;
import rx.Scheduler;

/**
 * Creates a user
 *
 * @author Nikhil Warke
 */

public class CreateUser extends UseCase<User, String, UserRepository> {

    @Inject
    public CreateUser(UserRepository repository,
                      Messenger messenger,
                      @Named("Thread") Scheduler threadScheduler,
                      @Named("PostExecution") Scheduler postExecutionScheduler) {
        super(repository, messenger, threadScheduler, postExecutionScheduler);
    }

    @Override
    public Observable<String> buildObservable(User userDto) {
        return repository.createUserIfNotExists(userDto, messenger);
    }
}