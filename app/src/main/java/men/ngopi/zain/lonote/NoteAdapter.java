package men.ngopi.zain.lonote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import men.ngopi.zain.lonote.databaseHelper.LabelHelper;
import men.ngopi.zain.lonote.databaseHelper.NoteHelper;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.CustomViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Note> model;
    private View v;
    private NoteHelper noteHelper;

    public NoteAdapter(Context context, ArrayList<Note> model) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.model = model;
        noteHelper = new NoteHelper(context);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        v = inflater.inflate(R.layout.row_note, viewGroup, false);
        return new NoteAdapter.CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {
            customViewHolder.judulNote.setText(model.get(i).getJudulNote());
            customViewHolder.isiNote.setText(model.get(i).getIsiNote());

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NoteActivity.class);
                    intent.putExtra("idNote", model.get(i).getId());
                    intent.putExtra("judulNote", model.get(i).getJudulNote());
                    intent.putExtra("isiNote", model.get(i).getIsiNote());
                    intent.putExtra("idLabel", model.get(i).getLabelId());
                    context.startActivity(intent);
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    final int index = i;

                    // setup the alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("Delete Note");
                    builder.setMessage("Are you sure?");

                    // add the buttons
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            noteHelper.open();
                            noteHelper.delete(model.get(index));
                            noteHelper.close();
                            NoteListActivity.getNoteListActivity().getData(String.valueOf(model.get(index).getLabelId()));
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

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

        TextView judulNote;
        TextView isiNote;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            judulNote = itemView.findViewById(R.id.judulNote);
            isiNote = itemView.findViewById(R.id.isiNote);

            itemView.setTag(itemView);
        }
    }
}
