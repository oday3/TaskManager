package com.example.taskmanager.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.taskmanager.DetailedTaskActivity;
import com.example.taskmanager.R;
import com.example.taskmanager.modle.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {


    ArrayList<Task> data ;
    Activity activity;
    View itemView ;
    public TaskAdapter (ArrayList<Task> tasks , Activity activity){

         this.data = tasks;
         this.activity = activity;

    }

    @NonNull
    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int position) {

     final  int item =position;


         itemView = LayoutInflater.from(activity).inflate(R.layout.task_recycle_item,viewGroup,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewHolder myViewHolder, final int position) {


          myViewHolder.id.setText(data.get(position).getId()+"");
          myViewHolder.title.setText(data.get(position).getTitle());
          myViewHolder.summary.setText(data.get(position).getSummary());
          Picasso.get().load(data.get(position).getImage()).into(myViewHolder.image);
          myViewHolder.time.setText(data.get(position).getTime());
          myViewHolder.date.setText(data.get(position).getDate());
          myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DetailedTaskActivity.class);

                intent.putExtra("current task",data.get(position));

                activity.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class  MyViewHolder extends RecyclerView.ViewHolder{

        public TextView id;
        public TextView title;
        public TextView summary;
        public ImageView image;
        public TextView date;
        public TextView time;
         public LinearLayout linearLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

               id = itemView.findViewById(R.id.id_of_task);
               title = itemView.findViewById(R.id.title_of_task_in_list);
               summary = itemView.findViewById(R.id.summary_of_task_in_list);
               image = itemView.findViewById(R.id.image_of_task_in_list);
               date = itemView.findViewById(R.id.date_of_task_in_list);
               time = itemView.findViewById(R.id.time_of_task_in_list);
               linearLayout = itemView.findViewById(R.id.list_item);



        }
    }
}
