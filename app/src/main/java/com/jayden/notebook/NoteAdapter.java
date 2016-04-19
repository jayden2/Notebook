package com.jayden.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Jayden on 19-Apr-16.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

    public static class ViewHolder {
        TextView noteTitle;
        TextView noteText;
        ImageView noteIcon;
    }

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        Note note = getItem(position);

        //Create a new viewHolder
        ViewHolder viewHolder;

        //Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if (convertView == null) {

            //if there's no view being used create one, and create
            //a view holder along with it to save the view reference to it
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);

            //Grab references of views so we can populate them with specific note row data
            //Set view to view holder
            viewHolder.noteTitle = (TextView) convertView.findViewById(R.id.listItemNoteTitle);
            viewHolder.noteText = (TextView) convertView.findViewById(R.id.listItemNoteBody);
            viewHolder.noteIcon = (ImageView) convertView.findViewById(R.id.listItemNoteImg);

            //User set tag to store the view holder which is holding reference to the widgets
            convertView.setTag(viewHolder);
        } else {

            //Grab widgets from view holder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Populate date into the template view using the data object
        viewHolder.noteTitle.setText(note.getTitle());
        viewHolder.noteText.setText(note.getMessage());
        viewHolder.noteIcon.setImageResource(note.getAssociatedDrawable());

        return convertView;
    }
}
