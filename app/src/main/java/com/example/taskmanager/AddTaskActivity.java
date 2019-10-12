package com.example.taskmanager;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.taskmanager.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {

    TextInputLayout title , summary , description ,image;
    Button addTask;
    ProgressDialog progressDialog;

    String titleOfTask,summaryOfTask,descriptionOfTask,imageOfTask;

    public final static String ADD_TASK_URL= "http://www.beststrends.com/tasks/api/index.php/addTask";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_task_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addTask = findViewById(R.id.add_task_button);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View root = findViewById(android.R.id.content);

                 List<TextInputLayout> textInputLayouts = findViewsWithType(root,TextInputLayout.class);

                  title = findViewById(R.id.title_text_input_layout);
                  summary = findViewById(R.id.summary_text_input_layout);
                  description=findViewById(R.id.description_text_input_layout);
                  image = findViewById(R.id.image_text_input_layout);

                   titleOfTask = title.getEditText().getText().toString();
                   summaryOfTask = summary.getEditText().getText().toString();
                   descriptionOfTask = description.getEditText().getText().toString();
                   imageOfTask = image.getEditText().getText().toString();

                if(checkIsValid(textInputLayouts)){



                       getStringRequest();
                }

            }
        });

    }



    public static <T extends View> List<T> findViewsWithType(View root, Class<T> type) {
        List<T> views = new ArrayList<>();
        findViewsWithType(root, type, views);
        return views;
    }

    private static <T extends View> void findViewsWithType(View view, Class<T> type, List<T> views) {
        if (type.isInstance(view)) {
            views.add(type.cast(view));
        }

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                findViewsWithType(viewGroup.getChildAt(i), type, views);
            }
        }
    }


    public boolean checkIsValid(List<TextInputLayout> textInputLayouts) {

        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editTextString = textInputLayout.getEditText().getText().toString();
            if (editTextString.isEmpty()) {
                textInputLayout.setError("الحقل مطلوب");
                return false;
            }else {

                textInputLayout.setError(null);
            }
        }

        return true;
    }


//    public void addTask(){
//
//
//        showDialog();
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,ADD_TASK_URL,null,
//
//
//
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {

//                        try {
//                            if(response.getBoolean("status")) {
//
//                                hideDialog();
//                                finish();
//                                Toast.makeText(AddTaskActivity.this,"تمت الاضافة بنجاح",Toast.LENGTH_SHORT).show();
//
//                            }
//                            else {
//
//                                hideDialog();
//                                Toast.makeText(AddTaskActivity.this,"مشكلة في الاتصال بالانترنت",Toast.LENGTH_SHORT).show();
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(AddTaskActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show(); ;
//                        }
//
//                        hideDialog();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("hyyyyyyyyyyyyyyyyzm", error.getMessage());
//                        hideDialog();
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map=new HashMap<>();
//                map.put("title",titleOfTask);
//                map.put("summary",summaryOfTask);
//                map.put("description",descriptionOfTask);
//                map.put("image",imageOfTask);
//                map.put("longitude","0");
//                map.put("latitude","0");
//
//
//                return map;
//            }
//        };
//
//        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
//
//
//
//    }


    public void getStringRequest() {
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_TASK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("خخخخخخخخخخخخخخخخخخخخ", response);




                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            if(object.getBoolean("status")) {

                                    hideDialog();

                                     finish();
                                    Toast.makeText(AddTaskActivity.this,"تمت الاضافة بنجاح",Toast.LENGTH_SHORT).show();

                                }
                                else {

                                    hideDialog();
                                    Toast.makeText(AddTaskActivity.this,"مشكلة في الاتصال بالانترنت",Toast.LENGTH_SHORT).show();

                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("hzm", error.getMessage());
                        hideDialog();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("title",titleOfTask);
                map.put("summary",summaryOfTask);
                map.put("description",descriptionOfTask);
                map.put("image",imageOfTask);
                map.put("longitude","0");
                map.put("latitude","0");
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    public void showDialog() {
        progressDialog = new ProgressDialog(AddTaskActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("جاري اضافة المهمة...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
