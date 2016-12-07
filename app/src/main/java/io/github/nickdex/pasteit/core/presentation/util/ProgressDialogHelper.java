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

public class ProgressDialogHelper {

    private ProgressDialog dialog;

    private volatile int progressesCount = 0;

    public void showProgress(Context context) {
        showProgress(context, null);
    }

    public void showProgress(Context context, String message) {
        showProgress(context, message, null);
    }

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

    public void hideProgress() {
        progressesCount--;
        if (progressesCount <= 0) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            progressesCount = 0;
        }

    }

    private boolean inProgress() {
        return dialog != null && dialog.isShowing();
    }
}
