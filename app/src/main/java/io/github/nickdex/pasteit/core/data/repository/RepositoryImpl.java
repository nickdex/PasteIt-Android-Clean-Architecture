package io.github.nickdex.pasteit.core.data.repository;

import java.util.HashMap;
import java.util.Map;

import io.github.nickdex.pasteit.core.data.store.EntityStore;
import io.github.nickdex.pasteit.core.data.store.cache.Cache;
import io.github.nickdex.pasteit.domain.repository.Repository;
import io.github.nickdex.pasteit.interactor.UseCase;
import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.data.mapper.BaseMapper;

/**
 * Base class for all repositories implementations.
 *
 * @param <ENTITY_STORE>
 * @param <CACHE>
 * @param <ENTITY_DTO_MAPPER>
 */
public abstract class RepositoryImpl
        <ENTITY_STORE extends EntityStore,
                CACHE extends Cache,
                ENTITY_DTO_MAPPER extends BaseMapper> implements Repository {

    protected final Map<String, UseCase> useCasesMap = new HashMap<>();
    protected NetworkManager networkManager;
    protected ENTITY_STORE cloudStore;
    protected CACHE cache;
    protected ENTITY_DTO_MAPPER entityDtoMapper;

    public RepositoryImpl(NetworkManager networkManager,
                          ENTITY_STORE cloudStore,
                          CACHE cache,
                          ENTITY_DTO_MAPPER entityDtoMapper) {
        this.networkManager = networkManager;
        this.cloudStore = cloudStore;
        this.cache = cache;
        this.entityDtoMapper = entityDtoMapper;
    }

    @Override
    public void register(UseCase useCase) {
        if (useCase != null) {
            useCasesMap.put(useCase.toString(), useCase);
        }
    }

    @Override
    public void unregister(UseCase useCase) {
        if (useCase != null) {
            useCasesMap.remove(useCase.toString());
        }
    }
}
