package io.github.nickdex.pasteit.data.store.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import javax.inject.Inject;

import io.github.nickdex.pasteit.data.entity.UserEntity;
import io.github.nickdex.pasteit.data.store.UserEntityStore;
import rx.Observable;

/**
 * Handles {@link UserEntityStore} operation on Firebase.
 */
public class FirebaseUserEntityStore extends FirebaseEntityStore implements UserEntityStore {

    private static final String CHILD_USERS = "users";

    @Inject
    public FirebaseUserEntityStore() {
    }

    @Override
    public Observable<String> createUserIfNotExists(UserEntity userEntity) {
        DatabaseReference reference = database.child(CHILD_USERS).child(userEntity.getId());
        return createIfNotExists(reference, userEntity, userEntity.getId());
    }

    @Override
    public Observable<UserEntity> getUser(String userId) {
        Query query = database.child(CHILD_USERS).orderByKey();
        return get(query, UserEntity.class, true);
    }
}
