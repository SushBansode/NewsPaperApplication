package tatastrive.application.newspaperapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<HashMap<String,String>> arrayListNews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initComponents();
    }

    private void initComponents() {
        recyclerView=findViewById( R.id.recyclerView );
        mLayoutManager=new LinearLayoutManager( MainActivity.this );
        recyclerView.setLayoutManager( mLayoutManager );
        callAPI();
    }

    private void callAPI() {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.myjson.com/bins/ix56l";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseAPIResponse(response);


                        Toast.makeText( MainActivity.this, "Response"+response, Toast.LENGTH_SHORT ).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseAPIResponse(String response) {
        try {
            JSONObject objResponse = new JSONObject(response);
            JSONArray arrayHeadlines = objResponse.getJSONArray( "headlines" );
            arrayListNews = new ArrayList<>();
           for (int i=0; i< arrayHeadlines.length();i++)
           {
             JSONObject objItem= arrayHeadlines.getJSONObject( i );
             String title= objItem.getString( "title" );
             String imgUrl=objItem.getString( "imgUrl" );
            String description= objItem.getString( "descrption" );

               HashMap<String, String> map = new HashMap<>();
               map.put("title", title);
               map.put("url", imgUrl);
               map.put("detail", description);
               arrayListNews.add(map);

               mAdapter= new HomeListAdapter(MainActivity.this,arrayListNews);
               recyclerView.setAdapter(mAdapter);


           }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}