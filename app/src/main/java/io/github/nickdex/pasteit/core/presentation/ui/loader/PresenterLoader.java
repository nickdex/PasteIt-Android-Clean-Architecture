package io.github.nickdex.pasteit.core.presentation.ui.loader;

import android.content.Context;
import android.support.v4.content.Loader;

import io.github.nickdex.pasteit.core.presentation.presenter.BasePresenter;

public abstract class PresenterLoader<PRESENTER extends BasePresenter> extends Loader<PRESENTER> {

    private PRESENTER presenter;

    public PresenterLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        presenter = initPresenter();
        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        super.onReset();
        presenter.destroy();
        presenter = null;
    }

    protected abstract PRESENTER initPresenter();
}
