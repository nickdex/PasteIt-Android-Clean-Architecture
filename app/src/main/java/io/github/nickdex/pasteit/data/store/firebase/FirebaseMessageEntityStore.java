package io.github.nickdex.pasteit.data.store.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.List;

import javax.inject.Inject;

import io.github.nickdex.pasteit.data.entity.MessageEntity;
import io.github.nickdex.pasteit.data.manager.AuthManager;
import io.github.nickdex.pasteit.data.store.MessageEntityStore;
import rx.Observable;

/**
 * A class that contains implementations of {@link MessageEntityStore} methods.
 */
public class FirebaseMessageEntityStore extends FirebaseEntityStore implements MessageEntityStore {

    private static final String ROOT_MESSAGES = "clip_items";

    private AuthManager authManager;

    @Inject
    public FirebaseMessageEntityStore(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public Observable<List<MessageEntity>> getMessages() {
        Query query = database
                .child(ROOT_MESSAGES)
                .child(authManager.getCurrentUserId());
        return getList(query, MessageEntity.class, false);
    }

    @Override
    public Observable<Void> postMessage(MessageEntity message) {
        DatabaseReference reference = database
                .child(ROOT_MESSAGES)
                .child(authManager.getCurrentUserId());
        return create(reference, message, null);
    }
}
