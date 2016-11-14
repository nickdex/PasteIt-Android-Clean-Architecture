package io.github.nickdex.pasteit;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.github.nickdex.pasteit.interactor.UseCase;
import io.github.nickdex.pasteit.domain.repository.Repository;
import io.github.nickdex.pasteit.domain.Messenger;
import rx.Scheduler;
import rx.functions.Action0;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseUseCaseTest<USE_CASE extends UseCase, REPOSITORY extends Repository> {

    protected USE_CASE useCase;

    protected REPOSITORY mockRepository;

    @Mock
    protected Messenger mockMessenger;

    @Mock
    protected Scheduler mockThreadScheduler;

    @Mock
    protected Scheduler mockPostExecutionScheduler;

    @Before
    public void setUp() {
        mockRepository = createRepository();
        useCase = createUseCase();
    }

    protected abstract USE_CASE createUseCase();

    protected abstract REPOSITORY createRepository();

    public abstract void testBuildUseCaseObservable();

    protected void testBuildUseCaseObservable(Object requestData, Action0 action) {
        useCase.buildObservable(requestData);
        action.call();
        verifyNoMoreInteractions(mockRepository);
        verifyZeroInteractions(mockThreadScheduler);
        verifyZeroInteractions(mockPostExecutionScheduler);
    }
}
