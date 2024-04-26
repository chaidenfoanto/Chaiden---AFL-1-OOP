import java.time.LocalDate;

public class Pengembalian {
    private String noKembali;
    private Peminjaman noPinjam;
    private Pustakawan IDPustakawan;
    private String tglKembali;
    private Mahasiswa NIM;
    private Dosen_Staff NIK;
    private int totalDendaRusak;
    private int totalTelat;
    private int totalBayar;

    public Pengembalian() {
    }

    public Pengembalian(String noKembali, Peminjaman noPinjam, Pustakawan IDPustakawan, String tglKembali,
            Mahasiswa NIM, Dosen_Staff NIK, int totalDendaRusak, int totalTelat, int totalBayar) {
        this.noKembali = noKembali;
        this.noPinjam = noPinjam;
        this.IDPustakawan = IDPustakawan;
        this.tglKembali = tglKembali;
        this.NIM = NIM;
        this.NIK = NIK;
        this.totalDendaRusak = totalDendaRusak;
        this.totalTelat = totalTelat;
        this.totalBayar = totalBayar;
    }

    public void inputData(Peminjaman noPinjam, Mahasiswa mhs, Pustakawan pustakawan, int selisihhari) {
        this.noPinjam = noPinjam;
        NIM = mhs;
        IDPustakawan = pustakawan;
        tglKembali = Main.TimeNow();
        if (selisihhari > 0) {
            int totalDenda = hitungTotalDenda(selisihhari);
            System.out.println("Denda yang harus dibayar: " + totalDenda);

            int pembayaran = Main.getInputInt("Masukkan jumlah pembayaran: ");

            int kembalian = pembayaran - totalDenda;
            if (kembalian >= 0) {
                System.out.println("Kembalian: " + kembalian);
            } else {
                System.out.println("Pembayaran kurang. Tidak ada kembalian.");
            }
            System.out.println("Pengembalian berhasil disimpan.");
        } else {
            System.out.println("Tidak ada denda.");
        }

    }

    public void inputData(Peminjaman noPinjam, Dosen_Staff dosen, Pustakawan pustakawan, int selisihhari) {
        this.noPinjam = noPinjam;
        NIK = dosen;
        IDPustakawan = pustakawan;
        tglKembali = Main.TimeNow();
        if (selisihhari > 0) {
            int totalDenda = hitungTotalDenda(selisihhari);
            System.out.println("Denda yang harus dibayar: " + totalDenda);

            int pembayaran = Main.getInputInt("Masukkan jumlah pembayaran: ");

            int kembalian = pembayaran - totalDenda;
            if (kembalian >= 0) {
                System.out.println("Kembalian: " + kembalian);
            } else {
                System.out.println("Pembayaran kurang. Tidak ada kembalian.");
            }
            System.out.println("Pengembalian berhasil disimpan.");
        } else {
            System.out.println("Tidak ada denda.");
        }

    }

    public static long hitungSelisihHari(LocalDate dueDate, LocalDate currentDate) {
        return currentDate.until(dueDate).getDays();
    }

    public static int hitungTotalDenda(long selisihHari) {
        return (int) (selisihHari * 2000);
    }

    UserType getPilihan() {
        System.out.println("Pilih: ");
        for (UserType pilihan : UserType.values()) {
            System.out.println((pilihan.ordinal() + 1) + ". " + pilihan);
        }
        int choice = Main.getChoice(UserType.values().length);
        return UserType.values()[choice - 1];
    }

    public String getNoKembali() {
        return this.noKembali;
    }

    public void setNoKembali(String noKembali) {
        this.noKembali = noKembali;
    }

    public Peminjaman getNoPinjam() {
        return this.noPinjam;
    }

    public void setNoPinjam(Peminjaman noPinjam) {
        this.noPinjam = noPinjam;
    }

    public Pustakawan getIDPustakawan() {
        return this.IDPustakawan;
    }

    public void setIDPustakawan(Pustakawan IDPustakawan) {
        this.IDPustakawan = IDPustakawan;
    }

    public String getTglKembali() {
        return this.tglKembali;
    }

    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }

    public Mahasiswa getNIM() {
        return this.NIM;
    }

    public void setNIM(Mahasiswa NIM) {
        this.NIM = NIM;
    }

    public Dosen_Staff getNIK() {
        return this.NIK;
    }

    public void setNIK(Dosen_Staff NIK) {
        this.NIK = NIK;
    }

    public int getTotalDendaRusak() {
        return this.totalDendaRusak;
    }

    public void setTotalDendaRusak(int totalDendaRusak) {
        this.totalDendaRusak = totalDendaRusak;
    }

    public int getTotalTelat() {
        return this.totalTelat;
    }

    public void setTotalTelat(int totalTelat) {
        this.totalTelat = totalTelat;
    }

    public int getTotalBayar() {
        return this.totalBayar;
    }

    public void setTotalBayar(int totalBayar) {
        this.totalBayar = totalBayar;
    }

}
