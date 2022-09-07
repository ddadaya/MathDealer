package com.example.mathdealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity{
    private EditText getemail;
    private EditText getpassword;
    private Button registrate;
    private Button signin;

    FirebaseAuth authent = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Players");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrate=findViewById(R.id.reg);
        signin=findViewById(R.id.sign);
        getemail=findViewById(R.id.email);
        getpassword=findViewById(R.id.password);

        registrate.setOnClickListener(view -> {
            authent.createUserWithEmailAndPassword(getemail.getText().toString(),getpassword.getText().toString()).addOnSuccessListener(authResult->{
                reference.child(authent.getCurrentUser().getUid()).child("mail").setValue(getemail.getText().toString());
                reference.child(authent.getCurrentUser().getUid()).child("password").setValue(getpassword.getText().toString());
                reference.child(authent.getCurrentUser().getUid()).child("value1").setValue("");
                reference.child(authent.getCurrentUser().getUid()).child("value2").setValue("");
            });
        });

        signin.setOnClickListener(view -> {
            authent.signInWithEmailAndPassword(getemail.getText().toString(),getpassword.getText().toString())
                    .addOnCompleteListener(this,task->{
                if(task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, game_logic.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Sth wrong", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}



