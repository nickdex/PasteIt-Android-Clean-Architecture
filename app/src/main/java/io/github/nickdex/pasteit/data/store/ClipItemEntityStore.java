package io.github.nickdex.pasteit.data.store;

import java.util.List;

import io.github.nickdex.pasteit.core.data.store.EntityStore;
import io.github.nickdex.pasteit.data.entity.ClipItemEntity;
import io.github.nickdex.pasteit.data.entity.UserEntity;
import rx.Observable;

/**
 * Interface that represents a data store from where clips are retrieved.
 */
public interface ClipItemEntityStore extends EntityStore {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link ClipItemEntity}.
     */
    Observable<List<ClipItemEntity>> getClips();

    /**
     * Paste a {@link ClipItemEntity}.
     *
     * @param message The clip item to paste.
     */
    Observable<Void> pasteClip(ClipItemEntity message);
}
