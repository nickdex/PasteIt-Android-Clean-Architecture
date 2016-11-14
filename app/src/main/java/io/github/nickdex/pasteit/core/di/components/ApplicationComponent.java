package io.github.nickdex.pasteit.core.di.components;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import io.github.nickdex.pasteit.core.di.AppScope;
import io.github.nickdex.pasteit.core.di.modules.ApplicationModule;
import io.github.nickdex.pasteit.view.activity.BaseActivity;
import rx.Scheduler;

/**
 * A component whose lifetime is the life of the application.
 */
@AppScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity activity);

    //Exposed to sub-graphs.
    Context context();

    @Named("Thread")
    Scheduler threadScheduler();

    @Named("PostExecution")
    Scheduler postExecutionScheduler();
}
