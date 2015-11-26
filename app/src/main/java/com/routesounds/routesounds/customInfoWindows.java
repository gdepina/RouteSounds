package com.routesounds.routesounds;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by DepinaG on 26/11/2015.
 */
public class customInfoWindows implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater mInflater;

    public customInfoWindows(LayoutInflater inflater) {
        this.mInflater = inflater;

    }


    @Override
    public View getInfoContents(Marker marker) {
        View popup = mInflater.inflate(R.layout.info_windows_layout, null);
        TextView tv = (TextView) popup.findViewById(R.id.name);
        tv.setText(marker.getTitle());
        tv = (TextView) popup.findViewById(R.id.snippet);
        tv.setText(marker.getSnippet());


        return popup;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }
}