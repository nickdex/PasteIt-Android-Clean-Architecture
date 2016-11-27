package io.github.nickdex.pasteit.data.store;

import java.util.List;

import io.github.nickdex.pasteit.core.data.store.EntityStore;
import io.github.nickdex.pasteit.data.entity.UserEntity;
import rx.Observable;

/**
 * Interface that represents a data store from where user is retrieved.
 */
public interface UserEntityStore extends EntityStore {

    /**
     * Creates a {@link UserEntity}.
     *
     * @param userEntity User Entity to be created.
     */
    Observable<String> createUserIfNotExists(UserEntity userEntity);

    /**
     * Get an {@link rx.Observable} which will emit a {@link UserEntity} by its id.
     *
     * @param userId The id to retrieve user data.
     */
    Observable<UserEntity> getUser(String userId);
}
