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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Named;

import io.github.nickdex.pasteit.framework.domain.Messenger;
import io.github.nickdex.pasteit.framework.domain.repository.Repository;
import io.github.nickdex.pasteit.framework.usecase.GetUseCase;
import io.github.nickdex.pasteit.framework.usecase.UseCase;
import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link UseCase}
 */
@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest extends BaseUseCaseTest<UseCaseTest.TestUseCase, UseCaseTest.TestRepository> {

    private TestSubscriber<Integer> testSubscriber;

    @Override
    public void setUp() {
        super.setUp();
        testSubscriber = new TestSubscriber<>();
    }

    @Override
    protected TestUseCase createUseCase() {
        return new TestUseCase(mockRepository, mockMessenger, mockThreadScheduler, new TestScheduler());
    }

    @Override
    protected TestRepository createRepository() {
        TestRepository testRepository = mock(TestRepository.class);
        when(testRepository.getData()).thenReturn(Observable.just(Integer.MAX_VALUE));
        return testRepository;
    }

    @Test
    @Override
    public void testBuildUseCaseObservable() {
        testBuildUseCaseObservable(null, () -> verify(mockRepository).getData());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void buildUseCaseObservable_AsCorrectResult() {
        useCase.execute(testSubscriber);
        Assert.assertThat(testSubscriber.getOnNextEvents().size(), is(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void unSubscribe_AfterExecute_AsUnSubscribed() {
        Assert.assertThat(useCase.isUnSubscribed(), is(true));
        useCase.execute(testSubscriber);
        useCase.unSubscribe();
        Assert.assertThat(useCase.isUnSubscribed(), is(true));
    }

    interface TestRepository extends Repository {
        Observable<Integer> getData();
    }

    class TestUseCase extends GetUseCase<Integer, TestRepository> {


        TestUseCase(TestRepository repository,
                    Messenger messenger,
                    @Named("Thread") Scheduler threadScheduler,
                    @Named("PostExecution") Scheduler postExecutionScheduler) {
            super(repository, messenger, threadScheduler, postExecutionScheduler);
        }

        @Override
        public Observable<Integer> buildObservable() {
            return repository.getData();
        }
    }
}