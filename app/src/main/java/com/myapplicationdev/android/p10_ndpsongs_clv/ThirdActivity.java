package com.myapplicationdev.android.p10_ndpsongs_clv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etTitle, etYear;
    //    RadioButton rb1, rb2, rb3, rb4, rb5;
    Button btnCancel, btnUpdate, btnDelete;
    //    RadioGroup rg;
    RatingBar ratingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_third));

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etID = (EditText) findViewById(R.id.etID);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etYear = (EditText) findViewById(R.id.etYear);
        ratingbar = (RatingBar) findViewById(R.id.ratingBar2);


        Intent i = getIntent();
        final Tasks currentTasks = (Tasks) i.getSerializableExtra("task");

        etID.setText(currentTasks.getId() + "");
        etTitle.setText(currentTasks.getTitle());
        etYear.setText(currentTasks.getYearReleased() + "");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentTasks.setTitle(etTitle.getText().toString().trim());
                currentTasks.setYearReleased(Integer.valueOf(etYear.getText().toString().trim()));


                int selectedRB = ratingbar.getNumStars();
                RatingBar ratingbar = (RatingBar) findViewById(selectedRB);
//                currentTasks.setStars(Integer.parseInt(ratingbar.toString()));

//                int rating = (int) ratingbar.getRating();
//                currentTasks.setStars(rating);

                int result = dbh.updateTask(currentTasks);
                if (result > 0) {
                    Toast.makeText(ThirdActivity.this, "Task updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ThirdActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete this task?");
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //code here
                        DBHelper dbh = new DBHelper(ThirdActivity.this);
                        int result = dbh.deleteTask(currentTasks.getId());
                        if (result > 0) {
                            Toast.makeText(ThirdActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                myBuilder.setNegativeButton("NO", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



}