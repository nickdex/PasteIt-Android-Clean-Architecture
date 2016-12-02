package io.github.nickdex.pasteit.domain.message;

import io.github.nickdex.pasteit.domain.BaseUseCaseTest;
import io.github.nickdex.pasteit.domain.repository.MessageRepository;
import io.github.nickdex.pasteit.interactor.message.GetMessages;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link GetMessages}
 *
 * @author Nikhil Warke
 *         Created on 2/12/16.
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
