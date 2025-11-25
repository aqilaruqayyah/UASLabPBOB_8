package  models.akun;

import java.util.Random;
import java.util.HashSet;
import java.util.Set;


public abstract class Akun {
        private int id;
        private String nama;
        private String password;
        private static Random random = new  Random(); // ranndom id
        private static Set<Integer> usedId = new HashSet<>(); //  hindari duplikat id

        public Akun(String nama, String password) {
                this.id = generateRandomID();
                this.nama = nama;
                this.password = password; 
        }

        private int generateRandomID() {
                int randomNum;

                do {
                randomNum = random.nextInt(9000) + 1000;
                } while  (usedId.contains(randomNum));

                usedId.add(randomNum);
                return  randomNum;
        }

        public abstract void login();

        public void logout() {
                System.out.println("Anda telah berhasil logout");
        };

        public void tampilkanInfo() {
                System.out.println("ID   : " + id);
                System.out.println("Nama : " + nama);
        };

        public int getId() {
                return id;
        }

        public String getNama(){
                return nama ;
        }

        public String getPassword() {
                return password 
                ;
        }

        public void setPassword(String newPass) {
                this.password = newPass;
                System.out.println("Password berhasil diubah");
        };

}