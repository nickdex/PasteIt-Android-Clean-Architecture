package io.github.nickdex.pasteit.domain.repository;

import io.github.nickdex.pasteit.interactor.UseCase;

/**
 * Base Interface for Repository pattern.
 */
public interface Repository {

    void register(UseCase useCase);

    void unregister(UseCase useCase);
}
