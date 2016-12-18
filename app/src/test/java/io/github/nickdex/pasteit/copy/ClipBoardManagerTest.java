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

package io.github.nickdex.pasteit.copy;

import android.content.ClipData;
import android.content.ClipboardManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link ClipBoardManager}
 */
@RunWith(MockitoJUnitRunner.class)
public class ClipBoardManagerTest {

    private static final String TEXT = "Hello Nikhil";
    @Mock
    ClipboardManager mockSystemClipBoardManager;
    @Mock
    ClipData mockClipData;
    @Mock
    ClipData.Item message;

    @Test
    public void pasteWhenTextAvailable() {
        CharSequence clipText = TEXT;
        when(message.getText()).thenReturn(clipText);
        when(mockClipData.getItemAt(0)).thenReturn(message);
        when(mockSystemClipBoardManager.getPrimaryClip()).thenReturn(mockClipData);

        ClipBoardManager manager = new ClipBoardManager(mockSystemClipBoardManager);

        String pasteData = manager.getClip();
        assertThat(pasteData, is(TEXT));
    }
}
