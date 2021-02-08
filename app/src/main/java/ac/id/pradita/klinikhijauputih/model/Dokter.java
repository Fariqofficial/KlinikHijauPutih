package ac.id.pradita.klinikhijauputih.model;

public class Dokter {

    String id_dokter, ktp, nama, alamat, poli, telpon, username, password;

    public Dokter() {
    }

    public Dokter(String id_dokter, String ktp, String nama, String alamat, String poli, String telpon, String username, String password) {
        this.id_dokter = id_dokter;
        this.ktp = ktp;
        this.nama = nama;
        this.alamat = alamat;
        this.poli = poli;
        this.telpon = telpon;
        this.username = username;
        this.password = password;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPoli() {
        return poli;
    }

    public void setPoli(String poli) {
        this.poli = poli;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
