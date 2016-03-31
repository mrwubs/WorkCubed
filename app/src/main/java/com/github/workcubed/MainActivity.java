package com.github.workcubed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    Dbhelper db;

    /**
     *
     * @param savedInstanceState Saved date form previous instances
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Dbhelper(this);
        final ListView lv = (ListView) findViewById(R.id.listView);

        List<String> your_array_list = new ArrayList<String>(db.getAllProjects());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, your_array_list);
        lv.setAdapter(arrayAdapter);
//        finish();
//        startActivity(getIntent());

//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> parent, View view,
//                                        int position, long id) {
//                    System.out.println(parent);
//                    projectDisplay(view);
//                }
//            });

    }

    /**
     *
     * @param menu Information to put into the menu
     * @return     Returns true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    /**
     *
     * @param item  Information to put into the action bar
     * @return      If id is valid, returns true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    /**
//     *
//     * @return                         returns projectList -> the completed list of info for project
//     * @throws ClassNotFoundException  Class missing or corrupt
//     * @throws SQLException            SQL connection failed
//     * @throws InstantiationException  Problem with instantiation
//     * @throws IllegalAccessException  Illegal attempt to access
//     */
//    public static ArrayList<String> getAllProjects() {
//
////        Log.i("MainActivity", "Yo Yo Yo");
////        String myDriver = "com.mysql.jdbc.Driver";
////        String url = "jdbc:mysql://workedcubed.cj4vqnmu1rwe.us-west-2.rds.amazonaws.com";
////        String username = "crew";
////        String password = "pizza123";
////        Class.forName(myDriver).newInstance();
//
//
//
////            Statement stm;
////            stm = connection.createStatement();
////            String sql = "Select * From Customer";
////            ResultSet rst;
////            rst = stm.executeQuery(sql);
//
//        ArrayList<String> projectList = new ArrayList<>();
////            while (rst.next()) {
//
//        int id =     0;
//        Date date = new Date();
//        long creation = date.getTime();
//        Timestamp creation_date = new Timestamp(creation);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 2017);
//        calendar.set(Calendar.MONTH, 5);
//        calendar.set(Calendar.DAY_OF_MONTH, 17);
//        creation = date.getTime();
//        Timestamp deadline = new Timestamp(creation);
////            Date deadline =;
//        while (id < 6) {
//            String name = "Project" + id;
////                Project project = new Project(rst.getInt("ID"), rst.getString("Description"), rst.getTimestamp("DateCreated"),
////                        rst.getDate("DateDeadLine"), rst.getInt("Completed"), rst.getString("Name"));
//            Project project = new Project(id, "this is a project", creation_date, deadline, 0, name);
//            projectList.add(project.getName());
//            id++;
//        }
//        return projectList;
//
//    }

    /**
     *
     * @param view The button ID
     */
    public void addProject(View view) {
        finish();
        Intent intent = new Intent(this, ProjectForm.class);
        startActivity(intent);
    }

    /**
     *
     * @param view The button ID
     */
    public void projectDisplay(View view) {
        Intent intent = new Intent(this,ProjectView.class);
        startActivity(intent);

    }

    public void reportOnClick (View view) {
        Intent intent = new Intent(this, Report.class);
        startActivity(intent);
    }

}
