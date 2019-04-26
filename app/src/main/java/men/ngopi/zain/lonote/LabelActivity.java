package men.ngopi.zain.lonote;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import men.ngopi.zain.lonote.databaseHelper.LabelHelper;
import men.ngopi.zain.lonote.model.Label;


public class LabelActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText judul, keterangan;
    private MaterialButton btnLabel, btnLabel2;
    private LabelHelper labelHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        toolbar = findViewById(R.id.toolbarLabel);
        judul = findViewById(R.id.judulLabel);
        keterangan = findViewById(R.id.keteranganLabel);
        btnLabel = findViewById(R.id.btnLabel);
//        btnLabel2 = findViewById(R.id.btnLabel2);

        labelHelper = new LabelHelper(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Label");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!judul.getText().toString().equals("") && !keterangan.getText().toString().equals("")){
                    long startTime = System.nanoTime();
                    insertData(); //Tanpa transaction
                    long endTime = System.nanoTime();
                    Log.i("Time Insert Label = ", String.valueOf((endTime-startTime)/1000000));
                    Toast.makeText(LabelActivity.this, "Time : " + String.valueOf((endTime-startTime)/1000000) + " ms", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LabelActivity.this, "Isi Dulu Boy..", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        btnLabel2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!judul.getText().toString().equals("") && !keterangan.getText().toString().equals("")){
//                    long startTime = System.nanoTime();
//                    insertDataFast(); //Dengan transaction
//                    long endTime = System.nanoTime();
//                    Log.i("Time Insert Label = ", String.valueOf((endTime-startTime)/1000000));
//                    Toast.makeText(LabelActivity.this, "Time : " + String.valueOf((endTime-startTime)/1000000) + " ms", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(LabelActivity.this, "Isi Dulu Boy..", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void insertData(){
        Label label = new Label(judul.getText().toString(), keterangan.getText().toString());
        labelHelper.open();
        labelHelper.insert(label);
        labelHelper.close();
        MainActivity.getMainActivity().getAllData();
        finish();
    }

    private void insertDataFast(){
        Label label = new Label(judul.getText().toString(), keterangan.getText().toString());
        labelHelper.open();
        labelHelper.insertFast(label);
        labelHelper.close();
        MainActivity.getMainActivity().getAllData();
        finish();
    }
}
