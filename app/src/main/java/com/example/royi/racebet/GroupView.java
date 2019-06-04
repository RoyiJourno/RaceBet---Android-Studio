package com.example.royi.racebet;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GroupView extends AppCompatActivity {

    private ListView lv;
    public static ArrayList<ModelGroupView> modelArrayList;
    private CustomAdapterGroupView customAdapter;
    Map<String,String> listGroup = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);

        lv = (ListView) findViewById(R.id.listView);
        customAdapter = new CustomAdapterGroupView(this);

        initListGroup();

        modelArrayList = getModel();
        lv.setAdapter(customAdapter);

       /* myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child2: children) {
                    userList.add(child2.getValue(User.class));

                }

                modelArrayList = getModel();
                lv.setAdapter(customAdapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        userList.get(position).updateVote();
                        recreate();
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void initListGroup() {
        listGroup.put("Royi","1000000");
        listGroup.put("Hai","1");
        listGroup.put("Bar","-500");
        listGroup.put("R1oyi","1000000");
        listGroup.put("Ha2i","1");
        listGroup.put("Bar3","-500");
        listGroup.put("Royi4","1000000");
        listGroup.put("Ha5i","1");
        listGroup.put("Ba6r","-500");
        listGroup.put("Roy7i","1000000");
        listGroup.put("Hai3","1");
        listGroup.put("Ba2r","-500");
        listGroup.put("1Royi","1000000");
        listGroup.put("Ha8i","1");
        listGroup.put("Bar13","-500");
        listGroup.put("11Royi","1000000");
        listGroup.put("Ha211i","1");
        listGroup.put("Ba4r","-500");
        listGroup.put("Ro5yi","1000000");
        listGroup.put("Hai6","1");
        listGroup.put("Ba7r","-500");
        listGroup.put("Ro8yi","1000000");
        listGroup.put("Ha21i","1");
        listGroup.put("B23ar","-500");



    }

    private ArrayList<ModelGroupView> getModel(){
        ArrayList<ModelGroupView> list = new ArrayList<>();

        for (String iter:listGroup.keySet())
        {
            ModelGroupView model = new ModelGroupView();
            model.setFullname(iter);
            model.setScore(listGroup.get(iter));
            list.add(model);
        }
        return list;
    }
}

