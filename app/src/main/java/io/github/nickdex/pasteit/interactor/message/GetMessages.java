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

package io.github.nickdex.pasteit.interactor.message;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.model.ClipItem;
import io.github.nickdex.pasteit.domain.model.Device;
import io.github.nickdex.pasteit.domain.repository.MessageRepository;
import io.github.nickdex.pasteit.interactor.UseCase;
import rx.Observable;
import rx.Scheduler;

/**
 * Fetches all {@link ClipItem} messages for the requested device from repository.
 */
public class GetMessages extends UseCase<Device, List<ClipItem>, MessageRepository> {

    @Inject
    public GetMessages(MessageRepository repository,
                       Messenger messenger,
                       @Named("Thread") Scheduler threadScheduler,
                       @Named("PostExecution") Scheduler postExecutionScheduler) {
        super(repository, messenger, threadScheduler, postExecutionScheduler);
    }

    @Override
    public Observable<List<ClipItem>> buildObservable(Device device) {
        return repository.getClips(device, messenger);
    }
}
