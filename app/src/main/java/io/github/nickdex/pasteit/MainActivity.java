package io.github.nickdex.pasteit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final String CLIP_ITEMS_CHILD = "clip_items";
    public static final String ANONYMOUS = "anonymous";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_INVITE = 1;

    GoogleApiClient googleApiClient;

    private String username;

    // View Variables
    private ImageButton sendButton;
    private RecyclerView clipItemRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private EditText itemEditText;

    // Firebase Instance Variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference firebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ClipItem, ItemViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = ANONYMOUS;
        final String PHONE = getString(R.string.phone_device_type);
        final String CHROME = getString(R.string.chrome_device_type);

        // Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            username = firebaseUser.getDisplayName();
        }

        // Google Api Client
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        // Assign fields
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        clipItemRecyclerView = (RecyclerView) findViewById(R.id.itemRecycleView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        itemEditText = (EditText) findViewById(R.id.itemEditText);
        sendButton = (ImageButton) findViewById(R.id.sendButton);

        // Firebase Database
        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ClipItem, ItemViewHolder>(
                ClipItem.class,
                R.layout.item_clip,
                ItemViewHolder.class,
                firebaseDatabaseReference.child(CLIP_ITEMS_CHILD)) {
            @Override
            protected void populateViewHolder(ItemViewHolder viewHolder, ClipItem model, int position) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.itemTextView.setText(model.getText());
                viewHolder.deviceTextView.setText(model.getDeviceName());
                //TODO Add drawable resource for chrome
                /*
                if(getString(R.string.phone_device_type).equals(model.getDeviceType()))

                    switch (model.getDeviceType()) {
                        case PHONE:
                            viewHolder.deviceImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.smartphone));
                            break;
                        case CHROME:
                            viewHolder.deviceImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.chrome));
                    }
                 */
            }
        };

        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int clipItemCount = firebaseRecyclerAdapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 || (positionStart >= (clipItemCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    clipItemRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        clipItemRecyclerView.setLayoutManager(linearLayoutManager);
        clipItemRecyclerView.setAdapter(firebaseRecyclerAdapter);

        itemEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                } else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipItem item = new ClipItem(itemEditText.getText().toString(), android.os.Build.MODEL, PHONE /* Change to tablet as required */);
                firebaseDatabaseReference.child(CLIP_ITEMS_CHILD)
                        .push().setValue(item);
                itemEditText.setText("");
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed: " + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;
        TextView deviceTextView;
        CircleImageView deviceImageView;

        ItemViewHolder(View v) {
            super(v);
            itemTextView = (TextView) itemView.findViewById(R.id.itemTextView);
            deviceTextView = (TextView) itemView.findViewById(R.id.deviceTextView);
            deviceImageView = (CircleImageView) itemView.findViewById(R.id.deviceImageView);
        }
    }
}
