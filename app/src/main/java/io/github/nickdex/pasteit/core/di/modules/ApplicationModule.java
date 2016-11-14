package io.github.nickdex.pasteit.core.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.nickdex.pasteit.AndroidApplication;
import io.github.nickdex.pasteit.core.executor.JobExecutor;
import io.github.nickdex.pasteit.core.executor.PostExecutionThread;
import io.github.nickdex.pasteit.core.executor.ThreadExecutor;
import io.github.nickdex.pasteit.core.executor.UIThread;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    // Add Additional objects like cache or repository
}
