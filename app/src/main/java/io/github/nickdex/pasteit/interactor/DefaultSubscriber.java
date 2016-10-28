package io.github.nickdex.pasteit.interactor;

import rx.Subscriber;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 *
 * @author Nikhil Warke
 * @version 1.0
 */

public class DefaultSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        // no-op by default
    }

    @Override
    public void onError(Throwable e) {
        // no-op by default
    }

    @Override
    public void onNext(T t) {
        // no-op by default
    }
}
