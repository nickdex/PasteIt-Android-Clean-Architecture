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
public abstract class DefaultSubscriber<T> extends Subscriber<T> {

    private View view;

    public DefaultSubscriber(View view) {
        this.view = view;
    }

    @Override
    public void onCompleted() {
        if (view != null) {
            view.hideProgress();
        }
    }

    @Override
    public abstract void onError(Throwable e);

    @Override
    public abstract void onNext(T t);
}
