package com.example.mathdealer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class game_logic extends AppCompatActivity{

private Button check;
private TextView firstn,secondn,score;
private EditText answer;
private Toast mMyToast;
int first_value,second_value,correct,counter=0;

String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
FirebaseDatabase Database = FirebaseDatabase.getInstance();
DatabaseReference our_bd = Database.getReference().child("Players").child(uid);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_logic);

        check=findViewById(R.id.check);
        firstn=findViewById(R.id.firstn);
        secondn=findViewById(R.id.secondn);
        answer=findViewById(R.id.answer);
        score=findViewById(R.id.score);



        our_bd.child("value1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().length()!=0) {
                    first_value=Integer.valueOf(dataSnapshot.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        our_bd.child("value2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().length()!=0) {
                    second_value=Integer.valueOf(dataSnapshot.getValue().toString());
                    correct=first_value+second_value;
                    firstn.setText(String.valueOf(first_value));
                    secondn.setText(String.valueOf(second_value));
                } else{
                    nextone();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        check.setOnClickListener(view -> {

            if (answer.length()==0){
                mMyToast.makeText(this,"Input the answer",Toast.LENGTH_SHORT).show();
            } else {
                if(answer.getText().toString().equals(String.valueOf(correct))){
                    mMyToast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
                    counter++;
                    score.setText(String.valueOf(counter));
                    answer.setText("");
                    nextone();
                } else {
                    mMyToast.makeText(this,"Incorrect",Toast.LENGTH_SHORT).show();
                    counter--;
                    score.setText(String.valueOf(counter));
                    answer.setText("");
                }

            }
        });
    }

    public int randomnum() {
        Random ran = new Random();
        return ran.ints(-5, 5).findAny().getAsInt();
    }
    public void nextone(){
        first_value=randomnum();
        second_value=randomnum();
        firstn.setText(String.valueOf(first_value));
        secondn.setText(String.valueOf(second_value));
        correct=first_value+second_value;
        our_bd.child("value1").setValue(first_value);
        our_bd.child("value2").setValue(second_value);
    }
}