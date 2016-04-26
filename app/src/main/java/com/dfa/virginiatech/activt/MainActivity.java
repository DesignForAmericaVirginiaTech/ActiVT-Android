package com.dfa.virginiatech.activt;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, SurveyFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener,
        AgendaFragment.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener,
        OnDateSelectedListener,
        NavigationView.OnNavigationItemSelectedListener {

    // ~ Fields ...................................................................................
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Start Survey Fragment
        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {
            //restored from a previous state
            if (savedInstanceState != null) {
                return;
            }
            // Create new Survey Fragment
            SurveyFragment surveyFragment = new SurveyFragment();
            //if started via intent, probably won't be doing this
            surveyFragment.setArguments(getIntent().getExtras());

            //Add fragment to the container
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, surveyFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_survey) {
            SurveyFragment surveyFragment = new SurveyFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, surveyFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_calendar) {
            CalendarFragment calendarFragment = new CalendarFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, calendarFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_agenda) {
            AgendaFragment agendaFragment = new AgendaFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, agendaFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //do something
    }

    @Override
    public void onClick(View v) {
        //Create Calendar Fragment
        CalendarFragment calendarFragment = new CalendarFragment();
        Bundle args = new Bundle();
        //args.putInt(ArticleFragment.ARG_POSITION, position);
        calendarFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //replace current survey frag
        transaction.replace(R.id.fragment_container, calendarFragment);
        transaction.addToBackStack(null);

        //commit
        transaction.commit();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        String selectedDate = ""+findMonth(date.getMonth())+" "+date.getDay()+", "+date.getYear();


        //Create Agenda Fragment
        AgendaFragment agendaFragment = new AgendaFragment();
        Bundle args = new Bundle();
        //put args

        args.putString("selectedDate", selectedDate);
        agendaFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //replace current calendar fragment
        transaction.replace(R.id.fragment_container, agendaFragment);
        transaction.addToBackStack(null);

        //commit
        transaction.commit();
    }

    public String findMonth(int i) {
        switch (i) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
            default:
                return "May";
        }
    }

    public void onAddEvent() {
        //throw event to default calendar thingy
    }

//    //Create Event Fragment
//    EventFragment eventFragment = new EventFragment();
//    Bundle args = new Bundle();
//    //args.putString();
//    eventFragment.setArguments(args);
//
//    FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//    //replace current survey frag
//    transaction.replace(R.id.fragment_container, eventFragment);
//    transaction.addToBackStack(null);
//
//    //commit
//    transaction.commit();
}
