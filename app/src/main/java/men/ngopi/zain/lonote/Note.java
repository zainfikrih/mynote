package men.ngopi.zain.lonote;

public class Note {
    private int id;
    private String judulNote;
    private String isiNote;
    private int labelId;

    public Note(){

    }

    public Note(int id, String judulNote, String isiNote, int labelId) {
        this.id = id;
        this.judulNote = judulNote;
        this.isiNote = isiNote;
        this.labelId = labelId;
    }

    public Note(String judulNote, String isiNote, int labelId) {
        this.judulNote = judulNote;
        this.isiNote = isiNote;
        this.labelId = labelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudulNote() {
        return judulNote;
    }

    public void setJudulNote(String judulNote) {
        this.judulNote = judulNote;
    }

    public String getIsiNote() {
        return isiNote;
    }

    public void setIsiNote(String isiNote) {
        this.isiNote = isiNote;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }
}
