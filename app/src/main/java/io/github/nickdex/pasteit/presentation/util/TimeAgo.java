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

package io.github.nickdex.pasteit.presentation.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateUtils;

import io.github.nickdex.pasteit.R;

/**
 * Utility class for calculating relative time.
 */
public class TimeAgo {

    public static String getTimeAgo(long deltaSeconds, Context ctx) {
        Resources res = ctx.getResources();

        if (deltaSeconds >= res.getInteger(R.integer.ZERO) && deltaSeconds <= res.getInteger(R.integer.ALMOST_DAY)) {
            if (deltaSeconds <= res.getInteger(R.integer.FEW_SECONDS)) {
                return res.getString(R.string.time_ago_just_now);
            } else if (deltaSeconds <= res.getInteger(R.integer.ALMOST_MINUTE)) {
                return deltaSeconds + " " + res.getString(R.string.time_ago_unit_seconds) + " " + res.getString(R.string.time_ago_suffix);
            } else if (deltaSeconds <= res.getInteger(R.integer.MINUTES)) {
                return res.getString(R.string.time_ago_term_a) + " " + res.getString(R.string.time_ago_unit_minute) + " " + res.getString(R.string.time_ago_suffix);
            } else if (deltaSeconds <= res.getInteger(R.integer.ALMOST_HOUR)) {
                return Math.abs(deltaSeconds / res.getInteger(R.integer.A_MINUTE)) + " " + res.getString(R.string.time_ago_unit_minutes) + " " + res.getString(R.string.time_ago_suffix);
            } else if (deltaSeconds <= res.getInteger(R.integer.HOURS)) {
                return res.getString(R.string.time_ago_term_an) + " " + res.getString(R.string.time_ago_unit_hour) + " " + res.getString(R.string.time_ago_suffix);
            } else {
                return Math.abs(deltaSeconds / res.getInteger(R.integer.A_HOUR)) + " " + res.getString(R.string.time_ago_unit_hours) + " " + res.getString(R.string.time_ago_suffix);
            }
        } else {
            long now = System.currentTimeMillis() - deltaSeconds * 1000;
            return DateUtils.getRelativeDateTimeString(ctx, now,
                    DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS,
                    DateUtils.FORMAT_SHOW_TIME).toString();
        }
    }

    /**
     * Returns time relative to the system time in human readable form.
     *
     * @param time The time in the past.
     * @return The time relative to the system time in human readable form.
     */
    public static String getPrettyTime(long time, Context context) {
        long timeDistance = Math.round((Math.abs(System.currentTimeMillis() - time) / 1000));
        return getTimeAgo(timeDistance, context);
    }
}
