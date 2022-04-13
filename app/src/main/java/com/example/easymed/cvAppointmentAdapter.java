package com.example.easymed;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

    public class cvAppointmentAdapter extends RecyclerView.Adapter<cvAppointmentAdapter.Viewholder> {

        private Context context;
        private ArrayList<Appointment> appArrayList;

        // Constructor
        public cvAppointmentAdapter(Context context, ArrayList<Appointment> appArrayList) {
            this.context = context;
            this.appArrayList = appArrayList;
        }

        @NonNull
        @Override
        public cvAppointmentAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // to inflate the layout for each item of recycler view.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            return new Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull cvAppointmentAdapter.Viewholder holder, int position) {
            // to set data to textview and imageview of each card layout
            Appointment app = appArrayList.get(position);
            holder.cv_time.setText(app.getTime().toString());
            holder.cv_day.setText("" + app.getDate());
            holder.cv_location.setText(app.getLocation());

            holder.edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //moves from this listing appointment activity to edit appointment passing all appointment info
                    Intent intent_add = new Intent(listing_appointmentsActivity.this, edit_appointmentActivity.class);
                    startActivity(intent_add);
                }
            });
        }

        @Override
        public int getItemCount() {
            // this method is used for showing number
            // of card items in recycler view.
            return appArrayList.size();
        }

        // View holder class for initializing of
        // your views such as TextView and Imageview.
        public class Viewholder extends RecyclerView.ViewHolder {
            private TextView cv_time, cv_day, cv_location;
            private Button edit_btn;
            public Viewholder(@NonNull View itemView) {
                super(itemView);
                cv_time = itemView.findViewById(R.id.cv_textView_time);
                cv_day = itemView.findViewById(R.id.cv_textView_day);
                cv_location = itemView.findViewById(R.id.cv_textView_location);

                edit_btn = itemView.findViewById(R.id.cv_edit_btn);

            }
        }
    }


}
