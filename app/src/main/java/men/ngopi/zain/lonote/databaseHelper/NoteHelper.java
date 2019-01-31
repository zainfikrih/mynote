package men.ngopi.zain.lonote.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import men.ngopi.zain.lonote.model.Note;

import static android.provider.BaseColumns._ID;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.NoteColumns.ISI_NOTE;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.NoteColumns.JUDUL_NOTE;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.NoteColumns.LABEL_ID;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.TABLE_NOTE;

public class NoteHelper {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Context context;

    public NoteHelper(Context context) {
        this.context = context;
    }

    public NoteHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Note> getData(String id){
//        Cursor cursor = database.query(TABLE_NOTE, null, LABEL_ID + " = ?", new String[] { id }, null, null, _ID + " DESC", null);
        Cursor cursor = database.query(TABLE_NOTE, null, LABEL_ID + " = ?", new String[] { id }, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Note> arrayList = new ArrayList<>();
        Note note;
        if(cursor.getCount() > 0){
            do{
                note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setJudulNote(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL_NOTE)));
                note.setIsiNote(cursor.getString(cursor.getColumnIndexOrThrow(ISI_NOTE)));
                note.setLabelId(cursor.getInt(cursor.getColumnIndexOrThrow(LABEL_ID)));
                arrayList.add(note);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return arrayList;
    }

    public ArrayList<Note> search(String judul, String idLabel){
        Cursor cursor = database.query(TABLE_NOTE, null, JUDUL_NOTE + " LIKE ? and " + LABEL_ID + " = ?",new String[] { "%" + judul + "%", idLabel},null,null,null, null);
        cursor.moveToFirst();
        ArrayList<Note> arrayList = new ArrayList<>();
        Note note;
        if(cursor.getCount() > 0 ){
            do{
                note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setJudulNote(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL_NOTE)));
                note.setIsiNote(cursor.getString(cursor.getColumnIndexOrThrow(ISI_NOTE)));
                note.setLabelId(cursor.getInt(cursor.getColumnIndexOrThrow(LABEL_ID)));
                arrayList.add(note);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } else {

        }
        return arrayList;
    }

    public long insert(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL_NOTE, note.getJudulNote());
        contentValues.put(ISI_NOTE, note.getIsiNote());
        contentValues.put(LABEL_ID, note.getLabelId());
        return database.insert(TABLE_NOTE, null, contentValues);
    }

    public int update(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL_NOTE, note.getJudulNote());
        contentValues.put(ISI_NOTE, note.getIsiNote());
        contentValues.put(LABEL_ID, note.getLabelId());
        return database.update(TABLE_NOTE, contentValues, _ID + "= '"+ note.getId() +"'",null);
    }

    public int delete(Note note){
        return database.delete(TABLE_NOTE, _ID + "= '" + note.getId() + "'", null);
    }
}
