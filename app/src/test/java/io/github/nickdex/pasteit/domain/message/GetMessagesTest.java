package io.github.nickdex.pasteit.domain.message;

import io.github.nickdex.pasteit.domain.BaseUseCaseTest;
import io.github.nickdex.pasteit.domain.repository.MessageRepository;
import io.github.nickdex.pasteit.interactor.message.GetMessages;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit test for {@link GetMessages}.
 */
public class GetMessagesTest extends BaseUseCaseTest<GetMessages, MessageRepository> {

    private final String CHROME = "CHROME";

    @Override
    protected GetMessages createUseCase() {
        return new GetMessages(mockRepository, mockMessenger, mockThreadScheduler, mockPostExecutionScheduler);
    }

    @Override
    protected MessageRepository createRepository() {
        return mock(MessageRepository.class);
    }

    @Override
    public void testBuildUseCaseObservable() {
        testBuildUseCaseObservable(CHROME, () -> verify(mockRepository).getMessages(CHROME, mockMessenger));
    }
}
