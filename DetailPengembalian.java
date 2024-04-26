import java.util.ArrayList;

public class DetailPengembalian {
    private Pengembalian noPengembalian;
    private int jumlah;
    private Buku IDBuku;
    private CD IDCD;

    public DetailPengembalian() {

    }

    public DetailPengembalian(Pengembalian noPengembalian, int jumlah, Buku IDBuku, CD IDCD) {
        this.noPengembalian = noPengembalian;
        this.jumlah = jumlah;
        this.IDBuku = IDBuku;
        this.IDCD = IDCD;
    }

    public void inputData(Pengembalian noKembali, Buku IDBuku) {

        this.noPengembalian = noKembali;
        jumlah = Main.getInputInt("Masukkan jumlah pengembalian: ");
        this.IDBuku = IDBuku;

    }

    public void inputData(Pengembalian noKembali, CD IDCD) {

        this.noPengembalian = noKembali;
        jumlah = Main.getInputInt("Masukkan jumlah pengembalian: ");
        this.IDCD = IDCD;
    }

    public void inputData(Pengembalian noPengembalian, ArrayList<Buku> bukuList, ArrayList<CD> listCD) {
        this.noPengembalian = noPengembalian;

        int choice;
        System.out.println("Pilih jenis koleksi yang ingin anda pinjam: ");
        Pilihan pilihan = getPilihan();
        if (pilihan == Pilihan.Buku) {
            System.out.println("Daftar Buku:");
            for (int i = 0; i < bukuList.size(); i++) {
                System.out.println((i + 1) + ". " + bukuList.get(i).getIDBuku() + " - "
                        + bukuList.get(i).getJudul());
            }

            choice = Main.getChoice(bukuList.size());
            IDBuku = bukuList.get(choice - 1);

        } else if (pilihan == Pilihan.CD) {
            System.out.println("Daftar CD:");
            for (int i = 0; i < listCD.size(); i++) {
                System.out.println((i + 1) + ". " + listCD.get(i).getIDCD() + " - "
                        + listCD.get(i).getJudul());
            }

            choice = Main.getChoice(listCD.size());
            IDCD = listCD.get(choice - 1);
        }
    }

    Pilihan getPilihan() {
        System.out.println("Pilih: ");
        for (Pilihan pilihan : Pilihan.values()) {
            System.out.println((pilihan.ordinal() + 1) + ". " + pilihan);
        }
        int choice = Main.getChoice(Pilihan.values().length);
        return Pilihan.values()[choice - 1];
    }

    public Pengembalian getNoPengembalian() {
        return this.noPengembalian;
    }

    public void setNoPengembalian(Pengembalian noPengembalian) {
        this.noPengembalian = noPengembalian;
    }

    public int getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Buku getIDBuku() {
        return this.IDBuku;
    }

    public void setIDBuku(Buku IDBuku) {
        this.IDBuku = IDBuku;
    }

    public CD getIDCD() {
        return this.IDCD;
    }

    public void setIDCD(CD IDCD) {
        this.IDCD = IDCD;
    }

}

enum Pilihan {
    Buku,
    CD
}
