package models.menu;

public class Makanan extends MenuItem {

    private int tingkatPedas; 
    private String kategori; 

    public Makanan(String nama, double harga, int tingkatPedas, String kategori) {
        super(nama, harga);
        this.tingkatPedas = tingkatPedas;
        this.kategori = kategori;
    }

    public int getTingkatPedas() {
        return tingkatPedas;
    }

    public String getKategori() {
        return kategori;
    }


    @Override
    public String getInfo() {
        return String.format("%s (Rp %.1f) - Kategori: %s, Level Pedas: %d",
                getNama(),        
                getHarga(),       
                this.kategori,
                this.tingkatPedas
        );
    }
}
