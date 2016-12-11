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

package io.github.nickdex.pasteit.data.manager.impl;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.github.nickdex.pasteit.data.manager.AuthException;
import io.github.nickdex.pasteit.data.manager.AuthManager;
import io.github.nickdex.pasteit.domain.model.User;
import io.github.nickdex.pasteit.interactor.user.CreateUser;
import rx.Subscriber;

/**
 * Implementation of {@link AuthManager}.
 */
public class AuthManagerImpl implements AuthManager {

    private FirebaseAuth auth;

    private GoogleApiClient googleApiClient;

    public AuthManagerImpl(GoogleApiClient client) {
        this.googleApiClient = client;
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public void signInGoogle(GoogleSignInAccount acct, final Subscriber<String> signInSubscriber, final CreateUser createUser) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (!signInSubscriber.isUnsubscribed()) {
                        if (task.isSuccessful()) {
                            saveUser(task.getResult().getUser(), signInSubscriber, createUser);
                        } else {
                            signInSubscriber.onError(new AuthException());
                        }
                    }
                });
    }

    private void saveUser(FirebaseUser fUser, Subscriber<String> subscriber, CreateUser createUser) {
        User user = new User();
        user.setId(fUser.getUid());
        user.setEmail(fUser.getEmail());
        user.setName(fUser.getDisplayName());

        createUser.execute(user, subscriber);
    }

    @Override
    public void signOut(final Subscriber<String> signOutSubscriber) {
        final String id = getCurrentUserId();
        auth.signOut();
        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                        status -> {
                            googleApiClient.disconnect();
                            googleApiClient.unregisterConnectionCallbacks(this);
                            if (!signOutSubscriber.isUnsubscribed()) {
                                if (status.isSuccess()) {
//                                        deleteCache();
                                    signOutSubscriber.onNext(id);
                                } else {
                                    signOutSubscriber.onError(new AuthException());
                                }
                            }
                        }

                );
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });
        googleApiClient.connect();
    }

    @Override
    public boolean isSignedIn() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public String getCurrentUserId() {
        String id = null;
        if (isSignedIn()) {
            //noinspection ConstantConditions
            id = auth.getCurrentUser().getUid();
        }
        return id;
    }

    @Override
    public String getCurrentUserEmail() {
        String email = null;
        if (isSignedIn()) {
            //noinspection ConstantConditions
            email = auth.getCurrentUser().getEmail();
        }
        return email;
    }

    @Override
    public Uri getPhotoUrl() {
        Uri photoUri = null;
        if (isSignedIn()) {
            //noinspection ConstantConditions
            photoUri = auth.getCurrentUser().getPhotoUrl();
        }
        return photoUri;
    }


}
