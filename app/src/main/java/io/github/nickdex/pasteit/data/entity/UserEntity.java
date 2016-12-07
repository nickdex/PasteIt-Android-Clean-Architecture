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

package io.github.nickdex.pasteit.data.entity;

import io.github.nickdex.pasteit.core.data.entity.Entity;

/**
 * Model class to represent a user in data layer.
 */
public class UserEntity implements Entity {

    private String id;
    private String name;
    private String email;
    private boolean chrome = false;
    private boolean phone = false;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasChrome() {
        return chrome;
    }

    public void setChrome(boolean chrome) {
        this.chrome = chrome;
    }

    public boolean hasPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }
}
