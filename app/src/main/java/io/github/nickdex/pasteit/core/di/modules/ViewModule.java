package io.github.nickdex.pasteit.core.di.modules;

import dagger.Module;
import dagger.Provides;
import io.github.nickdex.pasteit.R;
import io.github.nickdex.pasteit.core.presentation.view.View;
import io.github.nickdex.pasteit.domain.Messenger;

@Module
public class ViewModule {

    private View view;

    public ViewModule(View view) {
        this.view = view;
    }

    @Provides
    Messenger providesMessenger() {
        return new Messenger() {
            @Override
            public void showNoNetworkMessage() {
                view.showMessage(R.string.no_internet_connection);
            }

            @Override
            public void showFromCacheMessage() {
                view.showMessage(R.string.data_from_cache);
            }
        };
    }
}
