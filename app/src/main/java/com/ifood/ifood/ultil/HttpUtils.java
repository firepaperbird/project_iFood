package com.ifood.ifood.ultil;

import android.content.Context;

import com.ifood.ifood.R;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.entity.StringEntity;


public class HttpUtils {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url, context), entity,"application/json", responseHandler);
    }

    public static void post(Context context, String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url, context), entity,"application/json", responseHandler);
    }

    public static void put(Context context, String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.put(context, getAbsoluteUrl(url, context), entity,"application/json", responseHandler);
    }

    public static void delete(Context context, String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.delete(context, getAbsoluteUrl(url, context), entity, "application/json", responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    public static void putByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(url, params, responseHandler);
    }

    public static void deleteByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.delete(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl, Context context) {
        return Helper.getConfigValue(context, "api_url") + relativeUrl;
    }
}
