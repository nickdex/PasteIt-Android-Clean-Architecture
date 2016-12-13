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

package io.github.nickdex.pasteit.framework.core.data.manager.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.Map;

import io.github.nickdex.pasteit.framework.core.data.manager.NetworkManager;

/**
 * Implementation of {@link NetworkManager}
 */
public class NetworkManagerImpl extends BroadcastReceiver implements NetworkManager {

    private Context context;

    private IntentFilter connectivityIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

    private Map<String, Listener> listeners = new HashMap<>();

    public NetworkManagerImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        context.registerReceiver(this, connectivityIntentFilter);
    }

    @Override
    public void stop() {
        context.unregisterReceiver(this);
    }

    @Override
    public void add(String tag, Listener listener) {
        listeners.put(tag, listener);
    }

    @Override
    public void remove(String tag) {
        listeners.remove(tag);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkAvailable()) {
            if (!isInitialStickyBroadcast()) {
                for (Listener listener : listeners.values()) {
                    if (listener != null) {
                        listener.onNetworkAvailable();
                    }
                }
            }
        }
    }
}
