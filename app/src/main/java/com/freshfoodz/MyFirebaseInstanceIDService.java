package com.freshfoodz;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by Administrator on 16/07/2016.
 */
//public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService {
//
//    private static final String TAG = "MyFirebaseIIDService";
//
//    @Override
//    public void onTokenRefresh() {
//        try {
//            CommonClass.writelog("IdService", "onTokenRefresh() 28");
//            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//            if (refreshedToken != null && !refreshedToken.equals("")) {
//                CommonClass comm=new CommonClass(this);
//                comm.setSession(ConstValue.COMMON_FCM_ID, refreshedToken);
//                CommonClass.sendRegistrationToServer(getApplicationContext(),refreshedToken,true);
//                CommonClass.writelog("IdService", "onTokenRefresh() 33 Token:" + refreshedToken);
//            }
//            else
//            {
//                CommonClass.writelog("IdService", "onTokenRefresh() 37 Token:Null" );
//            }
//            Log.d(TAG, "Refreshed token: " + refreshedToken);
//            // TODO: Implement this method to send any registration to your app's servers.
//        }
//        catch (Exception ex) {
//            CommonClass.writelog("IdService", "onTokenRefresh() 40 Ex:"+ex.getMessage());
//        }
//    }
//
//}
public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        try {

            if (s != null && !s.equals("")) {

            } else {
            }
            Log.d(TAG, "Refreshed token: " + s);
            // TODO: Implement this method to send any registration to your app's servers.
        } catch (Exception ex) {
        }
    }


}
