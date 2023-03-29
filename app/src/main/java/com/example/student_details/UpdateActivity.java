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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class UpdateActivity extends AppCompatActivity {

    EditText curr,newname;
    Button update;

    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        curr = findViewById(R.id.txt_curname);
        newname = findViewById(R.id.txt_newname);
        update = findViewById(R.id.btn_update);



        fstore = FirebaseFirestore.getInstance();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // UpdateData(mcurr,mnew);

                String mcurr = curr.getText().toString().trim();
                String mnew = newname.getText().toString().trim();

                curr.setText("");
                newname.setText("");

                Map<String,Object> UserDetails = new HashMap<>();
                UserDetails.put("Name",mnew);
                fstore.collection("Students").whereEqualTo("Name", mcurr).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()&&!task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentId = documentSnapshot.getId();
                            fstore.collection("Students").document(documentId).update(UserDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(UpdateActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UpdateActivity.this, "Updation Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(UpdateActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(UpdateActivity.this,ReadData.class));
                    }
                });

            }
        });

    }

 //   private void UpdateData(String mcurr, String mnew) {
//        Map<String,Object> UserDetails = new HashMap<>();
//        UserDetails.put("Name",newname);
//        fstore.collection("Students").whereEqualTo("Name","").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()&&!task.getResult().isEmpty()) {
//                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
//                    String documentId = documentSnapshot.getId();
//                    fstore.collection("Students").document(documentId).update(UserDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(UpdateActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(UpdateActivity.this, "Updation Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }else {
//                    Toast.makeText(UpdateActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
 //   }
}