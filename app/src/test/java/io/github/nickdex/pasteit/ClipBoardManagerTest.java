package io.github.nickdex.pasteit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Testing {@link ClipBoardManager}
 *
 * @author Nikhil Warke
 */

@RunWith(MockitoJUnitRunner.class)
public class ClipBoardManagerTest {

    @Mock
    Context context;

    @Mock
    ClipboardManager mockSystemClipBoardManager;

    @Mock
    ClipData.Item message;

    @Test
    public void pasteWhenTextAvailable() {
        when(context.getSystemService(Context.CLIPBOARD_SERVICE)).thenReturn(mockSystemClipBoardManager);
        CharSequence data = "hello";
        when(message.getText()).thenReturn(data);
        when(mockSystemClipBoardManager.getPrimaryClip().getItemAt(0))
                .thenReturn(message);

        ClipBoardManager manager = new ClipBoardManager(context);

        String pasteData = manager.paste();
        assertThat(pasteData, is(message.toString()));
    }
}
