package ac.id.pradita.klinikhijauputih.model;

public class Staff {

    String id_staff, nama, alamat, email, no_hp, username, password;

    public Staff() {
    }

    public Staff(String id_staff, String nama, String alamat, String email, String no_hp, String username, String password) {
        this.id_staff = id_staff;
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.no_hp = no_hp;
        this.username = username;
        this.password = password;
    }

    public String getId_staff() {
        return id_staff;
    }

    public void setId_staff(String id_staff) {
        this.id_staff = id_staff;
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

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
