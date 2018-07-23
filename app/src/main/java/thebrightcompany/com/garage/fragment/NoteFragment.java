package thebrightcompany.com.garage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import thebrightcompany.com.garage.App;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.api.OnResponseListener;
import thebrightcompany.com.garage.api.base.BaseGetRequest;
import thebrightcompany.com.garage.fragment.note.NoteListAdapter;
import thebrightcompany.com.garage.fragment.note.model.NoteModel;
import thebrightcompany.com.garage.utils.Constant;
import thebrightcompany.com.garage.utils.Utils;
import thebrightcompany.com.garage.view.MainActivity;

public class NoteFragment extends Fragment {
    public final int limit = 20;

    public ListView lstView;
    public NoteListAdapter adapter;
    public List<NoteModel> notes;

    public int previousLastPosition = 0;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getContext()).setTittle(getResources().getString(R.string.str_note_title));
        lstView = (ListView) getView().findViewById(R.id.lst_note);
        notes = new ArrayList<>();
        adapter = new NoteListAdapter(getContext(), R.layout.item_notice_customer );

        adapter.notes = this.notes;
        lstView.setAdapter(adapter);

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoteDetailFragment noteDetailFragment = new NoteDetailFragment();
                noteDetailFragment.noteId = adapter.notes.get(i).order_id;
                ((MainActivity)getContext()).addFragment(noteDetailFragment);
            }
        });
        lstView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                isScrollCompleted(i);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
            }
        });




        loadNote(0);
    }


    public void isScrollCompleted(int currentScrollState) {
        if (currentScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            loadNote(adapter.notes.size());

        }
    }

    public void loadNote(int start){
        if (!Utils.isNetworkAvailable(getContext())){
//            onNetWorkError(getString(R.string.str_msg_network_fail));
            return;
        }

        OnResponseListener<JsonObject> listener = new OnResponseListener<JsonObject>(){
            @Override
            public void onErrorResponse(VolleyError error) {

                super.onErrorResponse(error);
            }

            @Override
            public void onResponse(JsonObject response) {

                super.onResponse(response);
                try {
                    JSONObject object = new JSONObject(response.get("data").toString());

                    List<NoteModel>noteModels = LoganSquare.parseList(object.optString("notifications"),NoteModel.class);
                    notes.addAll(noteModels);
                    adapter.notes = notes;
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Map<String, String> mParams = new HashMap<>();
        mParams.put("start", String.valueOf(start));
        mParams.put("limit", String.valueOf(limit));
        mParams.put("token",Utils.APP_TOKEN);

        BaseGetRequest request = new BaseGetRequest( Constant.URL_GET_LIST_NOTIFICATION, new TypeToken<JsonObject>(){}.getType(),listener, mParams);
        App.addRequest(request, "notificatons");

    }


//    public void GET(String url, Map<String, String> params, Response.Listener<String> response_listener, Response.ErrorListener error_listener, String API_KEY, String stringRequestTag) {
//        final Map<String, String> mParams = params;
//        final String mAPI_KEY = API_KEY;
//        final String mUrl = url;
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.GET,
//                mUrl,
//                response_listener,
//                error_listener
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                return mParams;
//            }
//
//            @Override
//            public String getUrl() {
//                StringBuilder stringBuilder = new StringBuilder(mUrl);
//                int i = 1;
//                for (Map.Entry<String,String> entry: mParams.entrySet()) {
//                    String key;
//                    String value;
//                    try {
//                        key = URLEncoder.encode(entry.getKey(), "UTF-8");
//                        value = URLEncoder.encode(entry.getValue(), "UTF-8");
//                        if(i == 1) {
//                            stringBuilder.append("?" + key + "=" + value);
//                        } else {
//                            stringBuilder.append("&" + key + "=" + value);
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    i++;
//
//                }
//                String url = stringBuilder.toString();
//
//                return url;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                if (!(mAPI_KEY.equals(""))) {
//                    headers.put("X-API-KEY", mAPI_KEY);
//                }
//                return headers;
//            }
//        };
//
//        if (stringRequestTag != null) {
//            stringRequest.setTag(stringRequestTag);
//        }
//
//        mRequestQueue.add(stringRequest);
//    }

}

