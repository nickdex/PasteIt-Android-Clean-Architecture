/*
 * Copyright © 2017 Nikhil Warke
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

package io.github.nickdex.pasteit.messages;

import android.app.IntentService;
import android.content.Intent;

import javax.inject.Inject;

import io.github.nickdex.pasteit.AndroidApplication;
import io.github.nickdex.pasteit.copy.ClipBoardManager;

/**
 * An {@link IntentService} for copying clip to clipboard asynchronously from notification action.
 * <p>
 */
public class CopyClipIntentService extends IntentService {

    public static final String EXTRA_CLIP_DATA = "io.github.nickdex.pasteit.messages.extra.CLIP_DATA";
    @Inject
    ClipBoardManager clipBoardManager;

    public CopyClipIntentService() {
        super("CopyClipIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ((AndroidApplication) getApplication()).getApplicationComponent().inject(this);

            final String clipData = intent.getStringExtra(EXTRA_CLIP_DATA);
            copyToClipboard(clipData);
        }
    }

    /**
     * Copy the clip onto clipboard.
     */
    private void copyToClipboard(String clipData) {
        clipBoardManager.setClip(clipData);
    }
}
