package com.emma.alcchallenge;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Profile extends AppCompatActivity {
    private ImageView imv;
    private TextView tv;
    private TextView githubusername;
    Intent intent;
    String githublink;
    Button sharebtn;
    private ShareActionProvider shareAction;
    private Intent shareIntent;
    private String user;

    public Profile() {
        super();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_files,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share){


        Intent shareintent = new Intent();
            shareintent.setType("text/plain");
            shareintent.setAction(Intent.ACTION_SEND);
            shareintent.putExtra(Intent.EXTRA_TEXT,"Check out this awesome developer@ " + user + " " +  githublink);
            startActivity(Intent.createChooser(shareintent,"Send Link Via"));

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       // image loader library
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        imv = (ImageView) findViewById(R.id.profile_pix);
        tv = (TextView) findViewById(R.id.userName);


        //gets the strings from extras
        intent = getIntent();
        user = intent.getExtras().getString("extra");
        githublink = intent.getExtras().getString("Ex");

        //sets the image
        ImageLoader.getInstance().displayImage(githublink + ".png", imv);
        tv.setText(user);//sets the text
    }

}