package io.github.nickdex.pasteit.core.di.components;

import android.app.Activity;

import dagger.Component;
import io.github.nickdex.pasteit.core.di.PerActivity;
import io.github.nickdex.pasteit.core.di.modules.ActivityModule;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 * <p>
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link PerActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}

