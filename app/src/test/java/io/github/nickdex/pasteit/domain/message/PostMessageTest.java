package io.github.nickdex.pasteit.domain.message;

import io.github.nickdex.pasteit.domain.BaseUseCaseTest;
import io.github.nickdex.pasteit.domain.model.ClipItem;
import io.github.nickdex.pasteit.domain.repository.MessageRepository;
import io.github.nickdex.pasteit.interactor.message.PostMessage;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test for {@link PostMessage}
 *
 * @author Nikhil Warke
 *         Created on 2/12/16.
 */

public class PostMessageTest extends BaseUseCaseTest<PostMessage, MessageRepository> {

    private final ClipItem clipItem = new ClipItem();

    @Override
    protected PostMessage createUseCase() {
        return new PostMessage(mockRepository, mockMessenger, mockThreadScheduler, mockPostExecutionScheduler);
    }

    @Override
    protected MessageRepository createRepository() {
        return mock(MessageRepository.class);
    }

    @Override
    public void testBuildUseCaseObservable() {
        testBuildUseCaseObservable(clipItem, () -> verify(mockRepository.postMessage(clipItem, mockMessenger)));
    }
}
