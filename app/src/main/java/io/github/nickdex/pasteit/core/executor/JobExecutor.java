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

package io.github.nickdex.pasteit.core.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Decorated {@link ThreadPoolExecutor}.
 */
@Singleton
public class JobExecutor implements ThreadExecutor {

    private final static int POOL_SIZE = 3;
    private final static int MAX_POOL_SIZE = 5;
    private final static int KEEP_ALIVE_TIME = 10;

    private final ThreadPoolExecutor threadPoolExecutor;

    @Inject
    JobExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
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
