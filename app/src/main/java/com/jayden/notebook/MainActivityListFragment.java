package com.jayden.notebook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        notes = dbAdapter.getAllNotes();
        dbAdapter.close();

        noteAdapter = new NoteAdapter(getActivity(), notes);

        setListAdapter(noteAdapter);

        //set divider between fragments color and height
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.darker_gray));
        getListView().setDividerHeight(1);

        registerForContextMenu(getListView());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        launchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW, position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //position of pressed note
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int rowPosition = info.position;
        Note note = (Note) getListAdapter().getItem(rowPosition);

        //Returns id of menu item we selected
        switch (item.getItemId()) {
            case R.id.edit:
                launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT, rowPosition);
                Log.d("Edit Menu Click", "Edit pressed");
                return true;
            case R.id.delete:
                NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(note.getNoteId());

                notes.clear();
                notes.addAll(dbAdapter.getAllNotes());
                noteAdapter.notifyDataSetChanged();

                dbAdapter.close();
        }

        return super.onContextItemSelected(item);
    }

    private void launchNoteDetailActivity(MainActivity.FragmentToLaunch ftl, int position) {

        //Grab the note information associated with the clicked note item
        Note note = (Note) getListAdapter().getItem(position);

        //Create a new intent that launches our noteDetailActivity
        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);

        //Pass along the information of the note we clicked on to our noteDetailActivity
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA, note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA, note.getCategory());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getNoteId());

        switch(ftl) {
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW);
                break;
            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.EDIT);
                break;
        }
        startActivity(intent);
    }
}
