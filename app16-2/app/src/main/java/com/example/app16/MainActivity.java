package com.example.app16;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;

import com.example.app16.ui.main.SectionsPagerAdapter;
import com.example.app16.ui.main.ModelFacade;


public class MainActivity extends AppCompatActivity {
    ModelFacade model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化对象获取context
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        //？
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        //获取实例
        model = ModelFacade.getInstance(this);


    }

}