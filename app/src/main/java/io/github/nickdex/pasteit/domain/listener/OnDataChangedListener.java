package io.github.nickdex.pasteit.domain.listener;

/**
 * Listener for data changes.
 *
 * @author Nikhil Warke
 */
public interface OnDataChangedListener<T> {

    void onDataChanged(T data);
}
