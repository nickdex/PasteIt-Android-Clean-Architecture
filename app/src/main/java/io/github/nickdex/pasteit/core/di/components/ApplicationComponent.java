package io.github.nickdex.pasteit.core.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.github.nickdex.pasteit.core.di.modules.ApplicationModule;
import io.github.nickdex.pasteit.core.executor.PostExecutionThread;
import io.github.nickdex.pasteit.core.executor.ThreadExecutor;
import io.github.nickdex.pasteit.view.activity.BaseActivity;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or un-scoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();
}
