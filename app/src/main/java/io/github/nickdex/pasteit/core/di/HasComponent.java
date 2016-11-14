package io.github.nickdex.pasteit.core.di;

/**
 * Interface representing a contract for clients that contains a component for dependency injection.
 *
 * @author Nikhil Warke
 */

public interface HasComponent<C> {
    C getComponent();
}
