package io.github.nickdex.pasteit.core.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;
import javax.inject.Singleton;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the application to be memorized in the
 * correct component.
 */
@Scope
@Retention(RUNTIME)
@Singleton
public @interface AppScope {
}

