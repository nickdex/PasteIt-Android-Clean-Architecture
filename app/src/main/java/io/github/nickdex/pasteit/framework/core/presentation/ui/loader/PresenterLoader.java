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

package io.github.nickdex.pasteit.framework.core.presentation.ui.loader;

import android.content.Context;
import android.support.v4.content.Loader;

import io.github.nickdex.pasteit.framework.core.presentation.presenter.BasePresenter;

/**
 * A class that handles loading operations of a Presenter.
 *
 * @param <PRESENTER> The presenter associated with it.
 */
public abstract class PresenterLoader<PRESENTER extends BasePresenter> extends Loader<PRESENTER> {

    /**
     * The presenter that is managed by the PresenterLoader.
     */
    private PRESENTER presenter;

    protected PresenterLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        presenter = initPresenter();
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        super.onReset();
        presenter.destroy();
        presenter = null;
    }

    /**
     * Initializes a presenter for this loader.
     *
     * @return A presenter for this loader.
     */
    protected abstract PRESENTER initPresenter();
}
