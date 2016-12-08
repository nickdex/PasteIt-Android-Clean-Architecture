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

package io.github.nickdex.pasteit.core.presentation.view;

/**
 * Base Interface for view in MVP pattern.
 */

public interface View {

    /**
     * Shows the message to user.
     *
     * @param message The string to be shown to user.
     */
    void showMessage(String message);

    /**
     * Shows the message to user.
     *
     * @param messageResId The resource id to string to be shown to user.
     */
    void showMessage(int messageResId);

    /**
     * Shows the visual indicator of progress to user.
     */
    void showProgress();

    /**
     * Shows the visual indicator of progress to user with a message.
     *
     * @param message The string message to be shown during operation.
     */
    void showProgress(String message);

    /**
     * Shows the visual indicator of progress to user with a message.
     *
     * @param messageResId The resource id to string message to be shown during operation.
     */
    void showProgress(int messageResId);

    /**
     * Shows the visual indicator of progress to user with a message and a title.
     *
     * @param message The string message to be shown during operation.
     * @param title   The string to be set as title.
     */
    void showProgress(String message, String title);

    /**
     * Shows the visual indicator of progress to user with a message and a title.
     *
     * @param messageResId The resource id to string message to be shown during operation.
     * @param titleResId The resource id to string to be set as title.
     */
    void showProgress(int messageResId, int titleResId);

    /**
     * Hides the visual indicator of progress to user.
     */
    void hideProgress();

    /**
     * Closes the soft keyboard on screen.
     */
    void hideKeyboard();
}

