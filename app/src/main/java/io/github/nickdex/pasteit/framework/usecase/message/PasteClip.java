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

package io.github.nickdex.pasteit.framework.usecase.message;

import javax.inject.Inject;
import javax.inject.Named;

import io.github.nickdex.pasteit.framework.domain.Messenger;
import io.github.nickdex.pasteit.framework.domain.model.ClipItem;
import io.github.nickdex.pasteit.framework.domain.repository.MessageRepository;
import io.github.nickdex.pasteit.framework.usecase.UseCase;
import rx.Observable;
import rx.Scheduler;

/**
 * Sends a {@link ClipItem} message to repository.
 */
public class PasteClip extends UseCase<ClipItem, Void, MessageRepository> {

    @Inject
    public PasteClip(MessageRepository repository,
                     Messenger messenger,
                     @Named("Thread") Scheduler threadScheduler,
                     @Named("PostExecution") Scheduler postExecutionScheduler) {
        super(repository, messenger, threadScheduler, postExecutionScheduler);
    }

    @Override
    public Observable<Void> buildObservable(ClipItem clipItem) {
        return repository.pasteClip(clipItem, messenger);
    }

}
