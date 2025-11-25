package models.pesanan;

public class Meja {
    private int nomor;
    private String status; 

    public Meja(int nomor, String status) {
        this.nomor = nomor;
        this.status = status;
    }

    public int getNomor() {
        return nomor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Meja no " + nomor + " (" + status + ")";
    }
}