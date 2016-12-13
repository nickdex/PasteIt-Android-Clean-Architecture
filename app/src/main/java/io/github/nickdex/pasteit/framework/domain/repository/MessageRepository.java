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

package io.github.nickdex.pasteit.framework.domain.repository;

import java.util.List;

import io.github.nickdex.pasteit.framework.domain.Messenger;
import io.github.nickdex.pasteit.framework.domain.model.ClipItem;
import io.github.nickdex.pasteit.framework.domain.model.Device;
import rx.Observable;

/**
 * Interface that represents a Repository for clips.
 */
public interface MessageRepository extends Repository {

    /**
     * Returns a observable which will emit a list of all clips for a given userId.
     *
     * @param device    The device for which clips are to be fetched.
     * @param messenger Doesn't show any messages yet.
     * @return The observable which will emit a list of all clips for a given userId.
     */
    Observable<List<ClipItem>> getClips(Device device, Messenger messenger);

    /**
     * Pastes a clip.
     *
     * @param message   The clip to be pasted.
     * @param messenger Doesn't show any messages yet.
     * @return The observable which doesn't emit anything.
     */
    Observable<Void> pasteClip(ClipItem message, Messenger messenger);
}
