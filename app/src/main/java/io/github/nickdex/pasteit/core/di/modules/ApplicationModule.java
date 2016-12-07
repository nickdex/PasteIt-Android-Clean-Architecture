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

package io.github.nickdex.pasteit.core.di.modules;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.github.nickdex.pasteit.AndroidApplication;
import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.data.manager.impl.NetworkManagerImpl;
import io.github.nickdex.pasteit.core.di.AppScope;
import io.github.nickdex.pasteit.data.manager.AuthManager;
import io.github.nickdex.pasteit.data.manager.impl.AuthManagerImpl;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @AppScope
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Named("Thread")
    @AppScope
    Scheduler providesThreadScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Named("PostExecution")
    @AppScope
    Scheduler providesPostExecutionScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @AppScope
    GoogleApiClient providesGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(application.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        return new GoogleApiClient.Builder(application)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Provides
    @AppScope
    AuthManager providesAuthManager(GoogleApiClient googleApiClient) {
        return new AuthManagerImpl(googleApiClient);
    }

    @Provides
    @AppScope
    NetworkManager providesNetworkManager() {
        return new NetworkManagerImpl(application);
    }
}
