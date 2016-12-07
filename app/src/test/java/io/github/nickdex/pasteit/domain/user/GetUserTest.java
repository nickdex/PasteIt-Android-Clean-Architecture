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
