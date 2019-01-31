package men.ngopi.zain.lonote;

public class Label {
    private int id;
    private String label;
    private String keterangan;

    public Label(){

    }

    public Label(int id, String label, String keterangan) {
        this.id = id;
        this.label = label;
        this.keterangan = keterangan;
    }

    public Label(String label, String keterangan) {
        this.label = label;
        this.keterangan = keterangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
