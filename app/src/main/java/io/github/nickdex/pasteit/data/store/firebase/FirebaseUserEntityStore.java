package io.github.nickdex.pasteit.data.store.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import javax.inject.Inject;

import io.github.nickdex.pasteit.data.entity.UserEntity;
import io.github.nickdex.pasteit.data.store.UserEntityStore;
import rx.Observable;

/**
 * A class that contains implementation of {@link UserEntityStore}.
 */
public class FirebaseUserEntityStore extends FirebaseEntityStore implements UserEntityStore {

    private static final String ROOT_USERS = "users";

    @Inject
    public FirebaseUserEntityStore() {
    }

    @Override
    public Observable<String> createUserIfNotExists(UserEntity userEntity) {
        DatabaseReference reference = database.child(ROOT_USERS).child(userEntity.getId());
        return createIfNotExists(reference, userEntity, userEntity.getId()).doOnNext(s -> updatePhone(reference, userEntity));
    }

    @Override
    public Observable<UserEntity> getUser(String userId) {
        Query query = database.child(ROOT_USERS).child(ROOT_USERS).child(userId);
        return get(query, UserEntity.class, true);
    }

    /**
     * Sets the phone property of {@link UserEntity} to true after createIfNotExists runs to create user.
     *
     * @param reference  {@link DatabaseReference} of the path to userEntity.
     * @param userEntity {@link UserEntity} that is to be updated.
     */
    private void updatePhone(DatabaseReference reference, UserEntity userEntity) {
        userEntity.setPhone(true);
        update(reference, userEntity, userEntity.getId());
    }

}
