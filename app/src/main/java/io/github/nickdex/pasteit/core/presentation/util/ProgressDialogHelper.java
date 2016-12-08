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

package io.github.nickdex.pasteit.core.presentation.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * A class that helps in managing the Dialog showing progress operation.
 */
public class ProgressDialogHelper {

    /**
     * The progress dialog from Android that is shown to user.
     */
    private ProgressDialog dialog;

    /**
     * A variable that stores the count of no of calls to show or hide methods.
     */
    private volatile int progressesCount = 0;

    /**
     * Shows the progress dialog to user.
     */
    public void showProgress(Context context) {
        showProgress(context, null);
    }

    /**
     * Shows the progress dialog to user with a message.
     *
     * @param context The context which calls the dialog.
     * @param message The string message to be shown during operation.
     */
    public void showProgress(Context context, String message) {
        showProgress(context, message, null);
    }

    /**
     * Shows the progress dialog to user with a message and a title.
     * It also increments the progressesCount by 1.
     *
     * @param context The context which calls the dialog.
     * @param message The string message to be shown during operation.
     * @param title   The string to be set as title.
     */
    public void showProgress(Context context, String message, String title) {
        if (context == null) {
            return;
        }

        if (!inProgress()) {
            dialog = new ProgressDialog(context);
            if (message != null) dialog.setMessage(message);
            if (title != null) dialog.setTitle(title);
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        progressesCount++;
    }

    /**
     * Hides the progress dialog from user.
     * It also decrements the progressesCount by 1.
     */
    public void hideProgress() {
        progressesCount--;
        if (progressesCount <= 0) {
            if (inProgress()) {
                dialog.dismiss();
            }
            progressesCount = 0;
        }

    }

    /**
     * Returns true if progress is visible, false otherwise.
     *
     * @return Returns true if progress is visible, false otherwise.
     */
    private boolean inProgress() {
        return dialog != null && dialog.isShowing();
    }
}
