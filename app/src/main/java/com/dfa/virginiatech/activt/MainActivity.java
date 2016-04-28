package com.dfa.virginiatech.activt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import android.widget.AdapterView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, SurveyFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener,
        AgendaFragment.OnFragmentInteractionListener, EventFragment.OnFragmentInteractionListener,
        OnDateSelectedListener,
        NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    // ~ Fields ...................................................................................
    FragmentManager fragmentManager;
    Calendar timeOfEvent;
    private String selectedDate;
    private String selectedDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timeOfEvent = Calendar.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        selectedDescription = "BODYPUMP™ is the original barbell class that strengthens your entire body. This 60-minute workout challenges all your major muscle groups one song at a time by using the best weight-room exercises like squats, presses, lifts and curls.";

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

    /**
     * Switches between container fragments (survey, calendar, agenda) when the user clicks
     * their desired fragment from the sidebar.
     *
     * @param item
     * @return
     */
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

    /**
     * Called when the submit button is clicked in the Survey Fragment
     * Starts CalendarFragment
     * @param v the view that is clicked
     */
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

    /**
     * Called when a date is selected in the CalendarFragment.
     * Starts AgendaFragment
     * @param widget the calendarView that is visible
     * @param date the date that is selected or unselected
     * @param selected true if the date has been selected, false if unselected
     */
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        selectedDate = "" + getMonth(date.getMonth()) + " " + date.getDay() + ", " + date.getYear();
        timeOfEvent.set(date.getYear(), date.getMonth(), date.getDay());

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

    /**
     * Takes an integer 0-11 inclusive and converts it to a month as a string
     * @param month integer 0-11 inclusive
     * @return the name of the corresponding month as a string
     */
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    /**
     * Called in AgendaFragment when an item is selected
     * Sends to EventFragment
     * @param parent adapter view that is parent to the list
     * @param view the view that is clicked
     * @param position item position in the ListView
     * @param id identification??
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EventFragment eventFragment = new EventFragment();
        Bundle args = new Bundle();
        //put args

        args.putString("selectedDate", selectedDate);
        args.putString("selectedDescription", selectedDescription);
        eventFragment.setArguments(args);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, eventFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Adds event to the default calendar.
     * Called when Add Event button is clicked in EventFragment
     */
    public void onAddEvent(View v) {
        // hard coding time values for specified app
        timeOfEvent.set(Calendar.HOUR_OF_DAY, 16);
        timeOfEvent.set(Calendar.MINUTE, 0);

        Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
        calendarIntent.setType("vnd.android.cursor.item/event");
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, timeOfEvent.getTimeInMillis());

        // create an endOfEvent object to pass through via intent.
        Calendar endOfEvent = timeOfEvent;
        endOfEvent.add(Calendar.HOUR, 1);

        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endOfEvent.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "BODYPUMP");
        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, selectedDescription);
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "War Memorial Gym: Dance Room");
        calendarIntent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY");
        startActivity(calendarIntent);
    }


}
