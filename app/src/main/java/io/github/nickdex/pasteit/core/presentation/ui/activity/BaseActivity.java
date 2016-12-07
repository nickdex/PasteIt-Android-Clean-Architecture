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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import dagger.Lazy;
import io.github.nickdex.pasteit.core.presentation.presenter.BasePresenter;
import io.github.nickdex.pasteit.core.presentation.ui.loader.PresenterLoader;
import io.github.nickdex.pasteit.core.presentation.view.View;

public abstract class BaseActivity<VIEW extends View,
        PRESENTER extends BasePresenter> extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<PRESENTER> {

    private static final int LOADER_ID = 101;

    protected PRESENTER presenter;

    protected VIEW view;

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
        presenter.attachView(view);
    }

    public void onLoadFinished() {
    }

    @Override
    public final void onLoaderReset(Loader<PRESENTER> loader) {
        onLoadReset();
    }

    public void onLoadReset() {
    }

    public VIEW getView() {
        return view;
    }

    protected abstract VIEW initView();

    protected abstract Lazy<PRESENTER> initPresenter();
}
