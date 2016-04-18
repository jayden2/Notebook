package com.jayden.notebook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        notes = new ArrayList<Note>();
        notes.add(new Note("Note Title 1", "note body 1", Note.Category.PERSONAL));
        notes.add(new Note("Note Title 2", "note body 2", Note.Category.FINANCE));
        notes.add(new Note("Note Title 3", "note body 3", Note.Category.TECHNICAL));
        notes.add(new Note("Note Title 4", "note body 4", Note.Category.QUOTE));
        notes.add(new Note("Note Title 5", "note body 5", Note.Category.QUOTE));
        notes.add(new Note("Note Title 6", "note body 6", Note.Category.PERSONAL));
        notes.add(new Note("Note Title 7", "note body 7", Note.Category.FINANCE));
        notes.add(new Note("Note Title 8", "note body 8", Note.Category.PERSONAL));
        notes.add(new Note("Note Title 9", "note body 9", Note.Category.TECHNICAL));
        notes.add(new Note("Note Title 10", "note body 10", Note.Category.PERSONAL));
        notes.add(new Note("Note Title 11", "note body 11", Note.Category.PERSONAL));

        noteAdapter = new NoteAdapter(getActivity(), notes);

        setListAdapter(noteAdapter);

        //set divider between fragments color and height
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.darker_gray));
        getListView().setDividerHeight(1);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
