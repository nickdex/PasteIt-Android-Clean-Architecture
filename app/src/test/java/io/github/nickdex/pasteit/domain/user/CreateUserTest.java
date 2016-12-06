package io.github.nickdex.pasteit.domain.user;

import org.junit.Test;

import io.github.nickdex.pasteit.domain.BaseUseCaseTest;
import io.github.nickdex.pasteit.domain.model.User;
import io.github.nickdex.pasteit.domain.repository.UserRepository;
import io.github.nickdex.pasteit.interactor.user.CreateUser;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateUserTest extends BaseUseCaseTest<CreateUser, UserRepository> {

    private final User testUser = new User();

    @Override
    protected CreateUser createUseCase() {
        return new CreateUser(mockRepository, mockMessenger, mockThreadScheduler, mockPostExecutionScheduler);
    }

    @Override
    protected UserRepository createRepository() {
        return mock(UserRepository.class);
    }

    @Test
    @Override
    public void testBuildUseCaseObservable() {
        testBuildUseCaseObservable(testUser, () -> verify(mockRepository).createUserIfNotExists(testUser, mockMessenger));
    }
}
