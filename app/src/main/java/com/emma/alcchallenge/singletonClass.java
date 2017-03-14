package com.emma.alcchallenge;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by dell on 13/03/2017.
 */

public class singletonClass {
    private static singletonClass mInstance;
    RequestQueue requestQueue;
    Context mcontext;
    Cache cache;
    Network network;

    public singletonClass(Context context){
        mcontext = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        cache = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        network = new BasicNetwork(new HurlStack());

        if (requestQueue == null){
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();

        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void stopRequestQueue(){
        requestQueue.stop();
    }

    public static synchronized singletonClass getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new singletonClass(context);
        }
        return mInstance;
    }
}