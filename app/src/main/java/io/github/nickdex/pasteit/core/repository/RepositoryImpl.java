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

package io.github.nickdex.pasteit.core.repository;

import java.util.HashMap;
import java.util.Map;

import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.data.mapper.BaseMapper;
import io.github.nickdex.pasteit.core.data.store.EntityStore;
import io.github.nickdex.pasteit.domain.repository.Repository;
import io.github.nickdex.pasteit.interactor.UseCase;

/**
 * Base class for all repositories implementations.
 *
 * @param <ENTITY_STORE> Data storage which collects and maintains the data.
 * @param <ENTITY_DM_MAPPER> Mapper that converts data between Entity and Domain models.
 */
public abstract class RepositoryImpl
        <ENTITY_STORE extends EntityStore,
                ENTITY_DM_MAPPER extends BaseMapper> implements Repository {

    protected final Map<String, UseCase> useCasesMap = new HashMap<>();
    protected NetworkManager networkManager;
    protected ENTITY_STORE cloudStore;
    protected ENTITY_DM_MAPPER entityDmMapper;

    public RepositoryImpl(NetworkManager networkManager,
                          ENTITY_STORE cloudStore,
                          ENTITY_DM_MAPPER entityDtoMapper) {
        this.networkManager = networkManager;
        this.cloudStore = cloudStore;
        this.entityDmMapper = entityDtoMapper;
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
