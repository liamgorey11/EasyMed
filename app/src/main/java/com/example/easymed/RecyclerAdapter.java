package com.example.easymed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
    Context context;
    String username;
    ArrayList<String> al_location;
    ArrayList<String> al_doctor;
    ArrayList<String> al_month;
    ArrayList<String> al_day;
    ArrayList<String> al_time;
    FeedReaderDbHelper dbHelper;
    FeedReaderAppointmentsDbHelper dbAppointmentsHelper;


    public RecyclerAdapter(Context context, String username, ArrayList<String> al_location, ArrayList<String> al_doctor, ArrayList<String> al_month, ArrayList<String> al_day, ArrayList<String> al_time){
        this.context = context;
        this.username = username;
        this.al_location = al_location;
        this.al_doctor = al_doctor;
        this.al_month = al_month;
        this.al_day = al_day;
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
        String str = "Appointment with "+al_doctor.get(position);
        holder.textView_Appointment.setText(str);
        holder.textView_Location.setText(al_location.get(position));
        holder.textView_Month.setText(al_month.get(position));
        holder.textView_Day.setText(al_day.get(position));
        holder.textView_Time.setText(al_time.get(position));
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();

                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {

                        SQLiteDatabase db = dbAppointmentsHelper.getReadableDatabase();

                        String selection = FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_USERNAME + " = ? AND "+FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_LOCATION + " = ? AND "+FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DOCTOR + " = ? AND "+FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_MONTH + " = ? AND "+FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DAY + " = ? AND "+FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_TIME+" = ?";
                        String[] selectionArgs = new String[]{username, al_location.get(index), al_doctor.get(index), al_month.get(index), al_day.get(index), al_time.get(index)};

                        db.delete(FeedReaderContract.FeedEntryAppointments.TABLE_NAME, selection, selectionArgs);

                        //deleteRow(new String[]{String.valueOf(et_username.getText().toString())}, db);
                        //clearTable(db);
                        //dropTable(db);
                    }
                };
                Thread objBgThread = new Thread(objRunnable);
                objBgThread.start();
                try {
                    objBgThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                al_location.remove(holder.getAdapterPosition());
                al_doctor.remove(holder.getAdapterPosition());
                al_month.remove(holder.getAdapterPosition());
                al_day.remove(holder.getAdapterPosition());
                al_time.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), al_location.size());
            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = holder.getAdapterPosition();

                Intent intent = new Intent(view.getContext(), editAppointmentActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("location", al_location.get(index));
                intent.putExtra("doctor", al_doctor.get(index));
                intent.putExtra("month", al_month.get(index));
                intent.putExtra("day", al_day.get(index));
                intent.putExtra("time", al_time.get(index));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return this.al_location.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView_Appointment;
        TextView textView_Location;
        TextView textView_Month;
        TextView textView_Day;
        TextView textView_Time;
        Button btn_delete, btn_edit;
        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textView_Appointment = itemView.findViewById(R.id.textView_Appointment);
            textView_Location = itemView.findViewById(R.id.textView_Location);
            textView_Month = itemView.findViewById(R.id.textView_Month);
            textView_Day = itemView.findViewById(R.id.textView_Day);
            textView_Time = itemView.findViewById(R.id.textView_Time);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);
        }
    }
}
