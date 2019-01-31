package men.ngopi.zain.lonote;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import men.ngopi.zain.lonote.databaseHelper.LabelHelper;


public class LabelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText judul, keterangan;
    private MaterialButton btnLabel;
    private LabelHelper labelHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        toolbar = findViewById(R.id.toolbarLabel);
        judul = findViewById(R.id.judulLabel);
        keterangan = findViewById(R.id.keteranganLabel);
        btnLabel = findViewById(R.id.btnLabel);

        labelHelper = new LabelHelper(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Label");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!judul.getText().toString().equals("") && !keterangan.getText().toString().equals("")){
                    insertData();
                } else {
                    Toast.makeText(LabelActivity.this, "Isi Dulu Boy..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void insertData(){
        labelHelper.open();
        Label label = new Label(judul.getText().toString(), keterangan.getText().toString());
        labelHelper.insert(label);
        labelHelper.close();
        MainActivity.getMainActivity().getAllData();
        finish();
    }
}
