package com.jayden.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

    private ImageButton noteCatButton;
    private EditText title, message;
    private Note.Category savedButtonCategory;
    private AlertDialog categoryDialogObject, confirmDialogObject;

    private static final String MODIFIED_CATEGORY = "Modified Category";

    private boolean newNote = false;
    private long noteId = 0;

    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Get the bundle to see if noteEditFragment is creating a new note
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            newNote = bundle.getBoolean(NoteDetailActivity.NEW_NOTE_EXTRA, false);
        }

        if (savedInstanceState != null) {
            savedButtonCategory = (Note.Category) savedInstanceState.get(MODIFIED_CATEGORY);
        }

        //Inflate edit fragment layout
        View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        //get widget references from layout
        title = (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);
        message = (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton = (ImageButton) fragmentLayout.findViewById(R.id.editNoteButton);
        Button savedButton = (Button) fragmentLayout.findViewById(R.id.saveNote);

        //populate widgets with note data
        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA, ""));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA, ""));
        noteId = intent.getExtras().getLong(MainActivity.NOTE_ID_EXTRA, 0);

        //If category was set and the orientation changes, set the button to saved category
        if (savedButtonCategory != null) {

            noteCatButton.setImageResource(Note.categoryToDrawable(savedButtonCategory));

        } else if (!newNote) {
            //Regular edit setup
            Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
            savedButtonCategory = noteCat;
            noteCatButton.setImageResource(Note.categoryToDrawable(noteCat));

        }

        buildCategoryDialog();
        buildConfirmDialog();

        noteCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialogObject.show();
            }
        });

        savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                confirmDialogObject.show();
            }
        });

        return fragmentLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(MODIFIED_CATEGORY, savedButtonCategory);
    }

    private void buildCategoryDialog() {
        final String[] categories = new String[]{ "Personal", "Technical", "Quote", "Finance" };

        AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
        categoryBuilder.setTitle("Choose Note Type");
        categoryBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //dismiss dialog window
                categoryDialogObject.cancel();

                switch (i) {
                    case 0:
                        savedButtonCategory = Note.Category.PERSONAL;
                        noteCatButton.setImageResource(R.drawable.p);
                        break;
                    case 1:
                        savedButtonCategory = Note.Category.TECHNICAL;
                        noteCatButton.setImageResource(R.drawable.t);
                        break;
                    case 2:
                        savedButtonCategory = Note.Category.QUOTE;
                        noteCatButton.setImageResource(R.drawable.q);
                        break;
                    case 3:
                        savedButtonCategory = Note.Category.FINANCE;
                        noteCatButton.setImageResource(R.drawable.f);
                        break;
                }
            }
        });

        categoryDialogObject = categoryBuilder.create();
    }

    private void buildConfirmDialog() {

        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
        confirmBuilder.setTitle("Are you sure?");
        confirmBuilder.setMessage("Do you want to save the note?");

        confirmBuilder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //Show current note data
                Log.d("Save Note", "Note Title: " + title.getText() + "Note Message: " + message.getText() + "Note Category: " + savedButtonCategory);

                NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();

                if (newNote) {
                    //create new note in database
                    dbAdapter.createNote(title.getText() + "", message.getText() + "",
                            (savedButtonCategory == null)? Note.Category.PERSONAL : savedButtonCategory);
                } else {
                    //otherwise edit note
                    dbAdapter.updateNote(noteId, title.getText() + "", message.getText() + "", savedButtonCategory);
                }

                dbAdapter.close();
                //Go back to note list fragment
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
            public  void onClick(DialogInterface dialog, int i) {
               //Don't do much here
           }
        });

        confirmDialogObject = confirmBuilder.create();
    }

}
