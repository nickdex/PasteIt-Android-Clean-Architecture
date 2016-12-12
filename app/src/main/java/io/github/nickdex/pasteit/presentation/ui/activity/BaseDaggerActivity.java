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

package io.github.nickdex.pasteit.presentation.ui.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.github.nickdex.pasteit.AndroidApplication;
import io.github.nickdex.pasteit.core.di.components.ViewComponent;
import io.github.nickdex.pasteit.core.di.modules.ViewModule;
import io.github.nickdex.pasteit.core.presentation.presenter.BasePresenter;
import io.github.nickdex.pasteit.core.presentation.ui.activity.BaseActivity;
import io.github.nickdex.pasteit.core.presentation.view.View;

/**
 * Base class for activities that need dependency injections for attaching the view to activity.
 */
public abstract class BaseDaggerActivity<VIEW extends View,
        PRESENTER extends BasePresenter,
        BINDING extends ViewDataBinding> extends BaseActivity<VIEW, PRESENTER, BINDING> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initViewComponent();
        super.onCreate(savedInstanceState);
    }

    /**
     * Adds a {@link ViewModule} for the injected view.
     */
    private void initViewComponent() {
        ViewComponent viewComponent = ((AndroidApplication) getApplication()).getApplicationComponent()
                .plus(new ViewModule(view));
        injectViewComponent(viewComponent);
    }

    protected abstract void injectViewComponent(ViewComponent viewComponent);
}
