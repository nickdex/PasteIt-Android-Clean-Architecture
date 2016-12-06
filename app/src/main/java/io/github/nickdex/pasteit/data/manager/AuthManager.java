package io.github.nickdex.pasteit.data.manager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.github.nickdex.pasteit.interactor.user.CreateUser;
import rx.Subscriber;

/**
 * Interface that manages user account.
 */
public interface AuthManager {

    void signInGoogle(GoogleSignInAccount acct, Subscriber<String> signInSubscriber, CreateUser createUser);

    void signOut(Subscriber<String> signOutSubscriber);

    boolean isSignedIn();

    String getCurrentUserId();
}