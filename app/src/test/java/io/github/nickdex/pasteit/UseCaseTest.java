package io.github.nickdex.pasteit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.github.nickdex.pasteit.core.executor.PostExecutionThread;
import io.github.nickdex.pasteit.core.executor.ThreadExecutor;
import io.github.nickdex.pasteit.interactor.UseCase;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link io.github.nickdex.pasteit.interactor.UseCase}
 *
 * @author Nikhil Warke
 * @version 1.0
 */

@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest {

    private UseCaseTestClass useCase;

    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {
        this.useCase = new UseCaseTestClass(
                mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testBuildUseCaseObservableReturnCorrectResult() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        TestScheduler testScheduler = new TestScheduler();

        when(mockPostExecutionThread.getScheduler()).thenReturn(testScheduler);

        useCase.execute(testSubscriber);

        assertThat(testSubscriber.getOnNextEvents().size(), is(0));
    }

    @Test
    public void testSubscriptionWhenExecutingUseCase() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();


        useCase.execute(testSubscriber);
        useCase.unSubscribe();

        assertThat(testSubscriber.isUnsubscribed(), is(true));
    }

    private static class UseCaseTestClass extends UseCase {

        UseCaseTestClass(
                ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
            super(threadExecutor, postExecutionThread);
        }

        @Override
        protected Observable buildUseCaseObservable() {
            return Observable.empty();
        }

        @Override
        public void execute(Subscriber UseCaseSubscriber) {
            super.execute(UseCaseSubscriber);
        }
    }
}
