package io.github.nickdex.pasteit.core.presentation.view;

/**
 * Base Interface for view in MVP pattern.
 */

public interface View {

    void showMessage(String message);

    void showMessage(int messageResId);

    void showProgress();

    void showProgress(String message);

    void showProgress(int messageResId);

    void showProgress(String message, String title);

    void showProgress(int messageResId, int titleResId);

    void hideProgress();

    void hideKeyboard();
}

