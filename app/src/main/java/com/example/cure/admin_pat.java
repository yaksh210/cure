package com.example.cure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class admin_pat extends AppCompatActivity {

    RecyclerView recyclerView;
    myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pat);


        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<manage_pat> options=
                new FirebaseRecyclerOptions.Builder<manage_pat>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Patient"),manage_pat.class)
                        .build();

        adapter=new myadapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s) {

        FirebaseRecyclerOptions<manage_pat> options=
                new FirebaseRecyclerOptions.Builder<manage_pat>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Patient").orderByChild("firstName").startAt(s).endAt(s+"\uf8ff"),manage_pat.class)
                        .build();


        adapter=new myadapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}