package men.ngopi.zain.lonote.databaseHelper;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_LABEL = "tabel_label";
    static String TABLE_NOTE = "tabel_note";

    static final class LabelColumns implements BaseColumns{
        static String JUDUL = "judul";
        static String KETERANGAN = "keterangan";
    }

    static final class NoteColumns implements BaseColumns{
        static String JUDUL_NOTE = "judulNote";
        static String ISI_NOTE = "isiNote";
        static String LABEL_ID = "labelId";
    }
}
