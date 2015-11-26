package com.routesounds.routesounds;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DepinaG on 24/11/2015.
 */
public class BandAdapter extends ArrayAdapter<Band> {
    public BandAdapter(Context context, ArrayList<Band> bands) {
        super(context, 0, bands);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Band band = getItem(position);
        ViewHolder holder;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feed, parent, false);
            holder.wVwebsite = (ImageView) convertView.findViewById(R.id.wVwebsite);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvContact = (TextView) convertView.findViewById(R.id.tvContact);
           // holder.tvFrom = (TextView) convertView.findViewById(R.id.tvFrom);
            holder.listView = (ListView) convertView.findViewById(R.id.listView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Lookup view for data population
        // Populate the data into the template view using the data object
        String youtubeId = getVideoId(band.getWebsite());

        Picasso.with(getContext()).load("http://img.youtube.com/vi/"+youtubeId+"/0.jpg").into(holder.wVwebsite);
      // holder.tvFrom.setText(band.getWebsite());
        holder.tvName.setText(band.getName());
        holder.tvContact.setText(band.getContact());
        // Return the completed view to render on screen


        return convertView;
    }

    static class ViewHolder {
        ImageView wVwebsite;
        TextView tvName;
        TextView tvContact;
        TextView tvFrom;
        ListView listView;
    }


    final static String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";

    public static String getVideoId(String videoUrl) {
        if (videoUrl == null || videoUrl.trim().length() <= 0)
            return null;

        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find())
            return matcher.group(1);
        return null;
    }

}

