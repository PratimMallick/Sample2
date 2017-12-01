package redmart.com.redmartsample.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.logging.Logger;

import redmart.com.redmartsample.app.AppApplication;
import redmart.com.redmartsample.app.AppConstants;

/**
 * Created by pratim on 25/11/17.
 */
public class VolleyRequestHelper {

    private static final String TAG = VolleyRequestHelper.class
            .getSimpleName();

    private OnRequestCompletedListener mRequestCompletedListener;

    public VolleyRequestHelper(Context context, OnRequestCompletedListener callback) {
        mRequestCompletedListener = callback;
        //this.context = context;
    }

    /**
     * A callback interface indicates the status about the completion of HTTP
     * request.
     */
    public interface OnRequestCompletedListener {
        /**
         * Called when the Volley request has been completed.
         *
         * @param requestName  the String refers the request name
         * @param status       the status of the request either success or failure
         * @param response     the String response returns from the Webservice. It may be
         *                     null if request failed.
         * @param errorMessage the String refers the error message when request failed to
         *                     get the response
         */
        void onRequestCompleted(String requestName, boolean status,
                                String response, String errorMessage);

    }

    /**
     * Request String response from the Web API.
     *
     * @param requestName   the String refers the request name
     * @param webserviceUrl the String refers the web service URL.
     * @param webMethod     the integer indicates the web method.
     * @param getCache      the boolean indicates whether cache can enable/disable
     */
    public void getJsonObject(final String requestName, String webserviceUrl,
                              int webMethod, JSONObject jsonObject, boolean getCache){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(webMethod,
                webserviceUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        mRequestCompletedListener.onRequestCompleted(
                                requestName, true, response.toString(), null);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorResponse = "";
                try {
                    VolleyError responseError = new VolleyError(
                            new String(error.networkResponse.data));
                    try {
                        final JSONObject responseJson = new JSONObject(responseError.getMessage());
                        // Show Alert Information
                        errorResponse = responseJson.getString(AppConstants.API_MESSAGE);
                    } catch (Exception e) {
                        errorResponse = "Unknown";
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                mRequestCompletedListener.onRequestCompleted(
                        requestName, false, null,
                        errorResponse);
            }
        }){

            @Override
            public String getBodyContentType() {
                return AppConstants.APPLICATION_JSON;
            }
        };

        AppApplication.getInstance().addToRequestQueue(jsonObjectRequest, requestName);
    }


}
