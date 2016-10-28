package io.github.nickdex.pasteit.core.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Decorated {@link java.util.concurrent.ThreadPoolExecutor}
 *
 * @author Nikhil Warke
 * @version 1.0
 * @since 0.16
 */

public class JobExecutor implements ThreadExecutor {

    private final static int POOL_SIZE = 3;
    private final static int MAX_POOL_SIZE = 5;
    private final static int KEEP_ALIVE_TIME = 10;

    private final ThreadPoolExecutor threadPoolExecutor;

    JobExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new JobThreadFactory());
    }


    @Override
    public void execute(@NonNull Runnable command) {
        this.threadPoolExecutor.execute(command);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
