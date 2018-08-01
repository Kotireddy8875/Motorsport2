package com.example.kotireddy.motorsport;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Description extends AppCompatActivity {
    TextView team, names, updates, administrator, nationality, web, founded;
    ArrayList<ParticipantsDescriptionPojo> participantsDescriptionPojoList;
    ParticipantsDescriptionPojo participantsDescriptionPojo;
    ImageView logo;
    int flag;
    FloatingActionButton fab;
    SQLiteDatabase sqLiteDatabase;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.favor)
        {
            if (flag == 0) {
                Toast.makeText(this, R.string.Addtofav, Toast.LENGTH_SHORT).show();
                ContentValues contentValues = new ContentValues();
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_DESCRIPTION, participantsDescriptionPojo.getDescription());
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_POSTER_PATH, participantsDescriptionPojo.getLogo());
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_TITLE, participantsDescriptionPojo.getTeamName());
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_WEBSITE, participantsDescriptionPojo.getWebsite());
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_YEAR, participantsDescriptionPojo.getFormedYear());
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_ALSO_KNOWN_AS, participantsDescriptionPojo.getAlternateName() + " " + participantsDescriptionPojo.getTeamShortName());
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_COUNTRY, participantsDescriptionPojo.getCountry());
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_ID, participantsDescriptionPojo.getTeamId());
                contentValues.put(Contract.FavouriteContractEntry.COLUMN_MANAGER, participantsDescriptionPojo.getManager());
                Uri myuri = getContentResolver().insert(Contract.CONTENT_URI, contentValues);
                if (myuri != null) {
                    Toast.makeText(getApplicationContext(), myuri.toString(), Toast.LENGTH_SHORT).show();
                }
                flag = 1;
            } else {
                flag = 0;
                String stringid = participantsDescriptionPojo.getTeamId();
                Uri myuri = Contract.CONTENT_URI;
                int res = getContentResolver().delete(Contract.CONTENT_URI, stringid, null);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.fav,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        participantsDescriptionPojoList = getIntent().getExtras().getParcelableArrayList(getResources().getString(R.string.Data));
        int pos = getIntent().getExtras().getInt(getResources().getString(R.string.Position));
        participantsDescriptionPojo = participantsDescriptionPojoList.get(pos);
        setTitle(participantsDescriptionPojo.getTeamName());
        nationality = (TextView) findViewById(R.id.nationality);
        Uri uri = Contract.CONTENT_URI;
        Uri myUri = uri.buildUpon().appendPath(participantsDescriptionPojo.getTeamId()).build();
        Cursor cursor = getContentResolver().query(myUri, null, null, null, null);
        if (cursor.getCount() > 0) {
            flag = 1;
        } else {
            flag = 0;
        }
        cursor.close();

        if (participantsDescriptionPojo.getCountry().isEmpty())
            nationality.append(getResources().getString(R.string.NOdata));
        else
            nationality.append(participantsDescriptionPojo.getCountry());
        administrator = (TextView) findViewById(R.id.administrator);
        if (participantsDescriptionPojo.getManager().isEmpty())
            administrator.append( getResources().getString(R.string.NOdata));
        else
            administrator.append(participantsDescriptionPojo.getManager());
        web = (TextView) findViewById(R.id.web);
        if (!participantsDescriptionPojo.getWebsite().isEmpty())
            web.append(participantsDescriptionPojo.getWebsite());
        else
            web.append(getResources().getString(R.string.NOdata));
        updates = (TextView) findViewById(R.id.updates);
        if (!participantsDescriptionPojo.getDescription().isEmpty())
            updates.setText(participantsDescriptionPojo.getDescription());
        else
            updates.append(getResources().getString(R.string.NOdata));
        founded = (TextView) findViewById(R.id.founded);
        if (participantsDescriptionPojo.getFormedYear().isEmpty())
            founded.append(getResources().getString(R.string.NOdata));
        else
            founded.append(participantsDescriptionPojo.getFormedYear());
        AdView mAdView;
        mAdView = findViewById(R.id.banner);
        MobileAds.initialize(this, "ca-app-pub-6336523906622902~6817819328");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("C3528C0925D3AB8424433F9070BD2A5F")
                .build();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.msg3));
        progressDialog.show();
        mAdView.loadAd(adRequest);
        progressDialog.dismiss();
        final DBhelper dbHelper = new DBhelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        logo = (ImageView) findViewById(R.id.logo);
        Picasso.with(getApplicationContext()).load(participantsDescriptionPojo.getLogo()).into(logo);

    }
}
