package io.github.nickdex.pasteit;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.github.nickdex.pasteit.core.di.components.ApplicationComponent;
import io.github.nickdex.pasteit.core.di.components.DaggerApplicationComponent;
import io.github.nickdex.pasteit.core.di.modules.ApplicationModule;

/**
 * Android Main Application
 *
 * @author Nikhil Warke
 */

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

}
