package com.example.organizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.organizer.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "OrganizerDB.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE = "date";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_NOTES =
                "CREATE TABLE " + TABLE_NOTES + " ("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_TITLE + " TEXT, "
                        + COLUMN_DESCRIPTION + " TEXT, "
                        + COLUMN_TIME + " TEXT, "
                        + COLUMN_DATE + " TEXT"
                        + ")";

        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public boolean addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, note.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, note.getDescription());
        contentValues.put(COLUMN_TIME, note.getTime());
        contentValues.put(COLUMN_DATE, note.getDate());

        long result = db.insert(TABLE_NOTES, null, contentValues);
        db.close();
        return result != -1;
    }


    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String time = cursor.getString(3);
                String date = cursor.getString(4);

                Note note = new Note(
                        id,
                        title,
                        description,
                        time,
                        date
                );

                noteList.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return noteList;
    }

    public Note findNoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = new String[] {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_DESCRIPTION,
                COLUMN_TIME,
                COLUMN_DATE
        };
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = new String[] {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NOTES, columns, selection, selectionArgs, null, null, null, null);

        Note necessaryNote = null;
        if (cursor != null && cursor.moveToFirst()) {
            int noteId = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String time = cursor.getString(3);
            String data = cursor.getString(4);

            necessaryNote = new Note(
                    noteId,
                    title,
                    description,
                    time,
                    data
            );
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return necessaryNote;
    }
    public boolean updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_TIME, note.getTime());
        values.put(COLUMN_DATE, note.getDate());

        int result = db.update(TABLE_NOTES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
        db.close();

        return result > 0;
    }

    public boolean deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "SELECT * FROM " + TABLE_NOTES + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{String.valueOf(note.getId())});

        if (cursor.moveToFirst()) {
            int result = db.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
            cursor.close();
            db.close();
            return (result != 0);
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public List<Note> getSortedByTimeAndDateCreation() { // Сортировка по дате и времени создания
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> notesList = new ArrayList<Note>();

        String selectQuery = "SELECT * FROM " + TABLE_NOTES +
                " ORDER BY strftime('%s', " + COLUMN_DATE + " || ' ' || " + COLUMN_TIME + ") ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setDescription(cursor.getString(2));
                note.setTime(cursor.getString(3));
                note.setDate(cursor.getString(4));
                notesList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return notesList;
    }

}

