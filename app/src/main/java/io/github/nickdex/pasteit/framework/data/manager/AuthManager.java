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

package io.github.nickdex.pasteit.framework.data.manager;

import android.net.Uri;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.github.nickdex.pasteit.framework.usecase.user.CreateUser;
import rx.Subscriber;

/**
 * Interface that manages user account.
 */
public interface AuthManager {

    void signInGoogle(GoogleSignInAccount acct, Subscriber<String> signInSubscriber, CreateUser createUser);

    void signOut(Subscriber<String> signOutSubscriber);

    boolean isSignedIn();

    String getCurrentUserId();

    String getCurrentUserEmail();

    Uri getPhotoUrl();
}