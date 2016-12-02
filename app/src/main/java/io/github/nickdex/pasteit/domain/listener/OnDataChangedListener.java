package io.github.nickdex.pasteit.domain.listener;

/**
 * Interface definition for a callback to be invoked when data is changed.
 */
public interface OnDataChangedListener<T> {

    void onDataChanged(T data);
}
