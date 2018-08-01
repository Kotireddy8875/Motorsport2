package com.example.kotireddy.motorsport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Stagepojo> jsonstagepojo;
    FirebaseAuth auth;
    ProgressDialog dialog;
    public static final String POS="pos";
    int position;
    GridLayoutManager gridLayoutManager;
    public static final String MAINURL="https://www.thesportsdb.com/api/v1/json/1/all_leagues.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycler);
        gridLayoutManager=new GridLayoutManager(this,2);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null)
            new MainAsyncTask().execute();
        else
            Toast.makeText(this, R.string.msg7, Toast.LENGTH_SHORT).show();
        if(savedInstanceState!=null)
            position=savedInstanceState.getInt(POS);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!dialog.isShowing()) {
            position = gridLayoutManager.findFirstVisibleItemPosition();
            outState.putInt(POS, position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout)
        {
            auth.signOut();
// this listener will be called when there is change in firebase user session
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (dialog.isShowing())
            dialog.dismiss();
        super.onDestroy();
    }

    class MainAsyncTask extends AsyncTask<String,Void,String>{
        ArrayList<Stagepojo> arrayListLeague=new ArrayList<>();

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getResources().getString(R.string.Loading));
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpurlConnection urlconnection=new HttpurlConnection();
            URL url=null;
            String response="";
            try {
                url=urlconnection.buildUrl(MAINURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            response=urlconnection.getHttpresponse(url);
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray jsonArray=jsonObject.getJSONArray(getString(R.string.leagues));
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject object=jsonArray.getJSONObject(i);
                    String lId=object.getString(getResources().getString(R.string.idLeague));
                    String lLeague=object.getString(getResources().getString(R.string.strLeague));
                    String lSport=object.getString(getResources().getString(R.string.strSport));
                    String lAlternate=object.getString(getResources().getString(R.string.strLeagueAlternate));
                    if(lSport.equals(getResources().getString(R.string.Motorsport)))
                    arrayListLeague.add(new Stagepojo(lId,lLeague,lSport,lAlternate));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing())
                dialog.dismiss();
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(new StageAdapter(MainActivity.this,arrayListLeague));
            recyclerView.scrollToPosition(position);
        }

    }
}

