package com.emma.alcchallenge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    URL url;
    HttpURLConnection connection = null;//initialise the connection
    BufferedReader reader = null;
    ListView lv;
    Intent intent;
    ArrayList<myModel> myModelList;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout myswipe;
    String mUrl = "https://api.github.com/search/users?q=language:Java+location:Lagos";


    public MainActivity() {
        super();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivitymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            geturl();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview);//cast the list view from the the XML

        geturl();

        //creates the progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting Network connection Please wait....");
        progressDialog.show();


        //respond to swipe gestures
        myswipe = (SwipeRefreshLayout) findViewById(R.id.myswipe);
        myswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                geturl();
            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //image loader library
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

        .cacheInMemory(true)
                .cacheOnDisk(true)
        .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //lunches you to the profile activity
                intent = new Intent(MainActivity.this, Profile.class);
                //gets String from tags
                String ms = view.getTag(R.id.user).toString();

                //sends strings to profile activity
                intent.putExtra("Ex",(CharSequence)view.getTag());
                intent.putExtra("extra",ms);

                startActivity(intent);

            }

        });

    }

    public void geturl(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        ArrayList<myModel> myModelArrayList = new ArrayList<>();

                        try {

                            //converts the string received into JSON object
                            JSONObject jsonObject = new JSONObject(response);

                            //gets the json array named items
                            JSONArray jsonArray = jsonObject.getJSONArray("items");

                            //loops through all the array
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject final_obj = jsonArray.getJSONObject(i);
                                String username = final_obj.getString("login");
                                String getUrl = final_obj.getString("url");
                                String getprofilepix = final_obj.getString("html_url");

                                myModel model = new myModel(username,getUrl,getprofilepix);
                                myModelArrayList.add(model);//
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        CustomAdapter adapter = new CustomAdapter(getApplicationContext(),R.layout.view_layout,myModelArrayList);
                        lv.setAdapter(adapter);

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();

            }
        });

        singletonClass slc = new singletonClass(getApplicationContext());
        slc.getRequestQueue();
        slc.addToRequestQueue(stringRequest);

    }

//    private void geturl() {
//        JSONTask jt = new JSONTask();
//        jt.execute("https://api.github.com/search/users?q=language:Java+location:Lagos");
//    }
//
//
//
//    //This inner class perform all background operations and publish result in the UI Thread
//    public class JSONTask extends AsyncTask<String, String, ArrayList<myModel>>{
//
//        int length;
//        String getprofilepix;
//
//        @Override
//        protected ArrayList<myModel> doInBackground(String... strings) {
//            try {
//                url = new URL(strings[0]);
//                connection = (HttpURLConnection)url.openConnection();
//
//                InputStream stream = connection.getInputStream();
//                connection.connect();
//
//                reader = new BufferedReader(new InputStreamReader(stream));
//
//                StringBuffer buffer = new StringBuffer();
//
//                String line = "";
//               while ((line = reader.readLine()) != null){
//                    buffer.append(line);
//                }
//
//                String JSON = buffer.toString();//gets the stream data and convert to string
//
//                JSONObject jobj = new JSONObject(JSON);//gets the buffered string and convert to JSON Object
//
//                JSONArray Jary =jobj.getJSONArray("items");//gets the JSON array from JSON object
//
//                length = Jary.length();//gets the length of the array
//
//                myModelList = new ArrayList<>();//creates an arraylist of myModel
//
//                for (int i =0; i < length; i++){//loops through the whole array of data
//                    JSONObject final_obj = Jary.getJSONObject(i);//gets the specific array
//
//                    String username = final_obj.getString("login");
//                    String getUrl = final_obj.getString("url");
//                    String getprofilepix = final_obj.getString("html_url");
//
//                    myModel model = new myModel(username,getUrl,getprofilepix);
//                    myModelList.add(model);
//                }
//
//                return myModelList;
////this catches all exceptions
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } finally {
//                if (connection != null){
//                    connection.disconnect();//disconnects the url
//                }
//                if (reader != null){
//                    try {
//                        reader.close();//close the bufferReader class
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<myModel> list) {
//            progressDialog.dismiss();//removes progressDialog after the view has been published on the UI Theard
//            CustomAdapter adapter = new CustomAdapter(getApplicationContext(),R.layout.view_layout,list);
//            lv.setAdapter(adapter);
//
//            super.onPostExecute(list);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog.show();//show before the the background Thread finish executes
//
//            super.onPreExecute();
//            myswipe.setRefreshing(false);
//        }
//    }

}