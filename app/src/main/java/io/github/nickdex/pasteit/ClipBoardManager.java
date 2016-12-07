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

package io.github.nickdex.pasteit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import timber.log.Timber;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

/**
 * Listens to clipboard for new items and sends it to service.
 */
public class ClipBoardManager {

    private Context mContext;
    private ClipboardManager clipboard;

    public ClipBoardManager(Context context) {
        clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public String getMessage() {
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        CharSequence pasteData = item.getText();

        if (pasteData != null) {
            return pasteData.toString();
        } else {
            String message = "Type Not Supported";
            Timber.e(message);
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    public boolean canPaste() {
        // If the clipboard doesn't contain data, disable the getMessage menu item.
        // If it does contain data, decide if you can handle the data.
        return clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN);
    }
}

