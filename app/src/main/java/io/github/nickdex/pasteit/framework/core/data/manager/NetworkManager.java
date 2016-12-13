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

package io.github.nickdex.pasteit.framework.core.data.manager;

/**
 * Manager for checking state of network and managing listeners.
 */
public interface NetworkManager {

    /**
     * Check network availability
     *
     * @return true if there is network connection and false if not
     */
    boolean isNetworkAvailable();

    /**
     * Enable listening to network availability
     */
    void start();

    /**
     * Disable listening to network availability
     */
    void stop();

    /**
     * Add a listener to network availability
     *
     * @param tag      unique id of a listener
     * @param listener network availability listener
     */
    void add(String tag, Listener listener);

    /**
     * Remove a listener to network availability by a unique tag
     *
     * @param tag unique id of a listener
     */
    void remove(String tag);

    /**
     * Network availability listener
     */
    interface Listener {

        /**
         * Is triggered when network connection appears
         */
        void onNetworkAvailable();
    }
}
