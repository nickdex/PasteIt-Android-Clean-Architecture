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

package io.github.nickdex.pasteit.core.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import dagger.Lazy;
import io.github.nickdex.pasteit.core.presentation.presenter.BasePresenter;
import io.github.nickdex.pasteit.core.presentation.ui.loader.PresenterLoader;
import io.github.nickdex.pasteit.core.presentation.view.View;

/**
 * Base class for all activities.
 *
 * @param <VIEW> The view associated with the activity. It is used to render data on screen.
 * @param <PRESENTER> The presenter that controls the events.
 * @param <BINDING> The binding that joins layouts with data from models.
 */
public abstract class BaseActivity<VIEW extends View,
        PRESENTER extends BasePresenter,
        BINDING extends ViewDataBinding> extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<PRESENTER> {

    private static final int LOADER_ID = 101;

    protected PRESENTER presenter;

    protected VIEW view;

    protected BINDING binding;

    /**
     * A method to launch activity as start of a new task on the history stack.
     *
     * @param context The context to launch activity.
     * @param activityClass The activityClass that is to be launched.
     * @param clearStack The flag to make new activity the root of the task and finish any old activities.
     * @return The intent to launch activity as start of a new task on the history stack
     */
    protected static Intent getBaseStartIntent(Context context, Class<? extends BaseActivity> activityClass, boolean clearStack) {
        Intent intent = new Intent(context, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (clearStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initBinding();
        view = initView();

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onPause() {
        presenter.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    @Override
    public Loader<PRESENTER> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<PRESENTER>(this) {

            @Override
            protected PRESENTER initPresenter() {
                return BaseActivity.this.initPresenter().get();
            }
        };
    }

    @Override
    public final void onLoadFinished(Loader<PRESENTER> loader, PRESENTER presenter) {
        this.presenter = presenter;
        onLoadFinished();
        //noinspection unchecked
        presenter.attachView(view);
    }

    /**
     * Called when a previously created loader has finished its load.
     */
    public void onLoadFinished() {
    }

    @Override
    public final void onLoaderReset(Loader<PRESENTER> loader) {
        onLoadReset();
    }

    /**
     * Called when a previously created loader is being reset, and thus making its data unavailable.
     * The application should at this point remove any references it has to the Loader's data.
     */
    public void onLoadReset() {
    }

    /**
     * The view that is associated with this activity.
     *
     * @return The view that is associated with this activity.
     */
    public VIEW getView() {
        return view;
    }

    /**
     * The view to be associated with this activity.
     *
     * @return The view to be associated with this activity.
     */
    protected abstract VIEW initView();

    /**
     * A presenter associated with this activity that can be lazily loaded.
     *
     * @return A presenter that is passed to the loader.
     */
    protected abstract Lazy<PRESENTER> initPresenter();

    /**
     * A {@link ViewDataBinding} class associated with this activity.
     *
     * @return The binding class associated with this activity.
     */
    protected abstract BINDING initBinding();
}
