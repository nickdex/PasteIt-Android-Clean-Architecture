package io.github.nickdex.pasteit.domain.repository;

import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.model.User;
import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link User} related data.
 */
public interface UserRepository extends Repository {

    Observable<String> createUserIfNotExists(User user, Messenger messenger);

    Observable<User> getUser(final String userId, Messenger messenger);

}
