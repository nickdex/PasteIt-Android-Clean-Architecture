package io.github.nickdex.pasteit.domain.repository;

import java.util.List;

import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.model.ClipItem;
import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link ClipItem} related data.
 */
public interface MessageRepository extends Repository {

    Observable<List<ClipItem>> getMessages(String peerId, Messenger messenger);

    Observable<Void> postMessage(ClipItem message, Messenger messenger);

    Observable<Void> editMessage(ClipItem editedMessage, Messenger messenger);

    Observable<Void> deleteMessage(ClipItem message, Messenger messenger);
}
