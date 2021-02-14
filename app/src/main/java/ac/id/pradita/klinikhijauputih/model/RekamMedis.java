package ac.id.pradita.klinikhijauputih.model;

public class RekamMedis {
    String nama_pasien, id_rekMedis, id_dokter, id_pasien, anastesa, diagnosa, terapi, resep, tanggal;

    public RekamMedis() {
    }

    public RekamMedis(String nama_pasien, String id_rekMedis, String id_dokter, String id_pasien, String anastesa, String diagnosa, String terapi, String resep, String tanggal) {
        this.nama_pasien = nama_pasien;
        this.id_rekMedis = id_rekMedis;
        this.id_dokter = id_dokter;
        this.id_pasien = id_pasien;
        this.anastesa = anastesa;
        this.diagnosa = diagnosa;
        this.terapi = terapi;
        this.resep = resep;
        this.tanggal = tanggal;
    }

    public String getNama_pasien() {
        return nama_pasien;
    }

    public void setNama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
    }

    public String getId_rekMedis() {
        return id_rekMedis;
    }

    public void setId_rekMedis(String id_rekMedis) {
        this.id_rekMedis = id_rekMedis;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getAnastesa() {
        return anastesa;
    }

    public void setAnastesa(String anastesa) {
        this.anastesa = anastesa;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getTerapi() {
        return terapi;
    }

    public void setTerapi(String terapi) {
        this.terapi = terapi;
    }

    public String getResep() {
        return resep;
    }

    public void setResep(String resep) {
        this.resep = resep;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
