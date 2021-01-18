package com.example.whereto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.whereto.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FaqAbout extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_about);

        ListView resultsListView = (ListView) findViewById(R.id.results_listview);

        HashMap<String, String> nameAddresses = new HashMap<>();
        nameAddresses.put("How do you upload a picture?","When checking-in, you can choose to take a picture to share when prompted");
        nameAddresses.put("How do you change your username?","You can go to the navigation bar and click on settings. From there, you can edit your personal information including username");
        nameAddresses.put("How do you check crowded places?","Every category of Whereto provided, it will appear along with the number of people have visited according to the timestamp that you set");
        nameAddresses.put("What if I forgot my password?","You can change password by providing your registered account in the forgot password page. You will receive a temporary password that being sent to your registered email address ");
        nameAddresses.put("Can I delete my account","Yes!, you can navigate to the settings page from the sidebar menu to disable or delete your account");

        List<HashMap<String,String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item, //initialize simple adapter that takes 5 arguments
                new String[]{"First Line","Second Line"}, //key that adapter will look for
                new int[]{R.id.text1,R.id.text2}); //xml file

        Iterator it = nameAddresses.entrySet().iterator(); //iterate through the data
        while(it.hasNext()){
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next(); //allow to tell java that we just want the key value pair of the current iteration
            resultsMap.put("First Line", pair.getKey().toString()); //statement convert to string
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }
        resultsListView.setAdapter(adapter);
    }
}