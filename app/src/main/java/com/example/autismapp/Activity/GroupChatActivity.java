package com.example.autismapp.Activity;
// Necessary Android and Firebase imports
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.autismapp.Adapter.ChatAdapter;
import com.example.autismapp.Model.MessageModel;
import com.example.autismapp.R;
import com.example.autismapp.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;// Declaring an instance of ActivityGroupChatBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat); // Sets the layout for this activity
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());// Inflates the view using the binding
        setContentView(binding.getRoot()); // Sets the content view to the root view in the binding
        getSupportActionBar().hide();// Hides the action bar
        // Setting up the behavior for the back arrow button
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigates back to the ChatActivity when the arrow is clicked
                Intent intent = new Intent(GroupChatActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
       // Initializing Firebase database and required variables
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();// ArrayList to hold message models

        final String senderId = FirebaseAuth.getInstance().getUid();// Retrieves the current user's ID
        // Setting the title for the group chat
        binding.userName.setText("Autism Squad Group");
       // Initializing the chat adapter and attaching it to the RecyclerView
        final ChatAdapter adapter = new ChatAdapter(messageModels, this);
        binding.chatRecyclerView.setAdapter(adapter);

        // Setting up the layout manager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);
       // Listening for changes in the "Group Chat" node in Firebase database
        database.getReference().child("Group Chat")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // Clears the messageModels ArrayList
                                messageModels.clear();
                                // Iterates through the snapshot and adds MessageModel objects to the list
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                                    messageModels.add(model);
                                }
                                adapter.notifyDataSetChanged();// Notifies the adapter of changes in the data
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handles the event of data retrieval being cancelled
                                // (You can add specific actions to handle this scenario)

                            }
                        });
       // Setting up the behavior when the send button is clicked
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = binding.enterMessage.getText().toString();// Retrieves the message
                final MessageModel model = new MessageModel(senderId, message);// Creates a message model
                model.setTimestamp(new Date().getTime());// Sets the timestamp for the message
                binding.enterMessage.setText("");// Clears the message input field
                // Sends the message to the "Group Chat" node in Firebase
                database.getReference().child("Group Chat")
                        .push()
                        .setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // Shows a toast indicating the message was sent successfully
                                Toast.makeText(GroupChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}