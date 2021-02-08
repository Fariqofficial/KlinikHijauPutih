package ac.id.pradita.klinikhijauputih.model;

public class Jadwal {

    String id_jadwal, nama_dokter, hari_praktek, keterangan_dokter, nama_staff;

    public Jadwal() {
    }

    public Jadwal(String id_jadwal, String nama_dokter, String hari_praktek, String keterangan_dokter, String nama_staff) {
        this.id_jadwal = id_jadwal;
        this.nama_dokter = nama_dokter;
        this.hari_praktek = hari_praktek;
        this.keterangan_dokter = keterangan_dokter;
        this.nama_staff = nama_staff;
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

    public String getHari_praktek() {
        return hari_praktek;
    }

    public void setHari_praktek(String hari_praktek) {
        this.hari_praktek = hari_praktek;
    }

    public String getKeterangan_dokter() {
        return keterangan_dokter;
    }

    public void setKeterangan_dokter(String keterangan_dokter) {
        this.keterangan_dokter = keterangan_dokter;
    }

    public String getNama_staff() {
        return nama_staff;
    }

    public void setNama_staff(String nama_staff) {
        this.nama_staff = nama_staff;
    }
}
