package io.github.nickdex.pasteit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import timber.log.Timber;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

/**
 * Listens to clipboard for new items and sends it to service
 *
 * @author Nikhil Warke
 * @since 23/10/16.
 */

public class ClipBoardManager {

    private Context mContext;
    private ClipboardManager clipboard;

    public ClipBoardManager(Context context) {
        clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public String paste() {
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
        // If the clipboard doesn't contain data, disable the paste menu item.
        // If it does contain data, decide if you can handle the data.
        return clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN);
    }
}

