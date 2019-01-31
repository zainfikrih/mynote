package men.ngopi.zain.lonote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import men.ngopi.zain.lonote.databaseHelper.LabelHelper;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.CustomViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Label> model;
    private View v;
//    private String judul;
//    private String keterangan;
    private LabelHelper labelHelper;

    public LabelAdapter(Context context, ArrayList<Label> model) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.model = model;
        labelHelper = new LabelHelper(context);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = inflater.inflate(R.layout.row_label, viewGroup, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {
        customViewHolder.judul.setText(model.get(i).getLabel());
        customViewHolder.keterangan.setText(model.get(i).getKeterangan());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Label " + model.get(i).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, NoteListActivity.class);
                intent.putExtra("judul", model.get(i).getLabel());
                intent.putExtra("id", model.get(i).getId());
                context.startActivity(intent);
            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final int index = i;

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(50, 50, 50, 0);
                final EditText judul = new EditText(context);
                judul.setText(model.get(i).getLabel());
                judul.setLayoutParams(lp);
                judul.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
                layout.addView(judul);

                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp2.setMargins(50, 0, 50, 50);
                final EditText keterangan = new EditText(context);
                keterangan.setText(model.get(i).getKeterangan());
                keterangan.setLayoutParams(lp2);
                layout.addView(keterangan);

                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Edit Label");

                // add the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        labelHelper.open();
                        model.get(index).setLabel(judul.getText().toString());
                        model.get(index).setKeterangan(keterangan.getText().toString());
                        Label label2 = new Label(model.get(index).getId(), judul.getText().toString(), keterangan.getText().toString());
                        labelHelper.update(label2);
                        labelHelper.close();
                        notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        labelHelper.open();
                        labelHelper.delete(model.get(index));
                        labelHelper.close();
                        MainActivity.getMainActivity().getAllData();
//                        model.remove(index);
//                        notifyItemRemoved(index);
//                        notifyItemRangeChanged(index, model.size());
//                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setView(layout);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView judul;
        TextView keterangan;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.judulLabel);
            keterangan = itemView.findViewById(R.id.keteranganLabel);

            itemView.setTag(itemView);
        }
    }
}
