package com.example.student_details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText name,email,course;
    Button submit;
    String sname,semail,scrs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.txt_getname);
        email = findViewById(R.id.txt_getemail);
        course = findViewById(R.id.txt_getcourse);

        submit=findViewById(R.id.btn_getsubmit);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname=name.getText().toString().trim();
                semail=email.getText().toString().trim();
                scrs=course.getText().toString().trim();

                Map<String, Object> student = new HashMap<>();
                student.put("Name",sname);
                student.put("email",semail);
                student.put("course",scrs);

                fstore.collection("Students").add(student).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(MainActivity.this, "Details Entered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,ReadData.class));
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

                name.setText("");
                email.setText("");
                course.setText("");
            }
        });


    }
}