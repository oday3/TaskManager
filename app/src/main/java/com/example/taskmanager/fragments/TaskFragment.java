package com.example.taskmanager.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.taskmanager.R;
import com.example.taskmanager.adapters.TaskAdapter;
import com.example.taskmanager.app.AppController;
import com.example.taskmanager.modle.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {
    public static final String GET_ALL_TASKS_URL = "http://www.beststrends.com/tasks/api/index.php/getTasks";
    ArrayList<Task> tasks = new ArrayList<>();
    ProgressBar progressBar;
    RecyclerView recyclerView ;
    TaskAdapter adapter;

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_task, container, false);


        getAllTasks(root);
        return  root;
    }


    public void getAllTasks(final View view){

        showDialog(view);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GET_ALL_TASKS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.getBoolean("status") == true){


                                JSONArray arrayOfTasks = response.getJSONArray("tasks");

                                for(int i = 0 ; i < arrayOfTasks.length(); i++){

                                    JSONObject currentTask =  arrayOfTasks.getJSONObject(i);

                                    int idOfTask = currentTask.getInt("id");
                                    String titleOfTask = currentTask.getString("title");
                                    String summaryOfTask = currentTask.getString("summary");
                                    String descriptionOfTask  = currentTask.getString("description");
                                    String dateOfTask = currentTask.getString("date");
                                    String imageOfTask = currentTask.getString("image");

                                    String[] parts = dateOfTask.split(" ");
                                    String date =  parts[0];
                                    String time = parts[1] ;

                                    String []timeWithoutSeconds = time.split(":");

                                    String realTime = timeWithoutSeconds[0] +":"+ timeWithoutSeconds[1];

                                    Task task = new Task();

                                    task.setId(idOfTask);
                                    task.setTitle(titleOfTask);
                                    task.setSummary(summaryOfTask);
                                    task.setDescription(descriptionOfTask);
                                    task.setDate(date);
                                    task.setTime(realTime);
                                    task.setImage(imageOfTask);

                                    tasks.add(task);
                                }

                                Toast.makeText(getActivity(),"تم جلب البيانات",Toast.LENGTH_SHORT).show();
                                hideDialog();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        long seed = System.nanoTime();
                        Collections.shuffle(tasks, new Random(seed));

                        adapter = new TaskAdapter(tasks,getActivity());
                        recyclerView = view.findViewById(R.id.list_of_tasks);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


    }


    public void showDialog(View view) {
        progressBar = view.findViewById(R.id.loading_tasks_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideDialog() {

        int isVisible =  progressBar.getVisibility();

        if (isVisible == 0){

            progressBar.setVisibility(View.GONE);

        }
    }


}
