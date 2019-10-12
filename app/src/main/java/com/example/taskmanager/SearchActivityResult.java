package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.taskmanager.adapters.TaskAdapter;
import com.example.taskmanager.app.AppController;
import com.example.taskmanager.modle.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivityResult extends AppCompatActivity {

    public static final String SERARCH_URL = "http://www.beststrends.com/tasks/api/index.php/searchTask";

    ArrayList<Task> serachResult = new ArrayList<>();


    RecyclerView recyclerView ;
    TaskAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

     //   progressBar = findViewById(R.id.loading_tasks_progress_bar_in_result);



        Toolbar toolbar = (Toolbar) findViewById(R.id.search_task_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String title =  getIntent().getStringExtra("titleOFActivity");

            setTitle("نتائج البحث حول : "+title);


        recyclerView = findViewById(R.id.list_of_tasks_in_search_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivityResult.this));

                  getSearch(title);




      //  Log.d("gggggggggggggggggg",serachResult.toString());



       // hideDialog();

    }

//    public void hideDialog() {
//
//        int isVisible =  progressBar.getVisibility();
//
//        if (isVisible == 0){
//
//            progressBar.setVisibility(View.GONE);
//
//        }
//    }

    public void getSearch(String string) {

        showDialog();

        Map<String, String> map = new HashMap<>();

        map.put("title", string);

        JSONObject jsonObject = new JSONObject(map);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SERARCH_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject object = response;

                try {
                    if(object.getBoolean("status")) {

                        Toast.makeText(SearchActivityResult.this,object.getString("message"),Toast.LENGTH_SHORT).show();



                   JSONObject currentTask= object.getJSONObject("tasks");


                       //    Log.d("aaaaaaaaaaa",currentTask.toString());

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

                     //   Toast.makeText(SearchActivityResult.this,titleOfTask+idOfTask+"",Toast.LENGTH_SHORT).show();

                        serachResult.add(task);

                        adapter = new TaskAdapter(serachResult,SearchActivityResult.this);


                        recyclerView.setAdapter(adapter);

                        hideDialog();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    public void showDialog() {
        progressBar = findViewById(R.id.loading_tasks_progress_bar_in_search);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideDialog() {

        int isVisible =  progressBar.getVisibility();

        if (isVisible == 0){

            progressBar.setVisibility(View.GONE);

        }
    }

}

