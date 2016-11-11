package io.github.nickdex.pasteit.signin;

import android.support.annotation.NonNull;

import io.github.nickdex.pasteit.interactor.UseCase;
import io.github.nickdex.pasteit.presenter.Presenter;

/**
 * {@link Presenter} that controls communication between views and models of presentation layer of Sign in.
 */

class SignInPresenter implements Presenter {

    private final UseCase signInUseCase;

    private SignInView signView;

    SignInPresenter(UseCase signInUseCase) {
        this.signInUseCase = signInUseCase;
    }

    public void setView(@NonNull SignInView view) {
        this.signView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.signView = null;
    }
}
