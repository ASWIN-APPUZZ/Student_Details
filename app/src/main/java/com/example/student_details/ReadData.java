package com.example.student_details;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadData extends AppCompatActivity {

    ListView lst;
    FirebaseFirestore fstore;
    List<String> values = new ArrayList<>();
    // ArrayList <String> values= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readdata);

        fstore = FirebaseFirestore.getInstance();
        lst=findViewById(R.id.lst);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,values);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ReadData.this,UpdateActivity.class));
            }
        });
        fstore.collection("Students")
                //.whereEqualTo("course","Mpad")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ReadData.this, "Read Data Successful", Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Log.d("The Data is: ", document.getId()+"=>"+document.getData());

                        values.add("\n" + "Name: "+document.getString("Name")+"  \nEmail: "+document.getString("email")+"    \nCourse: "+document.getString("course"));
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(ReadData.this, "Data Read Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}