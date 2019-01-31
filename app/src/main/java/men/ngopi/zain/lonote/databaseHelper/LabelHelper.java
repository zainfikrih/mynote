package men.ngopi.zain.lonote.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import men.ngopi.zain.lonote.model.Label;

import static android.provider.BaseColumns._ID;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.LabelColumns.JUDUL;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.LabelColumns.KETERANGAN;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.TABLE_LABEL;

public class LabelHelper {

    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public LabelHelper (Context context){
        this.context = context;
    }

    public LabelHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Label> getAllData(){
        Cursor cursor = database.query(TABLE_LABEL, null,null,null,null,null,null, null);
        cursor.moveToFirst();
        ArrayList<Label> arrayList = new ArrayList<>();
        Label label;
        if(cursor.getCount() > 0 ){
            do{
                label = new Label();
                label.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                label.setLabel(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                label.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(KETERANGAN)));
                arrayList.add(label);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        return arrayList;
    }

    public ArrayList<Label> search(String judul){
        Cursor cursor = database.query(TABLE_LABEL, null, JUDUL + " LIKE ?",new String[] { "%" + judul + "%" },null,null,null, null);
        cursor.moveToFirst();
        ArrayList<Label> arrayList = new ArrayList<>();
        Label label;
        if(cursor.getCount() > 0 ){
            do{
                label = new Label();
                label.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                label.setLabel(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                label.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(KETERANGAN)));
                arrayList.add(label);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } else {
            Toast.makeText(context, judul, Toast.LENGTH_SHORT).show();
        }
        return arrayList;
    }

    public long insert(Label label){
        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL, label.getLabel());
        contentValues.put(KETERANGAN, label.getKeterangan());
        return database.insert(TABLE_LABEL, null, contentValues);
    }

    public int update(Label label){
        ContentValues contentValues = new ContentValues();
        contentValues.put(JUDUL, label.getLabel());
        contentValues.put(KETERANGAN, label.getKeterangan());
        return database.update(TABLE_LABEL, contentValues, _ID + "= '"+ label.getId() +"'", null);
    }

    public int delete(Label label){
        return database.delete(TABLE_LABEL, _ID + "= '" + label.getId() + "'", null);
    }
}
