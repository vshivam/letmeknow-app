package com.android.letmeknow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class categories_tab extends Activity {
	Button tech, artNd, all, sNs, mathNsc, business;
	Intent intent;
	Bundle bundle;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_layout);
        
     tech = (Button)findViewById(R.id.tech);
     artNd = (Button)findViewById(R.id.artNd);
     all = (Button)findViewById(R.id.all);
     sNs = (Button)findViewById(R.id.sNs);
     mathNsc = (Button)findViewById(R.id.mathNsc);
     business = (Button)findViewById(R.id.business);
     
     tech.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             // TODO Auto-generated method stub
        	 move_to_list("tech");
         }
     });
     
     artNd.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             // TODO Auto-generated method stub
        	 move_to_list("tech");
         }
     });
     sNs.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             // TODO Auto-generated method stub
        	 move_to_list("sNs");
         }
     });
     all.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             // TODO Auto-generated method stub
        	 move_to_list("all");
         }
     });
     business.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             // TODO Auto-generated method stub
        	 move_to_list("business");
         }
     });
     artNd.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
             // TODO Auto-generated method stub
        	 move_to_list("artNd");
         }
     });
     
    }
    
    void move_to_list(String cat)
    {
    	intent = new Intent(this, list.class);
    	intent.putExtra("cat",cat );
    	startActivity(intent);
    }
    
    }

