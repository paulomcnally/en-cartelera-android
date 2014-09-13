package com.mcnallydevelopers.android.apps.encartelera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.nhaarman.listviewanimations.ArrayAdapter;

/**
 * Created by paulomcnally on 9/13/14.
 */
public class MoviesAdapter  extends ArrayAdapter<MoviesList> {

    private final Context mContext;

    MoviesList data[] = null;

    public MoviesAdapter(final Context context, MoviesList[] data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.minicard, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = (TextView) view.findViewById(R.id.textview_minicard_name);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageview_minicard_front);
            viewHolder.textViewSynopsis = (TextView) view.findViewById(R.id.textview_minicard_synopsis);
            viewHolder.textViewGenres = (TextView) view.findViewById(R.id.textview_minicard_genres);
            viewHolder.textViewClasification = (TextView) view.findViewById(R.id.textview_minicard_clasification);
            viewHolder.textViewDuration = (TextView) view.findViewById(R.id.textview_minicard_duration);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final MoviesList list = data[position];

        viewHolder.textViewName.setText(list.getName());

        AQuery aq = new AQuery(view);

        aq.id(R.id.imageview_minicard_front).progress(R.id.progress_minicard).image(list.getFront(), true, true);

        viewHolder.textViewSynopsis.setText(list.getSynopsis());
        viewHolder.textViewGenres.setText(list.getGenres());
        viewHolder.textViewClasification.setText(list.getClasification());
        viewHolder.textViewDuration.setText(list.getDuration());

        return view;
    }

    private static final class ViewHolder {
        TextView textViewName;
        ImageView imageView;
        TextView textViewSynopsis;
        TextView textViewGenres;
        TextView textViewClasification;
        TextView textViewDuration;
    }
}


