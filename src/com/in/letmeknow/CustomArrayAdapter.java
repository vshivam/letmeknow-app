package com.in.letmeknow;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class CustomArrayAdapter extends ArrayAdapter<row_item> {
 
    Context context;
    
 
    public CustomArrayAdapter(Context context, int resourceId,List<row_item> items) 
    {
        super(context, resourceId, items);
        this.context = context;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        TextView title;
        TextView start_date;
        TextView end_date;
        TextView deadline;
        TextView description;
        LinearLayout ll_sDate, ll_eDate, ll_deadline;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        row_item rowItem = getItem(position);
 
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_layout, null);
            holder = new ViewHolder();
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.start_date = (TextView) convertView.findViewById(R.id.start_date);
            holder.end_date = (TextView) convertView.findViewById(R.id.end_date);
            holder.deadline = (TextView) convertView.findViewById(R.id.deadline);
            holder.ll_sDate = (LinearLayout) convertView.findViewById(R.id.ll_sDate);
            holder.ll_eDate = (LinearLayout) convertView.findViewById(R.id.ll_eDate);
            holder.ll_deadline = (LinearLayout) convertView.findViewById(R.id.ll_deadline);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        
        
        
        
        
        if(rowItem.get_description().equals("Data Unavailable"))
    		holder.description.setVisibility(View.GONE);
        else 
        	holder.description.setText(rowItem.get_description());
        
        if(rowItem.get_title().equals("Data Unavailable"))
    		holder.title.setVisibility(View.GONE);
        else 
        	holder.title.setText(rowItem.get_title());
        
        if(rowItem.get_start_date().equals("Data Unavailable"))
    		holder.ll_sDate.setVisibility(View.GONE);
        else 
        	holder.start_date.setText(rowItem.get_start_date());
        
        if(rowItem.get_end_date().equals("Data Unavailable"))
    		holder.ll_eDate.setVisibility(View.GONE);
        else 
        	holder.end_date.setText(rowItem.get_end_date());
        
        if(rowItem.get_deadline().equals("Data Unavailable"))
    		holder.ll_deadline.setVisibility(View.GONE);
        else 
        	holder.deadline.setText(rowItem.get_deadline());

 
        return convertView;
    }
}


