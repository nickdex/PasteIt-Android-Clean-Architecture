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

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.nickdex.pasteit.framework.core.data.entity.Entity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action2;
import rx.subscriptions.Subscriptions;

/**
 * Base class for Firebase operations.
 */
abstract class FirebaseEntityStore {

    protected DatabaseReference database;

    FirebaseEntityStore() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    protected <T> Observable<T> get(Query query, Class<T> itemClass, boolean subscribeForSingleEvent) {
        return getQuery(query, ((subscriber, dataSnapshot) -> subscriber.onNext(extract(dataSnapshot, itemClass))), subscribeForSingleEvent);
    }

    protected <T> Observable<List<T>> getList(Query query, Class<T> itemClass, boolean subscribeForSingleEvent) {
        return getQuery(query, ((subscriber, dataSnapshot) -> subscriber.onNext(extractList(dataSnapshot, itemClass))), subscribeForSingleEvent);
    }

    protected <T extends Entity, R> Observable<R> create(DatabaseReference databaseReference, T value, R successResponse) {
        return postQuery(databaseReference, value, successResponse, true);
    }

    protected <T extends Entity, R> Observable<R> update(DatabaseReference databaseReference, T value, R successResponse) {
        return postQuery(databaseReference, value, successResponse, false);
    }

    protected <R> Observable<R> delete(DatabaseReference databaseReference, R successResponse) {
        return deleteQuery(databaseReference, successResponse);
    }

    /**
     * Creates a node at specified path in {@link DatabaseReference} if it doesn't exist already.
     *
     * @param databaseReference Reference to Firebase {@link DatabaseReference} where node is to be created.
     * @param value             Object of a Model class that needs to be saved.
     * @param successResponse   Response Object that should be pushed to subscriber when operation completes successfully.
     * @return {@link Observable} that emits successResponse.
     */
    protected <T extends Entity, R> Observable<R> createIfNotExists(DatabaseReference databaseReference, T value, R successResponse) {
        return Observable.create(subscriber -> {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        postQuery(databaseReference, value, successResponse, false)
                                .subscribe(subscriber);
                    } else {
                        subscriber.onNext(successResponse);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    subscriber.onError(new FirebaseException(databaseError.getMessage()));
                }
            });
        });
    }

    private <T> Observable<T> getQuery(Query query, Action2<Subscriber<? super T>, DataSnapshot> onNextAction, boolean subscribeForSingleEvent) {
        return Observable.create(subscriber -> {
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    onNextAction.call(subscriber, dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    subscriber.onError(new FirebaseException(databaseError.getMessage()));
                }
            };
            if (subscribeForSingleEvent) {
                query.addListenerForSingleValueEvent(eventListener);
            } else {
                query.addValueEventListener(eventListener);
                subscriber.add(Subscriptions.create(() -> query.removeEventListener(eventListener)));
            }
        });
    }

    private <T extends Entity, R> Observable<R> postQuery(DatabaseReference databaseReference, T value, R successResponse, boolean newChild) {
        return Observable.create(subscriber -> {
            DatabaseReference reference = databaseReference;
            if (newChild) {
                if (value.getId() == null) {
                    reference = databaseReference.push();
                    value.setId(reference.getKey());
                } else {
                    reference = databaseReference.child(value.getId());
                }
            }
            reference.setValue(value, ((databaseError, databaseReference1) -> {
                if (databaseError == null) {
                    subscriber.onNext(successResponse);
                } else {
                    subscriber.onError(new FirebaseException(databaseError.getMessage()));
                }
            }));
        });
    }

    private <R> Observable<R> deleteQuery(DatabaseReference databaseReference, R successResponse) {
        return Observable.create(subscriber -> {
            databaseReference.removeValue((databaseError, databaseReference1) -> {
                if (databaseError == null) {
                    subscriber.onNext(successResponse);
                } else {
                    subscriber.onError(new FirebaseException(databaseError.getMessage()));
                }
            });
        });
    }

    private <T> List<T> extractList(DataSnapshot dataSnapshot, Class<T> itemClass) {
        Iterable<DataSnapshot> items = dataSnapshot.getChildren();
        List<T> result = new ArrayList<>();
        for (DataSnapshot item : items) {
            result.add(extract(item, itemClass));
        }
        return result;
    }

    private <T> T extract(DataSnapshot dataSnapshot, Class<T> itemClass) {
        return dataSnapshot.getValue(itemClass);
    }
}
