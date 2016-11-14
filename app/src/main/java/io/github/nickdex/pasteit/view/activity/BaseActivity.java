package io.github.nickdex.pasteit.view.activity;

import android.app.Activity;
import android.os.Bundle;

import io.github.nickdex.pasteit.AndroidApplication;
import io.github.nickdex.pasteit.core.di.components.ApplicationComponent;
import io.github.nickdex.pasteit.core.di.modules.ActivityModule;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

}
