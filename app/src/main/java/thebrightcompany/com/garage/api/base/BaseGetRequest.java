package thebrightcompany.com.garage.api.base;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import thebrightcompany.com.garage.api.BaseRequest;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.utils.Utils;


/**
 * Created by ChienNV on 10/24/16.
 */

public class BaseGetRequest<T> extends BaseRequest<T> {


    public BaseGetRequest(String url, Type type, OnResponseListener<T> listener) {
        super(Request.Method.GET, url, listener);
        mType = type;
        mListener = listener;
    }

    public BaseGetRequest(String url, Type type, OnResponseListener<T> listener, Map<String, String> mParams) {
        super(Request.Method.GET, Utils.getUrl(url,mParams), listener);
        mType = type;
        mListener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.i("Response get: ", json);
            return (Response<T>) Response.success
                    (
                            mGson.fromJson(json, mType),
                            HttpHeaderParser.parseCacheHeaders(response)
                    );
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }




}
