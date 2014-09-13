package com.mcnallydevelopers.android.apps.encartelera;

/**
 * Created by paulomcnally on 9/13/14.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by paulomcnally on 8/3/14.
 */
public class Helper {

    protected Context context;

    public Helper(Context context) {

        this.context = context;
    }

    public boolean isConnected() {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = false;
        if (activeNetwork != null)
            isConnected = true;
        return isConnected;

    }
}

