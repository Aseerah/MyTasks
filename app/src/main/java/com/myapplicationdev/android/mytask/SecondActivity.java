package com.myapplicationdev.android.mytask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

	ListView lv;
    ArrayList<Tasks> tasksList;
//	ArrayAdapter adapter;
	String moduleCode;
	int requestCode = 9;
    Button btn5Stars;

    CustomAdapter caNDP;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " +  getResources().getText(R.string.title_activity_second));

		lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);

		DBHelper dbh = new DBHelper(this);
        tasksList = dbh.getAllTask();
        dbh.close();

//		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songList);
//		lv.setAdapter(adapter);


        caNDP = new CustomAdapter(this, R.layout.row, tasksList);

        lv.setAdapter(caNDP);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setTitle("Alert!");
                myBuilder.setMessage("Are you sure you want to modify the task? \n");
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("MODIFY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                        i.putExtra("task", tasksList.get(position));
                        startActivityForResult(i, requestCode);

                    }
                });
                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                tasksList.clear();
                tasksList.addAll(dbh.getAllTaskByStars(5));
                caNDP.notifyDataSetChanged();
            }
        });
    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == this.requestCode && resultCode == RESULT_OK){
			DBHelper dbh = new DBHelper(this);
            tasksList.clear();
            tasksList.addAll(dbh.getAllTask());
            dbh.close();
            caNDP.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


}
