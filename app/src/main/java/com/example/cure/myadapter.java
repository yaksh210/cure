package com.example.cure;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class myadapter extends FirebaseRecyclerAdapter<manage_pat,myadapter.myviewholder>
    {
        public myadapter(@NonNull FirebaseRecyclerOptions<manage_pat> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final manage_pat model)
    {
            holder.firstname.setText(model.getFirstName());
            holder.email.setText(model.getEmail());
            holder.lastname.setText(model.getLastName());


            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final DialogPlus dialogPlus=DialogPlus.newDialog(holder.email.getContext())
                            .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                            .setExpanded(true,1000)
                            .create();

                        View myview=dialogPlus.getHolderView();

                    final EditText firstName=myview.findViewById(R.id.ufname);
                    final EditText lastName=myview.findViewById(R.id.ulname);
                    final EditText email=myview.findViewById(R.id.uemail);
                    Button submit=myview.findViewById(R.id.usubmit);

                    firstName.setText(model.getFirstName());
                    lastName.setText(model.getLastName());
                    email.setText(model.getEmail());

                    dialogPlus.show();


                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Map<String,Object> map =new HashMap<>();

                            map.put("firstName",firstName.getText().toString());
                            map.put("lastName",lastName.getText().toString());
                            map.put("email",email.getText().toString());

                            FirebaseDatabase.getInstance().getReference().child("Patient")
                                    .child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            dialogPlus.dismiss();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            dialogPlus.dismiss();
                                        }
                                    });


                        }
                    });


                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder builder=new AlertDialog.Builder(holder.email.getContext());
                    builder.setTitle("Do you Want to delete?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("Patient")
                                    .child(getRef(position).getKey()).removeValue();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    builder.show();

                }
            });



    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        Button edit,delete;
        TextView firstname,lastname,email;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            firstname=(TextView) itemView.findViewById(R.id.txtfname);
            lastname=(TextView)itemView.findViewById(R.id.txtlname);
            email=(TextView) itemView.findViewById(R.id.txtemail);

            edit=(Button)itemView.findViewById(R.id.edit);
            delete=(Button)itemView.findViewById(R.id.delete);

        }
    }
}
