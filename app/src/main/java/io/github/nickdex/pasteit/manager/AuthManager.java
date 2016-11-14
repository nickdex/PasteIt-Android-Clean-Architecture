package io.github.nickdex.pasteit.manager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.github.nickdex.pasteit.user.CreateUser;
import rx.Subscriber;

/**
 * Sign In with google
 *
 * @author Nikhil Warke
 */

public interface AuthManager {

    void signInGoogle(GoogleSignInAccount acct, Subscriber<String> signInSubscriber, CreateUser createUser);

    void signOut(Subscriber<String> signOutSubscriber);

    boolean isSignedIn();

    String getCurrentUserId();
}