package com.github.workcubed;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;
import java.util.Calendar;
import java.util.logging.Logger;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.app.Dialog;

import org.w3c.dom.Text;


public class TaskForm extends AppCompatActivity {

    private EditText name_of_task;
    private EditText description;
    private EditText estimated_hours;
    private EditText actual_hours;
    private TextView endDateDisplay;
    private Button endPickDate;
    private Calendar endDate;
    private Dbhelper dbHelper;
    private Button submit;
    private String project_name;

    static final int DATE_DIALOG_ID = 0;
    public final static String EXTRA_MESSAGE = "project name";

    private TextView activeDateDisplay;
    private Calendar activeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        dbHelper = new Dbhelper(this);
        final Activity temp = this;

        Intent intent = getIntent();
        project_name = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        /* capture our View elements for the end date function */
        endDateDisplay = (TextView) findViewById(R.id.deadline_text);
        endPickDate = (Button) findViewById(R.id.deadline_button);

        /* get the current date */
        endDate = Calendar.getInstance();

        /* add a click listener to the button   */
        endPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(endDateDisplay, endDate);
            }
        });

        /* display the current date (this method is below)  */
        //        updateDisplay(startDateDisplay, startDate);
        updateDisplay(endDateDisplay, endDate);
        name_of_task = (EditText) findViewById(R.id.task_name);
        description = (EditText) findViewById(R.id.task_description);
        estimated_hours = (EditText) findViewById(R.id.est_hours);
        actual_hours = (EditText) findViewById(R.id.hours_spent);



        submit = (Button) findViewById(R.id.submit_task);

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String usertask = name_of_task.getText().toString();
                final String userDescription = description.getText().toString();
                final String userEstimated = estimated_hours.getText().toString();
                final String userActual = actual_hours.getText().toString();

                final String endingdate = new StringBuilder().append(endDate.get(Calendar.MONTH) + 1)
                        .append("-")
                        .append(endDate.get(Calendar.DAY_OF_MONTH))
                        .append("-")
                        .append(endDate.get(Calendar.YEAR)).append(" ").toString();

                dbHelper.insertTask(usertask, userDescription, userActual, userEstimated, project_name, endingdate, 0);
                Intent intent = new Intent(temp, ProjectView.class);
                intent.putExtra(EXTRA_MESSAGE, project_name);
                startActivity(intent);
            }
        });



    }

    /**
     *
     * @param dateDisplay
     * @param date
     */
    public void showDateDialog(TextView dateDisplay, Calendar date) {
        activeDateDisplay = dateDisplay;
        activeDate = date;
        showDialog(DATE_DIALOG_ID);
    }

    private OnDateSetListener dateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            activeDate.set(Calendar.YEAR, year);
            activeDate.set(Calendar.MONTH, monthOfYear);
            activeDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay(activeDateDisplay, activeDate);
            //unregisterDateDisplay();
        }
    };

    private void unregisterDateDisplay() {
        activeDateDisplay = null;
        activeDate = null;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
                break;
        }
    }

    private void updateDisplay(TextView dateDisplay, Calendar date) {
        dateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(date.get(Calendar.MONTH) + 1).append("-")
                        .append(date.get(Calendar.DAY_OF_MONTH)).append("-")
                        .append(date.get(Calendar.YEAR)).append(" "));

    }
}
