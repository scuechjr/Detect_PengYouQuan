package com.example.swee1.pengyouquan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {
    ListView contactView;
    SimpleAdapter simpleAdapter;
    List<Map<String, Object>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactView = (ListView) findViewById(R.id.contacts);
        simpleAdapter = new SimpleAdapter(this, data, R.layout.item, new String[]{"friendID", "description"}, new int[]{R.id.nameID, R.id.description});
        contactView.setAdapter(simpleAdapter);
    }
}
