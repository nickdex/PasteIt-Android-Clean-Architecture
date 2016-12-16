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

package io.github.nickdex.pasteit.framework.data.store.firebase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.github.nickdex.pasteit.framework.data.entity.MessageEntity;
import io.github.nickdex.pasteit.framework.data.store.MessageEntityStore;
import rx.Observable;
import timber.log.Timber;

/**
 * Description
 */
public class MockFirebaseMessageEntityStore implements MessageEntityStore {

    private static List<MessageEntity> dummyMessages = new ArrayList<>();

    static {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setText("Dummy");
        messageEntity.setId("HI0kjptc7QhvcwEa7ndOPFInB6x2");
        messageEntity.setDeviceType("CHROME");
        messageEntity.setSenderEmail("nikwarke@gmail.com");
        messageEntity.setTimestamp(1481615967057L);
        dummyMessages.add(messageEntity);

    }

    private Gson gson;
    private Type type;

    @Inject
    public MockFirebaseMessageEntityStore() {
        gson = new Gson();
        type = new TypeToken<List<MessageEntity>>() {
        }.getType();
    }

    @Override
    public Observable<List<MessageEntity>> getMessages(String userId) {

        JsonReader jsonReader = new JsonReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("res/raw/dummy.json")));
        dummyMessages = gson.fromJson(jsonReader, type);
        Timber.d(dummyMessages.toString());
        return Observable.just(dummyMessages);
    }

    @Override
    public Observable<Void> postMessage(MessageEntity message) {
        File file = new File(this.getClass().getClassLoader().getResource("res/raw/dummy.json").getFile());
        JsonWriter jsonWriter = null;
        try {
            jsonWriter = new JsonWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dummyMessages.add(message);

        if (jsonWriter != null)
            gson.toJson(message, type, jsonWriter);

        return null;
    }
}
