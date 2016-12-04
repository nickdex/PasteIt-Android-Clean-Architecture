package io.github.nickdex.pasteit;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import io.github.nickdex.pasteit.core.di.components.ApplicationComponent;
import io.github.nickdex.pasteit.core.di.components.DaggerApplicationComponent;
import io.github.nickdex.pasteit.core.di.modules.ApplicationModule;
import io.github.nickdex.pasteit.data.manager.AuthManager;

/**
 * Class initializes {@link Application} level objects for the app.
 * It also sets up dependency injection framework to create generated files.
 */
public class AndroidApplication extends Application {

    @Inject
    AuthManager authManager;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
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
