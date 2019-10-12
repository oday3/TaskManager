package com.example.taskmanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.taskmanager.app.AppController;
import com.example.taskmanager.modle.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailedTaskActivity extends AppCompatActivity {

    ImageView imageView ;
    TextView idTask,title , description , data , time;
    ProgressDialog progressDialog;
    String ggg;

    public final static String DELETE_TASK_URL= "http://www.beststrends.com/tasks/api/index.php/deleteTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detailed_task_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageView = findViewById(R.id.image_of_task_in_detail);
        idTask = findViewById(R.id.id_of_task_in_detail);
        title = findViewById(R.id.title_of_task_in_detail);
        description  = findViewById(R.id.description_of_task_in_detail);
        data = findViewById(R.id.date_text_in_detail);
        time = findViewById(R.id.time_text_in_detail);



        Bundle bundle = getIntent().getExtras();

        Task item = (Task) bundle.getSerializable("current task");

        setTitle(item.getTitle());

         ggg = String.valueOf(item.getId());

        Picasso.get().load(item.getImage()).into(imageView);
        idTask.setText(item.getId()+"");
        title.setText(item.getTitle());

        description.setText(item.getDescription());
        data.setText(item.getDate());
        time.setText(item.getTime());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete_mwnue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

            showDeleteConfirmationDialog();
        }


        return super.onOptionsItemSelected(item);
    }



    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogThem);
        builder.setMessage("هل انت متاكد من عملية الحذف؟");
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                   deketTask();

            }
        });
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void deketTask(){

        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_TASK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            if(object.getBoolean("status")) {

                                hideDialog();
                                getParentActivityIntent();
                                finish();
                                Toast.makeText(DetailedTaskActivity.this,"تم الحذف بنجاح",Toast.LENGTH_SHORT).show();

                            }
                            else {

                                hideDialog();
                                Toast.makeText(DetailedTaskActivity.this,"مشكلة في الاتصال بالانترنت",Toast.LENGTH_SHORT).show();

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
                map.put("task_id",ggg);
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);



    }







    public void showDialog() {
        progressDialog = new ProgressDialog(DetailedTaskActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("جاري حذف المهمة...");
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
