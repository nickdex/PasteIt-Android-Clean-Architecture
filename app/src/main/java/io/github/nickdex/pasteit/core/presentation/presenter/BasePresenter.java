package io.github.nickdex.pasteit.core.presentation.presenter;

import android.support.annotation.NonNull;

import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.presentation.view.View;

public abstract class BasePresenter<VIEW extends View> {

    protected NetworkManager networkManager;
    protected VIEW view;

    public BasePresenter(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public void attachView(@NonNull VIEW view) {
        this.view = view;
        onViewAttached();
        networkManager.add(toString(), this::refreshData);
    }

    public void detachView() {
        networkManager.remove(toString());
        onViewDetached();
        this.view = null;
    }

    public void resume() {
    }

    public void pause() {
    }

    public void destroy() {
        onDestroyed();
    }

    public abstract void refreshData();

    protected void onViewAttached() {
    }

    protected void onViewDetached() {
    }

    protected void onDestroyed() {
    }
}
