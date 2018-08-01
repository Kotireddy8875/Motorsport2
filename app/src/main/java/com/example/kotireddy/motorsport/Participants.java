package com.example.kotireddy.motorsport;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Participants extends AppCompatActivity implements LoaderManager.LoaderCallbacks {
    public static final String KEY = "key";
    public static final String POSITION = "position";
    public static final String PARTICIPANTURL="https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=";
    ProgressDialog dialog;
    Stagepojo stagepojo;
    String url;
    String title;
    ParticipantsAdapter participantsAdapter;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    String value;
    LinearLayout linearLayout;
    ArrayList<ParticipantsDescriptionPojo> participantsPojoList;
    private int scrollPosition = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.favdisplay,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.favor)
            getSupportLoaderManager().restartLoader(10,null,this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);
        dialog = new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerparticipants);
        gridLayoutManager = new GridLayoutManager(this, 2);
        Toast.makeText(this, R.string.Swipe, Toast.LENGTH_SHORT).show();
        Bundle bundle = getIntent().getExtras();
        if (savedInstanceState != null)
            scrollPosition = savedInstanceState.getInt(POSITION);
        if (bundle != null) {
                title = bundle.getString(getResources().getString(R.string.myString));
                stagepojo = bundle.getParcelable(getResources().getString(R.string.Data));
                 url = title.replace(" ", "%20");
                 setTitle(title);
                value = "Partcipants";
                url = PARTICIPANTURL+url;


                if (checkOnline()) {
                    SoccerAsyncTask soccerAsyncTask = new SoccerAsyncTask(url);
                    soccerAsyncTask.execute();
                }else {
                    Toast.makeText(this,R.string.msg7 , Toast.LENGTH_SHORT).show();
                }
        }
        else {
            getSupportLoaderManager().restartLoader(10, null, this);
            value = "Fav";
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY, value);
        if ((!getSupportLoaderManager().hasRunningLoaders() || (!dialog.isShowing())) && (checkOnline())) {
            scrollPosition = gridLayoutManager.findFirstVisibleItemPosition();
            outState.putInt(POSITION, scrollPosition);
        }
    }

    public boolean checkOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
            return true;
        else
            return false;
    }

    private void retry() {
        Intent intent = new Intent(Participants.this, Participants.class);
        finish();
        startActivity(intent);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                Cursor cursor;
                participantsPojoList = new ArrayList<ParticipantsDescriptionPojo>();
                cursor = getContentResolver().query(Contract.CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    ParticipantsDescriptionPojo participantspojo = new ParticipantsDescriptionPojo(cursor.getString(1), cursor.getString(8), cursor.getString(2), " ", cursor.getString(6), cursor.getString(7), cursor.getString(3), cursor.getString(0), cursor.getString(5), cursor.getString(4));
                    participantsPojoList.add(participantspojo);
                }
                cursor.close();
                return null;
            }
        };
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (value == "Fav") {
            getSupportLoaderManager().restartLoader(10, null, this);
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        if (participantsPojoList.size() > 0) {
            participantsAdapter = new ParticipantsAdapter(Participants.this, participantsPojoList);
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager gm = new GridLayoutManager(Participants.this, 2);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
            } else {
                GridLayoutManager gm = new GridLayoutManager(Participants.this, 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
            }

            recyclerView.setAdapter(participantsAdapter);
            recyclerView.scrollToPosition(scrollPosition);

        } else {
            Toast.makeText(this, R.string.NoFavorites, Toast.LENGTH_SHORT).show();
            if(checkOnline())
                new SoccerAsyncTask(url).execute();
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }

    public class SoccerAsyncTask extends AsyncTask<String, Void, String> {
        String teamString;

        public SoccerAsyncTask(String s) {
            this.teamString = s;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getResources().getString(R.string.Loading));
            dialog.show();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            participantsAdapter = new ParticipantsAdapter(Participants.this, participantsPojoList);
            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager gm = new GridLayoutManager(Participants.this, 2);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
            } else {
                GridLayoutManager gm = new GridLayoutManager(Participants.this, 1);
                gm.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(gm);
            }
            recyclerView.setAdapter(participantsAdapter);
            recyclerView.scrollToPosition(scrollPosition);
        }
        @Override
        protected String doInBackground(String... strings) {
            URL myParticpanturl=null;
            String poster_response = "";
            HttpurlConnection connect = new HttpurlConnection();
            try {
                myParticpanturl = connect.buildUrl(teamString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String teamResponse = null;
            int i = 0;
            teamResponse = connect.getHttpresponse(myParticpanturl);
            participantsPojoList = new ArrayList<ParticipantsDescriptionPojo>();
            try {
                JSONObject teamObject = new JSONObject(teamResponse);
                JSONArray teamArray = teamObject.getJSONArray(getResources().getString(R.string.teams));
                Log.i("teamArray",teamArray.toString());
                while (i < teamArray.length()) {
                    JSONObject team = teamArray.getJSONObject(i);
                    String name = team.getString(getResources().getString(R.string.teamName));
                    String id = team.getString(getResources().getString(R.string.teamId));
                    String shortName = team.getString(getResources().getString(R.string.shortName));
                    String altName = team.getString(getResources().getString(R.string.altName));
                    String formedYear = team.getString(getResources().getString(R.string.year));
                    String logo = team.getString(getResources().getString(R.string.logo));
                    String manager = team.getString(getResources().getString(R.string.manager));
                    String website = team.getString(getResources().getString(R.string.website));
                    String country = team.getString(getResources().getString(R.string.country));
                    String description=team.getString(getResources().getString(R.string.Description));
                    ParticipantsDescriptionPojo teamsPojo = new ParticipantsDescriptionPojo(name, id, shortName, altName, description,formedYear, manager, logo, website, country);
                    participantsPojoList.add(teamsPojo);
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return poster_response;
        }
    }
    public Activity getActivity() {
        Context context = Participants.this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}