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

package io.github.nickdex.pasteit.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.nickdex.pasteit.framework.presentation.util.TimeAgo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for utility class {@link TimeAgo}.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class TimeAgoUnitTest {

    private static final long[] SECONDS = {2, 30, 100, 3000, 4000, 70000, 90000};
    private static String[] MESSAGES = {
            "just now",
            "30 seconds ago",
            "a minute ago",
            Math.abs(SECONDS[3] / 60) + " minutes ago",
            "an hour ago",
            Math.abs(SECONDS[5] / 3600) + " hours ago", null};


    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        MESSAGES[MESSAGES.length - 1] = DateUtils.getRelativeDateTimeString(context,
                System.currentTimeMillis() - SECONDS[SECONDS.length - 1] * 1000,
                DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS,
                DateUtils.FORMAT_SHOW_TIME).toString();
    }

    @Test
    public void testAllCases() {
        for (int i = 0; i < SECONDS.length; i++) {
            assertEquals(TimeAgo.getTimeAgo(SECONDS[i], context), MESSAGES[i]);
        }
    }

    @Test
    public void test_yesterday() {
        assertTrue(TimeAgo.getTimeAgo(87410, context).contains("Yesterday"));
    }
}
