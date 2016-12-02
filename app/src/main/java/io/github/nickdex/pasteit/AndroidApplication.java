package io.github.nickdex.pasteit;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import io.github.nickdex.pasteit.core.di.components.ApplicationComponent;
import io.github.nickdex.pasteit.core.di.components.DaggerApplicationComponent;
import io.github.nickdex.pasteit.core.di.modules.ApplicationModule;
import io.github.nickdex.pasteit.manager.AuthManager;

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

        if (authManager.isSignedIn()) {
            FirebaseMessaging.getInstance().subscribeToTopic("user_" + authManager.getCurrentUserId());
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (authManager.isSignedIn()) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("user_" + authManager.getCurrentUserId());
        }
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
