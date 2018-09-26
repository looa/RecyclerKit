package com.biubiu.recyclerkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.biubiu.kit.core.KitBaseAdapter;
import com.biubiu.sample.BaselineKit;
import com.biubiu.sample.CardCoverKit;
import com.biubiu.sample.CardCoverSmallKit;
import com.biubiu.sample.CardImageKit;
import com.biubiu.sample.CardTextKit;
import com.biubiu.sample.TitleKit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Object> list = new ArrayList<>();

        list.add(new TitleKit.Data());
        list.add(new CardCoverKit.Data());
        list.add(new CardCoverSmallKit.Data());
        list.add(new CardCoverSmallKit.Data());
        list.add(new CardTextKit.Data());
        list.add(new CardImageKit.Data());
        list.add(new CardCoverSmallKit.Data());
        list.add(new CardCoverSmallKit.Data());
        list.add(new TitleKit.Data());
        list.add(new CardCoverSmallKit.Data());
        list.add(new CardTextKit.Data());
        list.add(new CardImageKit.Data());
        list.add(new CardCoverSmallKit.Data());
        list.add(new CardCoverSmallKit.Data());
        list.add(new BaselineKit.Data());

        KitBaseAdapter adapter = new KitBaseAdapter(this, list);

        RecyclerView recycler = findViewById(R.id.recycler);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

    }
}
