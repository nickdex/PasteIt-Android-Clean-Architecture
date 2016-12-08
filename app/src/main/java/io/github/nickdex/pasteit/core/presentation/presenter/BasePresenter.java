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

package io.github.nickdex.pasteit.core.presentation.presenter;

import android.support.annotation.NonNull;

import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.presentation.view.View;

/**
 * Base class for Presenters.
 *
 * @param <VIEW> The view that is attached to this presenter.
 */
public abstract class BasePresenter<VIEW extends View> {

    protected NetworkManager networkManager;

    /**
     * The view that is associated with the presenter.
     */
    protected VIEW view;

    public BasePresenter(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    /**
     * Attaches the given view and calls onViewAttached.
     * A listener is added to network manager, for refreshing the data, when it detects that network is available.
     *
     * @param view The view associated with the presenter.
     */
    public void attachView(@NonNull VIEW view) {
        this.view = view;
        onViewAttached();
        networkManager.add(toString(), this::refreshData);
    }

    /**
     * Removes the attached view and calls onViewDetached.
     * Listener from Network manager is removed as well.
     */
    public void detachView() {
        networkManager.remove(toString());
        onViewDetached();
        this.view = null;
    }

    /**
     * The method that is tied to resume method of attached view.
     */
    public void resume() {
    }

    /**
     * The method that is tied to pause method of attached view.
     */
    public void pause() {
    }

    /**
     * A method that calls onDestroyed method.
     */
    public void destroy() {
        onDestroyed();
    }

    /**
     * A method that updates the view with fresh values.
     */
    public abstract void refreshData();

    /**
     * The method called after the view is attached.
     */
    protected void onViewAttached() {
    }

    /**
     * The method called after the view is detached.
     */
    protected void onViewDetached() {
    }

    /**
     * The method called after the view is destroyed.
     */
    protected void onDestroyed() {
    }
}
