package com.emma.alcchallenge;

import android.content.Intent;

/**
 * Created by dell on 06/03/2017.
 */

public class myModel {
        private String url;
        private String userName;
        private String profilePix;


        public myModel(String userName, String url, String profilePix){
            this.url = url;
            this.userName = userName;
            this.profilePix = profilePix;
//
        }

        public String getUrl() {
            return url;
        }

        public String getUserName() {
            return userName;
        }
///
        public String getProfilePix() {
            return profilePix;
        }

}


