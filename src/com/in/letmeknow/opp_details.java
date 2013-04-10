package com.in.letmeknow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
 
public class opp_details extends Activity {
	String response;
	String id;
	TextView title, where, org, start_date, end_date, deadline, eligibility, type, pub_date, website, email;
	//TextView details;
	WebView details;
	ImageButton apply_now;
	String emails;
	LinearLayout ll_loc, ll_sDate, ll_eDate, ll_org, ll_deadline, ll_elig, ll_type, ll_pub, main;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opp_details);
        title = (TextView)findViewById(R.id.title);
        where = (TextView)findViewById(R.id.location);
        org = (TextView)findViewById(R.id.org);
        start_date = (TextView)findViewById(R.id.start_date);
        end_date = (TextView)findViewById(R.id.end_date);
        deadline = (TextView)findViewById(R.id.deadline);
        eligibility = (TextView)findViewById(R.id.eligibility);
        type = (TextView)findViewById(R.id.type);
        apply_now = (ImageButton) findViewById(R.id.apply_now);
        pub_date = (TextView)findViewById(R.id.pub_date);
        details = (WebView)(findViewById(R.id.details));
        WebSettings webSettings = details.getSettings();
        webSettings.setDefaultFontSize(14);
        
        ll_loc= (LinearLayout)(findViewById(R.id.ll_loc));
        ll_org= (LinearLayout)(findViewById(R.id.ll_org));
        ll_sDate= (LinearLayout)(findViewById(R.id.ll_sDate));
        ll_eDate= (LinearLayout)(findViewById(R.id.ll_eDate));
        ll_elig= (LinearLayout)(findViewById(R.id.ll_elig));
        ll_pub= (LinearLayout)(findViewById(R.id.ll_pub));
        ll_type= (LinearLayout)(findViewById(R.id.ll_type));
        ll_deadline= (LinearLayout)(findViewById(R.id.ll_deadline));
        main = (LinearLayout)findViewById(R.id.main);
        main.setVisibility(View.GONE);
        
        
        ConnectivityManager connMgr = (ConnectivityManager) 
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        
        
        
        if(isWifiConn || isMobileConn)
        {        
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        
        
        apply_now.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(0);
                
            }
        });
       
        DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute("http://app.letmeknow.in/get_opp_details.php?id="+id);
        

           }
        else
        	Toast.makeText(getApplicationContext(), "Please Connect to the internet !", Toast.LENGTH_LONG).show();
    }    
    
private class SendEmailTask extends AsyncTask<String, Void, String> {
    	
    	private ProgressDialog Dialog = new ProgressDialog(opp_details.this);
    	
    	@Override
        protected void onPreExecute()
        {
        	
        		Dialog.setMessage("Sending E - Mail..");
        		Dialog.show();
            
        }
        @Override
        protected String doInBackground(String... urls) {
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("captcha_value", "1"));
			nameValuePairs.add(new BasicNameValuePair("captcha", "1"));
			nameValuePairs.add(new BasicNameValuePair("emails", emails));
			
	    
			for (String url : urls) {
		    try
			{
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
			}
			catch(Exception e)
			{
				Toast.makeText(getBaseContext(),"Error in http connection "+e.toString(), Toast.LENGTH_LONG).show();
			}
			finally
			{
			
			}
          
          }
			return response;
        }

        @Override
        protected void onPostExecute(String result) 
        {
                	Dialog.dismiss();            	
                }
            }
           
        	
    

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
    	
    	private ProgressDialog Dialog = new ProgressDialog(opp_details.this);
    	
    	@Override
        protected void onPreExecute()
        {
        	
        		Dialog.setMessage("Loading..");
        		Dialog.show();
            
        }
        @Override
        protected String doInBackground(String... urls) {
          response = "[";
          for (String url : urls) {
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
              response += "]";          
              
            } catch (Exception e) {
              e.printStackTrace();
            }
            
            
          }
          return response;
        }

        @Override
        protected void onPostExecute(String result) 
        {
        	try{
            	response = response.replace("null", "\"Data Unavailable\"");
            	JSONArray items_arr = new JSONArray(response);
                int arrlen = items_arr.length();
                for(int i=0; i<arrlen;i++)
                {
                	JSONObject obj = items_arr.getJSONObject(i);
                	if(!obj.getString("location").equalsIgnoreCase("Data Unavailable"))
                		where.setText(obj.getString("location"));
                	else
                		ll_loc.setVisibility(View.GONE);
                	
                	if(!obj.getString("name").equalsIgnoreCase("Data Unavailable"))
                		title.setText(obj.getString("name"));
                	else
                		title.setVisibility(View.GONE);
                		
                	if(!obj.getString("org").equalsIgnoreCase("Data Unavailable"))
                		org.setText(obj.getString("org"));
                	else
                		ll_org.setVisibility(View.GONE);
                	
                	if(!obj.getString("start_date").equalsIgnoreCase("Data Unavailable"))
                		start_date.setText(obj.getString("start_date"));
                	else
                		ll_sDate.setVisibility(View.GONE);
                	
                	if(!obj.getString("end_date").equalsIgnoreCase("Data Unavailable"))
                		end_date.setText(obj.getString("end_date"));
                	else
                		ll_eDate.setVisibility(View.GONE);
                	
                	if(!obj.getString("deadline").equalsIgnoreCase("Data Unavailable"))
                		deadline.setText(obj.getString("deadline"));
                	else
                		ll_deadline.setVisibility(View.GONE);
                	
                	
                	if(!obj.getString("eligibility").equalsIgnoreCase("Data Unavailable"))
                	{
                		HashMap hm = new HashMap();
                    	hm.put("0", "Undergrad Level");
                    	hm.put("1", "Grad Level");
                    	hm.put("2", "Post Grad Level");
                    	hm.put("3", "Non - Students");
                	
                    	String[] parts = obj.getString("eligibility").split(",");
                    	
                    	String elig ="";
                    	
                    	for (String s : parts)
                    	{
                    		elig = hm.get(s)+" / "+elig;
                    	}
                    	elig = elig.substring(0,elig.length()-3);
                		eligibility.setText(elig);
                	}
                	else
                		ll_elig.setVisibility(View.GONE);
                	
                	if(!obj.getString("type").equalsIgnoreCase("Data Unavailable"))
                	{
                		HashMap hm = new HashMap();
                    	hm.put("0", "Internship/Training");
                    	hm.put("1", "Scholarship/Fellowship");
                    	hm.put("2", "Festival");
                    	hm.put("3", "Performing Art events");
                    	hm.put("4", "Conference/Symposium/Summit");
                    	hm.put("5", "Workshop/Camp");
                    	hm.put("6", "Competition/Contest");
                    	hm.put("7", "Graduate Program");
                    	hm.put("8", "Volunteering");
                    	hm.put("9", "Part-time job");
                    	hm.put("10", "Freelancer job");
                    	hm.put("11", "Campus job");
                    	hm.put("12", "Full-time job");
                    	hm.put("13", "Call for Papers"); 	
                    	
                    	
                		type.setText(hm.get(obj.getString("type")).toString());
                	}
                	else
                		ll_type.setVisibility(View.GONE);
                	
                	if(!obj.getString("pub_date").equalsIgnoreCase("Data Unavailable"))
                		pub_date.setText(obj.getString("pub_date").substring(0,11));
                	else
                		ll_pub.setVisibility(View.GONE);

                	String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />";
                	String desc_long = obj.getString("desc_long").replace("../../../", "http://letmeknow.in");
                	details.loadDataWithBaseURL("file:///android_asset/", css+desc_long, "text/html","utf-8",  null);
                	
                	
                	Dialog.dismiss();
                	main.setVisibility(View.VISIBLE);
                	            	
                }
            }
            catch (JSONException e1 ) {
        		// TODO Auto-generated catch block
        		e1.printStackTrace();
        		
        		}
        	catch(Exception e2)
        	{
        	}
        	
        	
        }
      }


     

    @Override
    protected Dialog onCreateDialog(int id) {
 
        switch (id) {
            case 0:
                return createExampleDialog();
            default:
                return null;
        }
    }
 
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
 
        switch (id) {
            case 0:
                // Clear the input box.
                EditText text = (EditText) dialog.findViewById(0);
                text.setText("");
                break;
        }
    }
private Dialog createExampleDialog() {
	 
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Apply Now");
    builder.setMessage("Please enter your Email - Id");

     // Use an EditText view to get user input.
     final EditText input = new EditText(this);
     input.setId(0);
     builder.setView(input);

    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int whichButton) {
            emails = input.getText().toString();
            String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            
            if(emails.matches(pattern))
            {
            String app_url = "http://www.letmeknow.in/index/apply-now/id/"+ id;
            
            SendEmailTask send = new SendEmailTask();
            send.execute(app_url);}
            else 
            	Toast.makeText(getApplicationContext(), "Please Enter a Valid Email - ID", Toast.LENGTH_SHORT).show();
            return;
        }
    });

    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            return;
        }
    });

    return builder.create();
}
}





