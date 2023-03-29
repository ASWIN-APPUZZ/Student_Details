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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class DeleteActivity extends AppCompatActivity {

    EditText name;
    Button delete;
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        name = findViewById(R.id.txt_namefield);
        delete = findViewById(R.id.btn_del);

        fstore=FirebaseFirestore.getInstance();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dname = name.getText().toString().trim();

// Remove the 'capital' field from the document
                Map<String, Object> delete = new HashMap<>();
                delete.put("Name", FieldValue.delete());

                fstore.collection("Students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()&&!task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentId = documentSnapshot.getId();

                            fstore.collection("Students").document(documentId).update(delete).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    DocumentReference docRef = fstore.collection("Name").document(dname);
                                    docRef.update(delete).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(DeleteActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(DeleteActivity.this,Home_Activity.class));
                                        }

                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DeleteActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(DeleteActivity.this, "Failed to Delete", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(getApplicationContext(),Home_Activity.class));
                    }

                });


            }


        });
    }
}