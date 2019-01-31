package men.ngopi.zain.lonote;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import men.ngopi.zain.lonote.databaseHelper.NoteHelper;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText judulNote;
    private TextInputEditText isiNote;
    private NoteHelper noteHelper;
    private int id;
    private int idLabel;
    private int idNote;
    private String judulNoteStr, isiNoteStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        toolbar = findViewById(R.id.toolbarNote);
        isiNote = findViewById(R.id.isiEditNote);
        judulNote = findViewById(R.id.judulEditNote);

        id = getIntent().getIntExtra("id", -1);
        idNote = getIntent().getIntExtra("idNote", -1);
        idLabel = getIntent().getIntExtra("idLabel", -1);
        judulNoteStr = getIntent().getStringExtra("judulNote");
        isiNoteStr = getIntent().getStringExtra("isiNote");

        noteHelper = new NoteHelper(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        judulNote.setText(judulNoteStr);
        isiNote.setText(isiNoteStr);
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        if(!judulNote.getText().toString().equals("") && !isiNote.getText().toString().equals("")){
            if(idNote == -1){
                insertData();
            } else {
                updateData();
            }
        } else if(judulNote.getText().toString().equals("") && !isiNote.getText().toString().equals("")){
            String s = isiNote.getText().toString();
            judulNote.setText(s.substring(0, Math.min(s.length(), 30)));
            if(idNote == -1){
                insertData();
            }else {
                updateData();
            }
        } else if(!judulNote.getText().toString().equals("")) {
            if(idNote == -1){
                insertData();
            } else {
                updateData();
            }
        }
        super.onDestroy();
    }

    private void insertData(){
        noteHelper.open();
        Note note = new Note(judulNote.getText().toString(), isiNote.getText().toString(),id);
        noteHelper.insert(note);
        noteHelper.close();
        NoteListActivity.getNoteListActivity().getData(String.valueOf(id));
    }

    private void updateData(){
        noteHelper.open();
        Note note = new Note(idNote,judulNote.getText().toString(), isiNote.getText().toString(),idLabel);
        noteHelper.update(note);
        noteHelper.close();
        NoteListActivity.getNoteListActivity().getData(String.valueOf(idLabel));
    }
}
