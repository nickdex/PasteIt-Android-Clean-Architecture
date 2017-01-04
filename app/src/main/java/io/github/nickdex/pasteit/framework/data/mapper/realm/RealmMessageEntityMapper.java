/*
 * Copyright © 2017 Nikhil Warke
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

package io.github.nickdex.pasteit.framework.data.mapper.realm;

import javax.inject.Inject;

import io.github.nickdex.pasteit.framework.core.data.mapper.BaseMapper;
import io.github.nickdex.pasteit.framework.data.entity.MessageEntity;
import io.github.nickdex.pasteit.framework.data.entity.realm.RealmMessageEntity;

/**
 * Mapper class to convert messages in Realm into MessageEntity and vice versa.
 */
public class RealmMessageEntityMapper extends BaseMapper<RealmMessageEntity, MessageEntity> {

    @Inject
    public RealmMessageEntityMapper() {
    }

    /**
     * Method which converts msgEntity to a Realm message entity.
     *
     * @param msgEntity The message entity that contains some data.
     * @return Realm message entity mapped with data from msgEntity.
     */
    @Override
    public RealmMessageEntity mapToFirst(MessageEntity msgEntity) {
        if (msgEntity == null) {
            return null;
        }

        RealmMessageEntity realmEntity = new RealmMessageEntity();
        realmEntity.setId(msgEntity.getId());
        realmEntity.setDeviceType(msgEntity.getDeviceType());
        realmEntity.setSenderEmail(msgEntity.getSenderEmail());
        realmEntity.setText(msgEntity.getText());
        realmEntity.setTimestamp(msgEntity.getTimestamp());
        return realmEntity;
    }

    /**
     * Method which converts Realm message entity to a message entity.
     *
     * @param realmEntity The message entity that contains data.
     * @return The clip item mapped with data from Realm message entity.
     */
    @Override
    public MessageEntity mapToSecond(RealmMessageEntity realmEntity) {
        if (realmEntity == null) {
            return null;
        }

        MessageEntity msgEntity = new MessageEntity();
        msgEntity.setId(realmEntity.getId());
        msgEntity.setDeviceType(realmEntity.getDeviceType());
        msgEntity.setSenderEmail(realmEntity.getSenderEmail());
        msgEntity.setText(realmEntity.getText());
        msgEntity.setTimestamp(realmEntity.getTimestamp());
        return msgEntity;
    }
}
