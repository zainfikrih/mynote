package men.ngopi.zain.lonote.databaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.LabelColumns.JUDUL;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.LabelColumns.KETERANGAN;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.NoteColumns.ISI_NOTE;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.NoteColumns.JUDUL_NOTE;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.NoteColumns.LABEL_ID;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.TABLE_LABEL;
import static men.ngopi.zain.lonote.databaseHelper.DatabaseContract.TABLE_NOTE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "dblonote";

    public static String CREATE_TABLE_LABEL = "create table " + TABLE_LABEL + " (" + _ID +
            " integer primary key autoincrement, "+
            JUDUL + " text not null, "+
            KETERANGAN + " text not null);";

    public static String CREATE_TABLE_NOTE = "create table " + TABLE_NOTE + " (" + _ID +
            " integer primary key autoincrement, "+
            JUDUL_NOTE + " text not null, "+
            ISI_NOTE + " text not null, " +
            LABEL_ID + " integer, foreign key(" + LABEL_ID + ") references " + TABLE_LABEL + "(" + _ID + "));";

    public  DatabaseHelper (Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_LABEL);

        onUpgrade(sqLiteDatabase, 0, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LABEL);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
//        sqLiteDatabase.execSQL(CREATE_TABLE_NOTE);
//        onCreate(sqLiteDatabase);
        switch (i1){
            case 1:
                //version 1
            case 2:
                sqLiteDatabase.execSQL(CREATE_TABLE_NOTE);
            case 3:
                //version 3
        }
    }
}
