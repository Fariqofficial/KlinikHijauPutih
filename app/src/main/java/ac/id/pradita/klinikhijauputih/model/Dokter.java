package ac.id.pradita.klinikhijauputih.model;

public class Dokter {

    String id_dokter, ktp, nama, alamat, poli, telpon, hari, ket_dokter, email, password;

    public Dokter() {
    }

    public Dokter(String id_dokter, String ktp, String nama, String alamat, String poli, String telpon, String hari, String ket_dokter, String email, String password) {
        this.id_dokter = id_dokter;
        this.ktp = ktp;
        this.nama = nama;
        this.alamat = alamat;
        this.poli = poli;
        this.telpon = telpon;
        this.hari = hari;
        this.ket_dokter = ket_dokter;
        this.email = email;
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

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getKet_dokter() {
        return ket_dokter;
    }

    public void setKet_dokter(String ket_dokter) {
        this.ket_dokter = ket_dokter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
