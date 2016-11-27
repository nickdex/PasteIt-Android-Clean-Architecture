package io.github.nickdex.pasteit.data.store.firebase;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.github.nickdex.pasteit.core.data.entity.Entity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action2;
import rx.subscriptions.Subscriptions;

/**
 * Base class for Firebase operations.
 */
public abstract class FirebaseEntityStore {

    protected DatabaseReference database;

    public FirebaseEntityStore() {
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
     * Runs for the first time.
     *
     * @param databaseReference Reference to Firebase Reference that needs to be used.
     * @param value             Object of Models in data layer that have to be saved.
     * @param successResponse   UId of value when operation completes successfully.
     * @return {@link Observable} that will notify when value has been published.
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
            }
            subscriber.add(Subscriptions.create(() -> query.removeEventListener(eventListener)));
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
