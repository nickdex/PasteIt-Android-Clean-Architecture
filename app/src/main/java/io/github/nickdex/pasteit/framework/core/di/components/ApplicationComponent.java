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

package io.github.nickdex.pasteit.framework.core.di.components;

import dagger.Component;
import io.github.nickdex.pasteit.AndroidApplication;
import io.github.nickdex.pasteit.framework.core.di.AppScope;
import io.github.nickdex.pasteit.framework.core.di.modules.ApplicationModule;
import io.github.nickdex.pasteit.framework.core.di.modules.CacheModule;
import io.github.nickdex.pasteit.framework.core.di.modules.EntityStoreModule;
import io.github.nickdex.pasteit.framework.core.di.modules.RepositoryModule;
import io.github.nickdex.pasteit.framework.core.di.modules.ViewModule;
import io.github.nickdex.pasteit.messages.CopyClipIntentService;

/**
 * A component whose lifetime is the life of the application.
 */
@AppScope
@Component(modules = {ApplicationModule.class,
        RepositoryModule.class,
        CacheModule.class,
        EntityStoreModule.class})
public interface ApplicationComponent {

    ViewComponent plus(ViewModule viewModule);

    void inject(AndroidApplication app);

    void inject(CopyClipIntentService service);
}
