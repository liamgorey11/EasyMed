package com.example.easymed;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
    Context context;
    String username;
    ArrayList<String> al_location;
    ArrayList<String> al_doctor;
    ArrayList<String> al_date;
    ArrayList<String> al_time;
    FeedReaderDbHelper dbHelper;
    FeedReaderAppointmentsDbHelper dbAppointmentsHelper;


    public RecyclerAdapter(Context context, String username, ArrayList<String> al_location, ArrayList<String> al_doctor, ArrayList<String> al_date, ArrayList<String> al_time){
        this.context = context;
        this.username = username;
        this.al_location = al_location;
        this.al_doctor = al_doctor;
        this.al_date = al_date;
        this.al_time = al_time;
        this.dbHelper = new FeedReaderDbHelper(context);
        this.dbAppointmentsHelper = new FeedReaderAppointmentsDbHelper(context);

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String str = "Appointment with Dr. "+al_doctor.get(position);
        holder.textView_Appointment.setText(str);
        holder.textView_Location.setText(al_location.get(position));
        holder.textView_Date.setText(al_date.get(position));
        holder.textView_Time.setText(al_time.get(position));
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();

                al_location.remove(holder.getAdapterPosition());
                al_doctor.remove(holder.getAdapterPosition());
                al_date.remove(holder.getAdapterPosition());
                al_time.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), al_location.size());

                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {

                        SQLiteDatabase db = dbAppointmentsHelper.getReadableDatabase();

                        String selection = FeedReaderContract.FeedEntryAppointments._ID + " = ?";
                        String[] selectionArgs = new String[]{String.valueOf(index)};

                        int x = db.delete(FeedReaderContract.FeedEntryAppointments.TABLE_NAME, selection, selectionArgs);
                        Log.i("index ", String.valueOf(index));
                        Log.i("X ", String.valueOf(x));

                        //deleteRow(new String[]{String.valueOf(et_username.getText().toString())}, db);
                        //clearTable(db);
                        //dropTable(db);
                    }
                };
                Thread objBgThread = new Thread(objRunnable);
                objBgThread.start();
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();

                Intent intent = new Intent(view.getContext(), editAppointmentActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("index", index);
                intent.putExtra("location", al_location.get(holder.getAdapterPosition()));
                intent.putExtra("doctor", al_doctor.get(holder.getAdapterPosition()));
                intent.putExtra("date", al_date.get(holder.getAdapterPosition()));
                intent.putExtra("time", al_time.get(holder.getAdapterPosition()));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()
    {

        return this.al_location.size();
        //return 7;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView_Appointment;
        TextView textView_Location;
        TextView textView_Date;
        TextView textView_Time;
        Button btn_delete, btn_edit;
        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textView_Appointment = itemView.findViewById(R.id.textView_Appointment);
            textView_Location = itemView.findViewById(R.id.textView_Location);
            textView_Date = itemView.findViewById(R.id.textView_Date);
            textView_Time = itemView.findViewById(R.id.textView_Time);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);


        }
    }
}
