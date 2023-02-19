package com.example.cure;

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

public class myadapter_doc extends FirebaseRecyclerAdapter<manage_doc,myadapter_doc.myviewholder>
{
    public myadapter_doc(@NonNull FirebaseRecyclerOptions<manage_doc> recyclerOptions) {
        super(recyclerOptions);
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_sing,parent,false);
        return new myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final manage_doc model) {

        holder.dfirstName.setText(model.getDfirstName());
        holder.dlastName.setText(model.getDlastName());
        holder.demail.setText(model.getDemail());
        holder.certificate.setText(model.getCertificate());

        holder.edit_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.demail.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent_doc))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();

                final EditText dfirstName=myview.findViewById(R.id.udfname);
                final EditText dlastName=myview.findViewById(R.id.udlname);
                final EditText demail=myview.findViewById(R.id.udemail);
                final EditText certificate=myview.findViewById(R.id.udcerti);
                Button submit=myview.findViewById(R.id.udsubmit);

                dfirstName.setText(model.getDfirstName());
                dlastName.setText(model.getDlastName());
                demail.setText(model.getDemail());
                certificate.setText(model.getCertificate());
                dialogPlus.show();


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map =new HashMap<>();

                        map.put("dfirstName",dfirstName.getText().toString());
                        map.put("dlastName",dlastName.getText().toString());
                        map.put("demail",demail.getText().toString());
                        map.put("certificate",certificate.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("Doctor")
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


        holder.delete_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder=new AlertDialog.Builder(holder.demail.getContext());
                builder.setTitle("Do you Want to delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Doctor")
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

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView dfirstName,dlastName,demail,certificate;
        Button edit_doc,delete_doc;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            dfirstName=(TextView) itemView.findViewById(R.id.txtdfname);
            dlastName=(TextView)itemView.findViewById(R.id.txtdlname);
            demail=(TextView) itemView.findViewById(R.id.txtdemail);
            certificate=(TextView)itemView.findViewById(R.id.txtdcertificate);

            edit_doc=(Button)itemView.findViewById(R.id.edit_doc);
            delete_doc=(Button)itemView.findViewById(R.id.delete_doc);

        }
    }
}
