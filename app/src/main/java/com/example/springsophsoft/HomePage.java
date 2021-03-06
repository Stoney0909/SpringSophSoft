package com.example.springsophsoft;

import android.content.Intent;
import android.os.Bundle;

import com.example.springsophsoft.Helper.Databasehelper;
import com.example.springsophsoft.Helper.TransactionHelper;
import com.example.springsophsoft.ui.Notification.historyMessage;
import com.example.springsophsoft.ui.signUpAndLogIn.LogIn;

import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
 //  public String username;
 TextView textCartItemCount;
   // int mCartItemCount = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent i = getIntent();
         String username = i.getStringExtra("Send_Username");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_Search, R.id.nav_THistory,
                R.id.nav_AddCash, R.id.nav_Balance, R.id.nav_Setting)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Databasehelper db = new Databasehelper(this);

        View header = navigationView.getHeaderView(0);
        TextView balanceText = (TextView) header.findViewById(R.id.balanceTextView);
        String balance = "Balance: $" + db.getBalance();
        balanceText.setText(balance);

        TextView usernameText = (TextView) header.findViewById(R.id.usernameTextView);
        usernameText.setText("Username: " + LogIn.getString());

        TextView nameText = (TextView) header.findViewById(R.id.EmailTextView);
        String email = db.getEmail();
        nameText.setText("Email: " + email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        final MenuItem menuItem = menu.findItem(R.id.message);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);

            }
        });
        return true;
    }
    private void setupBadge() {
        TransactionHelper db = new TransactionHelper(this);
        int count=db.numberOfNotification();
        if (textCartItemCount != null) {
            if (count == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(count, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.message: {
                open();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
   // @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        TransactionHelper db= new TransactionHelper(this);
//        switch (item.getItemId()){
//            case R.id.message:
//
//                if(db.getInfo(1)==""){
////                    Toast.makeText(this," Nothing To Show",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    String message= db.getInfo(2)+" have requested $"+db.getInfo(1)+" on "+db.getInfo(3) ;
//                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//                }
//             default:
//               return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void open(){
        Intent intent = new Intent(this, historyMessage.class);
        startActivity(intent);
    }
}
