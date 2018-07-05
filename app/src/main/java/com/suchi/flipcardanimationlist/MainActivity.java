package com.suchi.flipcardanimationlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn_flip;
    private RecyclerView list;
    private ArrayList<ListData> arrayList;
    public static boolean isFront = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_flip = findViewById(R.id.btn_flip);
        list = findViewById(R.id.rc_list);
        setList();
        FlipListener();
        setAdapter();

    }

    private void setList() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ListData data = new ListData();
            data.setBackText("Byee");
            data.setFrontText("Hi");
            arrayList.add(data);
        }
    }

    private void setAdapter() {
        list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        list.setHasFixedSize(true);
        ListAdapter adapter = new ListAdapter(this, arrayList);
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        list.setAdapter(adapter);
    }

    private void FlipListener() {
        btn_flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventData data = new EventData();
                if (isFront) {
                    isFront = false;
                    data.setFront(false);
                } else {
                    isFront = true;
                    data.setFront(true);
                }
                data.setPosition(0);
                EventBus.getDefault().post(data);
            }
        });
    }
}
