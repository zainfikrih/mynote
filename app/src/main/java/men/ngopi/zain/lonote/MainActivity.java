package men.ngopi.zain.lonote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

import men.ngopi.zain.lonote.adapter.LabelAdapter;
import men.ngopi.zain.lonote.databaseHelper.LabelHelper;
import men.ngopi.zain.lonote.model.Label;

public class MainActivity extends AppCompatActivity {

    private MaterialSearchBar materialSearchBar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private LabelAdapter labelAdapter;
    private ArrayList<Label> labelList = new ArrayList<Label>();
    private LabelHelper labelHelper;
    static private MainActivity mainActivity;

    static public MainActivity getMainActivity(){
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        materialSearchBar = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent labelIntent = new Intent(MainActivity.this, LabelActivity.class);
                startActivity(labelIntent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        labelHelper = new LabelHelper(this);
        getAllData();

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search(materialSearchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onStart() {
//        Log.i("INI", "Start");
        super.onStart();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (materialSearchBar.hasFocus()) {
                Log.i("ini", "klik");
                Rect outRect = new Rect();
                materialSearchBar.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    materialSearchBar.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void getAllData(){
        labelHelper.open();
        labelList = labelHelper.getAllData();
        labelHelper.close();
        labelAdapter = new LabelAdapter(this, labelList);
        recyclerView.setAdapter(labelAdapter);
        labelAdapter.notifyDataSetChanged();
    }

    private void search(String judul){
        labelHelper.open();
        ArrayList<Label> searchList = labelHelper.search(judul);
        labelHelper.close();
        labelAdapter = new LabelAdapter(this, searchList);
        recyclerView.setAdapter(labelAdapter);
        labelAdapter.notifyDataSetChanged();
    }
}
