package men.ngopi.zain.lonote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import java.util.ArrayList;

import men.ngopi.zain.lonote.adapter.NoteAdapter;
import men.ngopi.zain.lonote.databaseHelper.NoteHelper;
import men.ngopi.zain.lonote.model.Note;

public class NoteListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int id;
    private String judul;
    private RecyclerView recyclerView;
    private ArrayList<Note> noteArrayList = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private FloatingActionButton floatingActionButton;
    static private NoteListActivity noteListActivity;
    private NoteHelper noteHelper;
    private SearchView searchView;

    static public NoteListActivity getNoteListActivity(){
        return noteListActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        noteListActivity = this;
        noteHelper = new NoteHelper(this);

        id = getIntent().getIntExtra("id", -1);
        judul = getIntent().getStringExtra("judul");

        recyclerView = findViewById(R.id.recyclerView2);
        toolbar = findViewById(R.id.toolbarListNote);
        floatingActionButton = findViewById(R.id.floatingButton2);
        searchView = findViewById(R.id.searchBar2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(judul);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return false;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(NoteListActivity.this));

//        noteArrayList.add(new Note("Judul", "Isi", 1));
//        noteAdapter = new NoteAdapter(NoteListActivity.this, noteArrayList);
//        recyclerView.setAdapter(noteAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (searchView.hasFocus()) {
                Log.i("ini", "klik");
                Rect outRect = new Rect();
                searchView.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    searchView.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStart() {
        getData(String.valueOf(id));
        super.onStart();
    }

    public void getData(String id){
        noteHelper.open();
        noteArrayList = noteHelper.getData(id);
        noteHelper.close();
        noteAdapter = new NoteAdapter(this, noteArrayList);
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();
    }

    private void search(String judul){
        noteHelper.open();
        ArrayList<Note> searchList = noteHelper.search(judul, String.valueOf(id));
        noteHelper.close();
        noteAdapter = new NoteAdapter(this, searchList);
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();
    }
}
