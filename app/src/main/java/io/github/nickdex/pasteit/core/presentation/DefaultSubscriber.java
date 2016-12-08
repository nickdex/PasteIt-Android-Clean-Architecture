/*
 * Copyright © 2016 Nikhil Warke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.nickdex.pasteit.core.presentation;

import io.github.nickdex.pasteit.core.presentation.view.View;
import rx.Subscriber;

/**
 * The subscriber used to for executing use cases in presentation layer.
 * It is used for performing certain actions as use case execute.
 */
public class DefaultSubscriber<T> extends Subscriber<T> {

    private View view;

    public DefaultSubscriber(View view) {
        this.view = view;
    }

    /**
     * Notifies the Observer that the {@link rx.Observable} has finished sending push-based notifications
     * and hides the visual indicator of progress of the view.
     * <p>
     * The {@link rx.Observable} will not call this method if it calls {@link #onError}.
     */
    @Override
    public void onCompleted() {
        if (view != null) {
            view.hideProgress();
        }
    }

    /**
     * Notifies the Observer that the {@link rx.Observable} has experienced an error condition.
     * <p>
     * If the {@link rx.Observable} calls this method, it will not thereafter call {@link #onNext} or
     * {@link #onCompleted}.
     *
     * @param e the exception encountered by the rx.Observable
     */
    @Override
    public void onError(Throwable e) {

    }

    /**
     * Provides the Observer with a new item to observe.
     * <p>
     * The {@link rx.Observable} may call this method 0 or more times.
     * <p>
     * The {@code rx.Observable} will not call this method again after it calls either {@link #onCompleted} or
     * {@link #onError}.
     *
     * @param t the item emitted by the rx.Observable
     */
    @Override
    public void onNext(T t) {

    }
}
