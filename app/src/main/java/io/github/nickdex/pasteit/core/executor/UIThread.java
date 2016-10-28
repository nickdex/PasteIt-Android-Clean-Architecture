package io.github.nickdex.pasteit.core.executor;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * MainThread (UI Thread) implementation based on a {@link rx.Scheduler}
 * which will execute actions on the Android UI thread
 *
 * @author Nikhil Warke
 * @version 1.0
 * @since 0.16
 */

public class UIThread implements PostExecuteThread {

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
