package models.menu;

public class Minuman extends MenuItem {


    private String ukuran; 
    private String suhu;   

    public Minuman(String nama, double harga, String ukuran, String suhu) {
        super(nama, harga);
        this.ukuran = ukuran;
        this.suhu = suhu;
    }

    public String getUkuran() {
        return ukuran;
    }

    public String getSuhu() {
        return suhu;
    }

    @Override
    public String getInfo() {
        return String.format("%s (Rp %.1f) - Ukuran: %s, Suhu: %s",
                getNama(),       
                getHarga(),       
                this.ukuran,
                this.suhu
        );
    }
}
