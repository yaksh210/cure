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

public class admin_doc extends AppCompatActivity {

    RecyclerView recyclerView_doc;
    myadapter_doc adapter_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_doc);


        recyclerView_doc=findViewById(R.id.recview_doc);
        recyclerView_doc.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<manage_doc> recyclerOptions=
                new FirebaseRecyclerOptions.Builder<manage_doc>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor"),manage_doc.class)
                        .build();

        adapter_doc=new myadapter_doc(recyclerOptions);
        recyclerView_doc.setAdapter(adapter_doc);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter_doc.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter_doc.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView sv=(SearchView)item.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                prosearch(q);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String q) {
                prosearch(q);
                return false;
            }
        });




        return super.onCreateOptionsMenu(menu);
    }

    private void prosearch(String q) {

        FirebaseRecyclerOptions<manage_doc> recyclerOptions=
                new FirebaseRecyclerOptions.Builder<manage_doc>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor").orderByChild("dfirstName").startAt(q).endAt(q+"\uf8ff"),manage_doc.class)
                        .build();

        adapter_doc=new myadapter_doc(recyclerOptions);
        adapter_doc.startListening();
        recyclerView_doc.setAdapter(adapter_doc);



    }
}