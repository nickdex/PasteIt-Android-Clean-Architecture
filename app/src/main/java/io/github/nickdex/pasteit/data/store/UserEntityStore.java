package io.github.nickdex.pasteit.data.store;

import io.github.nickdex.pasteit.core.data.store.EntityStore;
import io.github.nickdex.pasteit.data.entity.UserEntity;
import rx.Observable;

/**
 * Interface that represents a data store which can perform operations related to {@link UserEntity}.
 */
public interface UserEntityStore extends EntityStore {

    /**
     * Creates a {@link UserEntity}.
     *
     * @param userEntity {@link UserEntity} to be created.
     * @return {@link rx.Observable} which will emit unique id of the userEntity that was created.
     */
    Observable<String> createUserIfNotExists(UserEntity userEntity);

    /**
     * Fetches a {@link UserEntity} by its id.
     *
     * @param userId The id to retrieve user data.
     * @return {@link rx.Observable} which will emit a {@link UserEntity} for the given userId.
     */
    Observable<UserEntity> getUser(String userId);
}
