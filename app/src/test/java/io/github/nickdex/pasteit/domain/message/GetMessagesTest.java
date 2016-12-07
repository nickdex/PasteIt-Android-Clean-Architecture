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
