package io.github.nickdex.pasteit.data.store;

import java.util.List;

import io.github.nickdex.pasteit.core.data.store.EntityStore;
import io.github.nickdex.pasteit.data.entity.MessageEntity;
import rx.Observable;

/**
 * Interface that represents a data store from where clips are retrieved.
 */
public interface MessageEntityStore extends EntityStore {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link MessageEntity}.
     */
    Observable<List<MessageEntity>> getMessages();

    /**
     * Paste a {@link MessageEntity}.
     *
     * @param message The clip item to paste.
     */
    Observable<Void> postMessage(MessageEntity message);
}
