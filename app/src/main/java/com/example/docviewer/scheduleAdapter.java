package com.example.docviewer;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class scheduleAdapter extends RecyclerView.Adapter<scheduleAdapter.ExampleViewHolder> {
    DatabaseReference rootRef;
    DatabaseReference userRef;
    String save;
     String mail;
    DatabaseReference myRef;
    private ArrayList<schedule> scheduleArrayList;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public Button button;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.time);
            mTextView2 = itemView.findViewById(R.id.availability);
            mTextView3 = itemView.findViewById(R.id.bookedBy);
            button=itemView.findViewById(R.id.book);
        }
    }
    public scheduleAdapter(ArrayList<schedule> scheduleArrayList) {
        this.scheduleArrayList= scheduleArrayList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, int position) {
        final schedule currentItem = scheduleArrayList.get(position);


        holder.mTextView2.setText(currentItem.getAvailable());
        holder.mTextView3.setText(currentItem.getBookby());
        mail=currentItem.getMail();

        if(currentItem.getTimeslot().length()==1){
            if(currentItem.getTimeslot().equals("9")){
                currentItem.setTimeslot('0'+currentItem.getTimeslot()+":"+(Integer.parseInt(currentItem.getTimeslot())+1));
            }
            else
                currentItem.setTimeslot('0'+currentItem.getTimeslot()+":"+"0"+(Integer.parseInt(currentItem.getTimeslot())+1));
        }
        else if(currentItem.getTimeslot().length()==2){
            currentItem.setTimeslot(currentItem.getTimeslot()+":"+(Integer.parseInt(currentItem.getTimeslot())+1));
        }
        holder.mTextView1.setText(currentItem.getTimeslot());

        if(currentItem.getAvailable()=="false"){
            holder.mTextView2.setText(currentItem.getAvailable());
            holder.mTextView2.setTextColor(Color.RED);
            holder.mTextView1.setTextColor(Color.RED);
            holder.button.setClickable(false);
            holder.button.setBackgroundColor(Color.RED);
            holder.button.setText("Booked");

        }





        rootRef= FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("Members");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            if (ds.child("email").getValue().equals(mail)) {
                                Iterator<DataSnapshot> items=ds.child("table").getChildren().iterator();
                                while(items.hasNext()){
                                    DataSnapshot item=items.next();
                                    if(item.child("time").getValue(String.class).equals(currentItem.getTime())){
                                        item.child("status").getRef().setValue("false");
                                        break;
                                    }

                                }
                                break;
                            }

                        }
                        holder.button.setBackgroundColor(Color.RED);


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });
    }
    @Override
    public int getItemCount() {
        return scheduleArrayList.size();
    }
}
/*
*
*
*  if(time.length()==1){
                             if(time.equals('9')){
                                 time='0'+time+":"+(Integer.parseInt(time)+1);
                             }
                             else
                             time='0'+time+":"+"0"+(Integer.parseInt(time)+1);
                         }
                         else{
                             time=time+":"+(Integer.parseInt(time)+1);
                         }
                         *
                         *
                         *
                         *
                         *
* */