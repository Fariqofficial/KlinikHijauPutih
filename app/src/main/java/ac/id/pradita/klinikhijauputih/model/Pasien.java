package ac.id.pradita.klinikhijauputih.model;

public class Pasien {

    String id_pasien, no_ktp, nama, alamat, jenis_kelamin, umur, status, no_hp, nama_ibu, nama_pasangan;

    public Pasien() {
    }

    public Pasien(String id_pasien, String no_ktp, String nama, String alamat, String jenis_kelamin, String umur, String status, String no_hp, String nama_ibu, String nama_pasangan) {
        this.id_pasien = id_pasien;
        this.no_ktp = no_ktp;
        this.nama = nama;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
        this.umur = umur;
        this.status = status;
        this.no_hp = no_hp;
        this.nama_ibu = nama_ibu;
        this.nama_pasangan = nama_pasangan;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
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

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getNama_ibu() {
        return nama_ibu;
    }

    public void setNama_ibu(String nama_ibu) {
        this.nama_ibu = nama_ibu;
    }

    public String getNama_pasangan() {
        return nama_pasangan;
    }

    public void setNama_pasangan(String nama_pasangan) {
        this.nama_pasangan = nama_pasangan;
    }
}
