package io.github.nickdex.pasteit.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Named;

import io.github.nickdex.pasteit.domain.repository.Repository;
import io.github.nickdex.pasteit.interactor.GetUseCase;
import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link io.github.nickdex.pasteit.interactor.UseCase}
 *
 * @author Nikhil Warke
 * @version 1.0
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
        // TODO change useCase for longer action to uncomment this line
        //assertThat(useCase.isUnSubscribed(), Is.is(false));
        useCase.unSubscribe();
        Assert.assertThat(useCase.isUnSubscribed(), is(true));
    }

    interface TestRepository extends Repository {
        Observable<Integer> getData();
    }

    class TestUseCase extends GetUseCase<Integer, TestRepository> {


        public TestUseCase(TestRepository repository,
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