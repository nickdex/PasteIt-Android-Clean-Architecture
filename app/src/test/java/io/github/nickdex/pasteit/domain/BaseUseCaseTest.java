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

package io.github.nickdex.pasteit.domain;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.github.nickdex.pasteit.framework.domain.Messenger;
import io.github.nickdex.pasteit.framework.domain.repository.Repository;
import io.github.nickdex.pasteit.framework.usecase.UseCase;
import rx.Scheduler;
import rx.functions.Action0;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Base Test class for {@link UseCase}
 *
 * @param <USE_CASE>   Type of Use case.
 * @param <REPOSITORY> Type of Repository.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseUseCaseTest<USE_CASE extends UseCase, REPOSITORY extends Repository> {

    protected REPOSITORY mockRepository;
    @Mock
    protected Messenger mockMessenger;
    @Mock
    protected Scheduler mockThreadScheduler;
    @Mock
    protected Scheduler mockPostExecutionScheduler;
    USE_CASE useCase;

    @Before
    public void setUp() {
        mockRepository = createRepository();
        useCase = createUseCase();
    }

    protected abstract USE_CASE createUseCase();

    protected abstract REPOSITORY createRepository();

    public abstract void testBuildUseCaseObservable();

    protected void testBuildUseCaseObservable(Object requestData, Action0 action) {
        //noinspection unchecked
        useCase.buildObservable(requestData);
        action.call();
        verifyNoMoreInteractions(mockRepository);
        verifyZeroInteractions(mockThreadScheduler);
        verifyZeroInteractions(mockPostExecutionScheduler);
    }
}
