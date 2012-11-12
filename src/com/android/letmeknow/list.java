package com.android.letmeknow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
 
public class list extends Activity implements
        OnItemClickListener{
	String cat;
	ArrayList<String> titles;
	ArrayList<String> descriptions;
	ArrayList<String> start_dates;
	ArrayList<String> end_dates;
	ArrayList<String> deadlines;
	ArrayList<String> ids;
	int count = 0;
	
//    public static String[] titles = new String[] { "Strawberry",
//            "Banana", "Orange", "Mixed" };
// 
//    public static String[] descriptions = new String[] {
//            "It is an aggregate accessory fruit",
//            "It is the largest herbaceous flowering plant", "Citrus Fruit",
//            "Mixed Fruits" };
// 
//    public static String[] deadlines = { "It is an aggregate accessory fruit",
//        "It is the largest herbaceous flowering plant", "Citrus Fruit",
//        "Mixed Fruits" };
//    
//    public static String[] start_dates = {"It is an aggregate accessory fruit",
//        "It is the largest herbaceous flowering plant", "Citrus Fruit",
//        "Mixed Fruits" };
//    
//    public static String[] end_dates = { "It is an aggregate accessory fruit",
//        "It is the largest herbaceous flowering plant", "Citrus Fruit",
//        "Mixed Fruits" };
//    
//    public static String[] ids;
    
    ListView listView;
    List<row_item> rowItems;
    CustomArrayAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        
        Bundle bundle = this.getIntent().getExtras();
        cat = bundle.getString("cat");
 
        titles = new ArrayList<String>();
        descriptions = new ArrayList<String>();
        start_dates = new ArrayList<String>();
        end_dates = new ArrayList<String>();
        ids = new ArrayList<String>();
        
        DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute("http://10.0.2.2/letmeknow/get_results.php?cat="+cat+"&count="+Integer.toString(count));
        rowItems = new ArrayList<row_item>();
        for (int i = 0; i < titles.size(); i++) {
            row_item item = new row_item(titles.get(i), start_dates.get(i), end_dates.get(i), descriptions.get(i), deadlines.get(i));
            rowItems.add(item);
        }
 
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomArrayAdapter(this,R.layout.row_layout, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // not used
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                // do something
            	int lastInScreen = firstVisibleItem + visibleItemCount;
            	if(lastInScreen == totalItemCount)
            	{
            		DownloadWebPageTask task_1 = new DownloadWebPageTask();
            		task_1.execute("http://10.0.2.2/letmeknow/get_results.php?cat="+cat+"&count="+Integer.toString(count));
            	}
               	Log.e("scroll", "scroll");
                }
        });
    }
      
    
 
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
            "Item " + (position + 1) + ": " + rowItems.get(position),
            Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
    

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
          String response = "";
          for (String url : urls) {
        	  Log.e("url", url);
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            try {
              HttpResponse execute = client.execute(httpGet);
              InputStream content = execute.getEntity().getContent();

              BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
              String s = "";
              while ((s = buffer.readLine()) != null) {
                response += s;
              }

            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          return response;
        }

        @Override
        protected void onPostExecute(String result) 
        {
        	adapter.notifyDataSetChanged();
        }
      }

//      public void readWebpage(View view) {
//        DownloadWebPageTask task = new DownloadWebPageTask();
//        task.execute(new String[] { "http://www.vogella.com" });
//
//      }
    } 





