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

package io.github.nickdex.pasteit.core.presentation.view.impl;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.core.presentation.presenter.BasePresenter;
import io.github.nickdex.pasteit.core.presentation.util.ProgressDialogHelper;

/**
 * A class that handles various operations like progress, showing messages on a View.
 * Activity, Fragment, View and Service are supported.
 */
public abstract class ViewImpl implements io.github.nickdex.pasteit.core.presentation.view.View {

    private Activity activity;

    private Fragment fragment;

    private View view;

    private Service service;

    private ProgressDialogHelper progressDialogHelper;

    public ViewImpl(Activity activity) {
        this.activity = activity;
        init();
    }

    public ViewImpl(Fragment fragment) {
        this.fragment = fragment;
        init();
    }

    public ViewImpl(View view) {
        this.view = view;
    }

    public ViewImpl(Service service) {
        this.service = service;
        init();
    }

    /**
     * Initializes a new progressDialogHelper object.
     */
    private void init() {
        progressDialogHelper = new ProgressDialogHelper();
    }

    @Override
    public void showMessage(String message) {
        if (getSnackBarBackground() == null) {
            return;
        }
        Snackbar.make(getSnackBarBackground(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int messageResId) {
        if (getContext() == null) {
            return;
        }
        showMessage(getContext().getString(messageResId));
    }

    @Override
    public void showProgress() {
        if (getContext() == null) {
            return;
        }
        progressDialogHelper.showProgress(getContext(), getContext().getString(R.string.loading));
    }

    @Override
    public void showProgress(String message) {
        progressDialogHelper.showProgress(getContext(), message);
    }

    @Override
    public void showProgress(int messageResId) {
        if (getContext() == null) {
            return;
        }
        showProgress(getContext().getString(messageResId));
    }

    @Override
    public void showProgress(String message, String title) {
        progressDialogHelper.showProgress(getContext(), message, title);
    }

    @Override
    public void showProgress(int messageResId, int titleResId) {
        if (getContext() == null) {
            return;
        }
        showProgress(getContext().getString(messageResId), getContext().getString(titleResId));
    }

    @Override
    public void hideProgress() {
        progressDialogHelper.hideProgress();
    }

    @Override
    public void hideKeyboard() {
        if (getContext() == null || getWindowToken() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    /**
     * A method that completes the swipe-to-refresh action and loads data.
     *
     * @param swipeRefreshLayout The layout on which action is called.
     * @param presenter          The presenter which will refresh the data.
     */
    public void initSwipeToRefresh(SwipeRefreshLayout swipeRefreshLayout, BasePresenter presenter) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            presenter.refreshData();
        });
    }

    /**
     * Returns the view's context, null otherwise.
     *
     * @return Returns the view's context, null otherwise.
     */
    private Context getContext() {
        if (activity != null) {
            return activity;
        } else if (fragment != null) {
            return fragment.getContext();
        } else if (view != null) {
            return view.getContext();
        } else if (service != null) {
            return service;
        }
        return null;
    }

    /**
     * Returns root view of layout, null otherwise.
     *
     * @return Returns root view of layout, null otherwise. The view is used in creating Snackbar.
     */
    private View getSnackBarBackground() {
        if (activity != null) {
            return activity.findViewById(android.R.id.content);
        } else if (view != null) {
            return view;
        } else if (fragment != null) {
            return fragment.getView();
        }
        return null;
    }

    /**
     * Retrieves a unique token identifying the window this view is attached to.
     *
     * @return A unique token to be used in hiding the keyboard.
     */
    private IBinder getWindowToken() {
        if (activity != null) {
            View view = activity.getCurrentFocus();
            return view == null ? null : view.getWindowToken();
        } else if (fragment != null) {
            Activity activity = fragment.getActivity();
            if (activity == null) {
                return null;
            }
            View view = activity.getCurrentFocus();
            return view == null ? null : view.getWindowToken();
        } else if (view != null) {
            return view.getWindowToken();
        }
        return null;
    }
}
