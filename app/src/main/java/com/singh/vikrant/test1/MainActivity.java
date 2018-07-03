package com.singh.vikrant.test1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

//import com.facebook.stetho.Stetho;
import com.singh.vikrant.test1.Fragmets.Fragment_Adapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
//        initializerBuilder.enableWebKitInspector( Stetho.defaultInspectorModulesProvider(this) );
//        initializerBuilder.enableDumpapp( Stetho.defaultDumperPluginsProvider(getApplicationContext()) );
//        Stetho.Initializer initializer = initializerBuilder.build();
//        Stetho.initialize(initializer);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MOVIE");


        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        // Create an adapter that knows which fragment should be shown on each page
        Fragment_Adapter adapter = new Fragment_Adapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager);
        // int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(2);
    }

    }
