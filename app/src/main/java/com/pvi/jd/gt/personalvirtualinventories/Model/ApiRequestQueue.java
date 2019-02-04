package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Wrapper class for RequestQueue, which is used
 * to make requests to the Recipe API.
 */
public class ApiRequestQueue {
    private static ApiRequestQueue instance;
    private RequestQueue requestQueue;
    private Context context;

    private ApiRequestQueue(Context cxt) {
        this.context = cxt;
    }

    /**
     * Returns singleton of ApiRequestQueue
     * @param cxt application context
     * @return the singleton instance
     */
    public static synchronized ApiRequestQueue getInstance(Context cxt) {
        if(instance == null) {
            instance = new ApiRequestQueue(cxt);
        }
            return instance;
    }

    /**
     * Returns the singleton request queue object
     * @return RequestQueue object
     */
    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
            requestQueue.start();
        }
        return requestQueue;
    }

    /**
     * Adds a request to the request queue.
     * @param req request to add.
     * @param <T> the type of the request to add.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
