/*
 * Copyright © 2017 Nikhil Warke
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

package io.github.nickdex.pasteit;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import io.github.nickdex.pasteit.framework.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.framework.core.di.components.ApplicationComponent;
import io.github.nickdex.pasteit.framework.core.di.components.DaggerApplicationComponent;
import io.github.nickdex.pasteit.framework.core.di.modules.ApplicationModule;
import io.github.nickdex.pasteit.framework.data.manager.AuthManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Class initializes {@link Application} level objects for the app.
 * It also sets up dependency injection framework to create generated files.
 */
public class AndroidApplication extends Application {

    @Inject
    AuthManager authManager;

    @Inject
    NetworkManager networkManager;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
        this.initTimber();
        this.initRealm();
        networkManager.start();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        networkManager.stop();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
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
