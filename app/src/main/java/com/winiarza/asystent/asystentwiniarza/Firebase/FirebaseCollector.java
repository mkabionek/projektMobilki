package com.winiarza.asystent.asystentwiniarza.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.winiarza.asystent.asystentwiniarza.Firebase.ModelFirebase.Product;

import static android.content.ContentValues.TAG;

public class FirebaseCollector {

    public void getRecipe() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Product morelowka = singleSnapshot.getValue(Product.class);
                    Log.d(TAG, "JSON: " + morelowka.getDescription());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        reference.addValueEventListener(postListener);
    }
}


//        Query query = reference
//                .orderByKey()
//                .equalTo("wiśniówka");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                    Product morelowka = singleSnapshot.getValue(Product.class);
//                    Log.d(TAG, "JSON: " + morelowka.getDescription());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
////                Toast.makeText(SettingsActivity.this, "Unknown Error", Toast.LENGTH_SHORT);
//            }
//        });
//    }

//}
