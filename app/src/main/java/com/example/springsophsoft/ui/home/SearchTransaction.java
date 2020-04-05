package com.example.springsophsoft.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.lifecycle.ViewModelProviders;

import com.example.springsophsoft.Helper.Databasehelper;
import com.example.springsophsoft.R;
import com.example.springsophsoft.Send_Request;
import com.example.springsophsoft.ui.Search.SearchViewModel;
import com.example.springsophsoft.ui.signUpAndLogIn.LogIn;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SearchTransaction extends AppCompatActivity {

    private SearchViewModel searchViewModel;
    Databasehelper db;
    ArrayList<String> listItem;
    ArrayAdapter adapter;
    private ListView userlist;
    private EditText et;
    String user  = LogIn.getString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        et = (EditText)findViewById(R.id.myFilter);
        db = new Databasehelper(this);
        userlist = (ListView)findViewById(R.id.listView1);
        listItem = new ArrayList<>();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = userlist.getItemAtPosition(position).toString();
                userTransaction(text);

                //   toastMessage(text);
            }
        });


        viewData();
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
        });

    }

    private void userTransaction(String text){
        Intent intent = new Intent(this, personTransaction.class);
        intent.putExtra("Person_SendingTo",text);
        startActivity(intent);


    }

    public void viewData(){
        Cursor cursor=db.viewData();
        if(cursor.getCount()==0){
            toastMessage("The data is empty.");
        }
        else {
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1));
            }
            adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listItem);
            userlist.setAdapter(adapter);
        }
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}