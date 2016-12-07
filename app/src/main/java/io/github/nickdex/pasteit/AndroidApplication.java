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
