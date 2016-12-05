package io.github.nickdex.pasteit.data.repository;

import java.util.Collection;

import javax.inject.Inject;

import io.github.nickdex.pasteit.core.data.manager.NetworkManager;
import io.github.nickdex.pasteit.core.repository.RepositoryImpl;
import io.github.nickdex.pasteit.data.entity.UserEntity;
import io.github.nickdex.pasteit.data.mapper.UserEntityClipItemMapper;
import io.github.nickdex.pasteit.data.store.UserEntityStore;
import io.github.nickdex.pasteit.domain.Messenger;
import io.github.nickdex.pasteit.domain.listener.OnUserChangedListener;
import io.github.nickdex.pasteit.domain.model.User;
import io.github.nickdex.pasteit.domain.repository.UserRepository;
import io.github.nickdex.pasteit.interactor.UseCase;
import rx.Observable;

/**
 * Class that contains implementations of methods in {@link UserRepository}.
 */
public class UserRepositoryImpl extends RepositoryImpl<UserEntityStore, UserEntityClipItemMapper> implements UserRepository, OnUserChangedListener {

    @Inject
    public UserRepositoryImpl(NetworkManager networkManager,
                              UserEntityStore cloudStore,
                              UserEntityClipItemMapper userEntityClipItemMapper) {
        super(networkManager, cloudStore, userEntityClipItemMapper);
    }

    @Override
    public Observable<String> createUserIfNotExists(User user, Messenger messenger) {
        if (networkManager.isNetworkAvailable()) {
            return cloudStore.createUserIfNotExists(entityDmMapper.mapToFirst(user));
        } else {
            return Observable.<String>empty().doOnCompleted(messenger::showNoNetworkMessage);
        }
    }

    @Override
    public Observable<User> getUser(String userId, Messenger messenger) {
        Observable<UserEntity> entityObservable;
        entityObservable = cloudStore.getUser(userId);
        return entityObservable.map(userEntity -> entityDmMapper.mapToSecond(userEntity));
    }

    @Override
    public void onDataChanged(String userId) {
        Collection<UseCase> useCases = useCasesMap.values();
        for (UseCase useCase : useCases) {
            if (useCase instanceof OnUserChangedListener) {
                ((OnUserChangedListener) useCase).onDataChanged(userId);
            }
        }
    }
}
