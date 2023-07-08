package Model;

public class Institution {

    private int inst_id;
    private String instName;
    private String instAddress;

    public Institution() {}
    public Institution(String instName, String instAddress) {
        this.instName = instName;
        this.instAddress = instAddress;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstAddress() {
        return instAddress;
    }

    public void setInstAddress(String instAddress) {
        this.instAddress = instAddress;
    }

    public int getInst_id() {
        return inst_id;
    }

    public void setInst_id(int inst_id) {
        this.inst_id = inst_id;
    }
}
