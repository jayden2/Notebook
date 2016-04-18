package com.jayden.notebook;

/**
 * Created by Jayden on 19-Apr-16.
 */
public class Note {
    private String title, message;
    private long noteId,dateCreateMilli;
    private Category category;
    public enum Category{ PERSONAL, TECHNICAL, QUOTE, FINANCE }

    public Note(String title, String message, Category category) {
        this.title = title;
        this.message = message;
        this.category = category;
        this.noteId = 0;
        this.dateCreateMilli = 0;
    }

    public Note(String title, String message, Category category, long noteId, long dateCreateMilli) {
        this.title = title;
        this.message = message;
        this.category = category;
        this.noteId = noteId;
        this.dateCreateMilli = dateCreateMilli;
    }

    //getters
    public String getTitle() { return title; }

    public String getMessage() { return message; }

    public Category getCategory() { return category; }

    public long getDate() { return dateCreateMilli; }

    public long getNoteId() { return noteId; }

    //toString
    public String toString() {
        return "ID: " + noteId + " Title: " + title + " Message: " + message
                + " IconID: " + category.name() + " Date: ";
    }

    //return category picture
    public int getAssociatedDrawable() {
        return categoryToDrawable(category);
    }

    public static int categoryToDrawable(Category noteCategory) {
        switch(noteCategory) {
            case PERSONAL:
                return R.drawable.p;
            case TECHNICAL:
                return R.drawable.t;
            case FINANCE:
                return R.drawable.f;
            case QUOTE:
                return R.drawable.q;
        }
        return R.drawable.p;
    }
}
