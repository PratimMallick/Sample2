package redmart.com.redmartsample.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pratim on 25/11/17.
 */
public class NetworkUtils {

    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Fetch Active internet connection from network info
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        // return the network connection is active or not.
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
