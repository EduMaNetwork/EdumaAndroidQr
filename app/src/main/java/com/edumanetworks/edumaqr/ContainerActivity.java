package com.edumanetworks.edumaqr;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.edumanetworks.edumaqr.Fragments.History;
import com.edumanetworks.edumaqr.Fragments.Scan;
import com.edumanetworks.edumaqr.Fragments.Settings;

public class ContainerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Context context = ContainerActivity.this;
    private CoordinatorLayout coordinatorLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    public View navHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setSupportActionBar(toolbar);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        homeFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getBackground().setAlpha(122);
        toolbar.getBackground().setAlpha(122);
        navHeader = navigationView.getHeaderView(0);
        Menu navigationViewMenumenu =navigationView.getMenu();
    }

    public void homeFragment(){
        try {
            Bundle args = new Bundle();

            Fragment fragment = (Fragment) new Scan();
            fragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_layout_for_activity_navigation, fragment).commit();
            // startAppAd.showAd();

        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
        getMenuInflater().inflate(R.menu.container, menu);
       // MenuItem itemCart = menu.findItem(R.id.action_cart);

        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {

            case R.id.nav_scan:
            {
                setFragment(new Scan());
                break;
            }
            case R.id.nav_history:
            {
                setFragment(new History());
                break;
            }
            case R.id.nav_settings:
            {
                setFragment(new Settings());
                break;
            }
            case R.id.nav_share:
            {
                ShareMyApp();
                break;
            }



            case R.id.nav_like:
            {
                LikeMyApp();
                break;
            }


            case R.id.nav_apps:
            {
                GoToEduma();
                break;
            }


        }
        return true;
    }

    private void GoToEduma() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://play.google.com/store/apps/developer?id=EdumaNetwork"));

        startActivity(intent);
    }

    private void LikeMyApp() {
        Uri uri = Uri.parse("market://details?id=logicchip.tabwithnavigation");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=logicchip.tabwithnavigation")));
        }
    }

    private void ShareMyApp() {
        String appLink = "https://play.google.com/store/apps/details?id=logicchip.tabwithnavigation";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Eduma Qr (Google Play Store)");

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, appLink);
        startActivity(Intent.createChooser(sharingIntent, "Eduma QR"));


    }

    private void setFragment(Fragment fragment) {

        if(fragment!=null){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_layout_for_activity_navigation,fragment);
            ft.commit();
            //  startAppAd.showAd();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

}
