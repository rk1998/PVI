package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Wrapper class for RequestQueue, which is used
 * to make requests to the Recipe API.
 */
public class ApiRequestQueue {
    private static ApiRequestQueue instance;
    private RequestQueue requestQueue;
    private Context context;
    private ImageLoader imageLoader;

    private ApiRequestQueue(Context cxt) {

        this.context = cxt;
        this.requestQueue = getRequestQueue();
        this.imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(40);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
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

    /**
     * Gets the image loader object that can load images from a url
     * @return image loader object
     */
    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }
}
