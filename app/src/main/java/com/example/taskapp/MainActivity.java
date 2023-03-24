package com.example.taskapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.task.R;
import com.example.taskapp.ContactActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // In your onCreate method, set up the GoogleSignInOptions object with the scopes you need:
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope("https://www.googleapis.com/auth/contacts.readonly"))
                .build();

        // Create a GoogleSignInClient object with the GoogleSignInOptions object:
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set an OnClickListener on the sign-in button to start the sign-in flow:
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        loginBtn = findViewById(R.id.submitBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            // Get the signed-in account from the task:
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // If authentication succeeded, display the user's contacts:
            Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
            intent.putExtra("account", account);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Log.e(TAG, "Authentication failed", e);
            Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
        }
    }
}