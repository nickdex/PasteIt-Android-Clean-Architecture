package io.github.nickdex.pasteit.domain.user;

import org.junit.Test;

import io.github.nickdex.pasteit.domain.BaseUseCaseTest;
import io.github.nickdex.pasteit.domain.repository.UserRepository;
import io.github.nickdex.pasteit.interactor.user.GetUser;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Testing case of getting the user.
 */

public class GetUserTest extends BaseUseCaseTest<GetUser, UserRepository> {
    private static final String FAKE_USER_ID = "123";

    @Override
    protected GetUser createUseCase() {
        return new GetUser(mockRepository, mockMessenger, mockThreadScheduler, mockPostExecutionScheduler);
    }

    @Override
    protected UserRepository createRepository() {
        return mock(UserRepository.class);
    }

    @Test
    @Override
    public void testBuildUseCaseObservable() {
        testBuildUseCaseObservable(FAKE_USER_ID, () -> {
            verify(mockRepository).getUser(FAKE_USER_ID, mockMessenger);
        });
    }
}
