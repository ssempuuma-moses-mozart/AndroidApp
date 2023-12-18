package com.example.autismapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.autismapp.Adapter.FragmentsAdapter;
import com.example.autismapp.R;
import com.example.autismapp.databinding.ActivityChatBinding;
import com.example.autismapp.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        binding =ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("LiveChat");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);




    }

    // menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.setting:
                //do something
                Intent intentSetting = new Intent(ChatActivity.this, SettingActivity.class);
                startActivity(intentSetting);
                return true;
            case R.id.groupChat:
                Intent intentAbout = new Intent(ChatActivity.this, GroupChatActivity.class);
                startActivity(intentAbout);
                return true;

            case R.id.logout:

                mAuth.signOut();
                Intent VidAc =new Intent(ChatActivity.this, SignInActivity.class);
                startActivity(VidAc);
                Toast.makeText(ChatActivity.this, "You have Signed Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}