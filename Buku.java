public class Buku {
    private String IDBuku;
    private String Judul;
    private String Pengarang;
    private String Penerbit;
    private String tahunTerbit;
    private String edisiTerbit;
    private Cover jenisSampul;
    private int jumlahTersedia;
    private int jumlahTerpinjam;
    private int jumlahRusak;
    private int jumlahTotal;
    private GenreChoice Genre;
    private int HargaBeli;

    public Buku() {
    }

    public Buku(String IDBuku, String Judul, String Pengarang, String Penerbit, String tahunTerbit, String edisiTerbit,
            Cover jenisSampul, int jumlahTersedia, int jumlahTerpinjam, int jumlahRusak, int jumlahTotal,
            GenreChoice Genre, int HargaBeli) {
        this.IDBuku = IDBuku;
        this.Judul = Judul;
        this.Pengarang = Pengarang;
        this.Penerbit = Penerbit;
        this.tahunTerbit = tahunTerbit;
        this.edisiTerbit = edisiTerbit;
        this.jenisSampul = jenisSampul;
        this.jumlahTersedia = jumlahTersedia;
        this.jumlahTerpinjam = jumlahTerpinjam;
        this.jumlahRusak = jumlahRusak;
        this.jumlahTotal = jumlahTotal;
        this.Genre = Genre;
        this.HargaBeli = HargaBeli;
    }

    public void inputData(String idbuku) {
        IDBuku = idbuku;
        Judul = Main.getInput(
            "Book Title\t\t: ", false, false, false);
        Pengarang = Main.getInput(
            "Author\t\t\t: ", false, false, false);
        Penerbit = Main.getInput(
            "Publisher\t\t: ", false, false, false);
        tahunTerbit = Main.getInputDate(
            "Publication Year\t: ");
        edisiTerbit = Main.getInput(
            "Published Edition\t:", false, false, false);
        jenisSampul = getCover1();
        jumlahTersedia = Main.getInputInt(
            "Quantity Available\t: ");
        jumlahTerpinjam = Main.getInputInt(
            "Borrowed Ammount\t\t: ");
        jumlahRusak = Main.getInputInt(
            "Number of Damaged\t: ");
        jumlahTotal = jumlahTersedia + jumlahTerpinjam;
        System.out.println(
            "Total Book = " + jumlahTotal);
        Genre = getGenre1();
        HargaBeli = Main.getInputInt(
            "Book Prices\t\t: ");
    }

    Cover getCover1() {
        System.out.println(
            "Cover Type\t\t: ");
        for (Cover cover : Cover.values()) {
            System.out.println((cover.ordinal() + 1) + ". " + cover);
        }
        int choice = Main.getChoice(Cover.values().length);
        return Cover.values()[choice - 1];
    }

    GenreChoice getGenre1() {
        System.out.println("Genre\t\t\t: ");
        for (GenreChoice genre : GenreChoice.values()) {
            System.out.println((genre.ordinal() + 1) + ". " + genre);
        }
        int choice = Main.getChoice(GenreChoice.values().length);
        return GenreChoice.values()[choice - 1];
    }

    public GenreChoice getGenre() {
        return this.Genre;
    }

    public Cover getCover() {
        return this.jenisSampul;
    }

    public String getIDBuku() {
        return this.IDBuku;
    }

    public void setIDBuku(String IDBuku) {
        this.IDBuku = IDBuku;
    }

    public String getJudul() {
        return this.Judul;
    }

    public int getHargaDenda() {
        return this.HargaBeli;
    }

    public void setHargaDenda(int HargaBeli) {
        this.HargaBeli = HargaBeli;
    }

    public void setJudul(String Judul) {
        this.Judul = Judul;
    }

    public String getPengarang() {
        return this.Pengarang;
    }

    public void setPengarang(String Pengarang) {
        this.Pengarang = Pengarang;
    }

    public String getPenerbit() {
        return this.Penerbit;
    }

    public void setPenerbit(String Penerbit) {
        this.Penerbit = Penerbit;
    }

    public String getTahunTerbit() {
        return this.tahunTerbit;
    }

    public void setTahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public String getEdisiTerbit() {
        return this.edisiTerbit;
    }

    public void setEdisiTerbit(String edisiTerbit) {
        this.edisiTerbit = edisiTerbit;
    }

    public Cover getJenisSampul() {
        return this.jenisSampul;
    }

    public void setJenisSampul(Cover jenisSampul) {
        this.jenisSampul = jenisSampul;
    }

    public int getJumlahTersedia() {
        return this.jumlahTersedia;
    }

    public void setJumlahTersedia(int jumlahTersedia) {
        this.jumlahTersedia = jumlahTersedia;
    }

    public int getJumlahTerpinjam() {
        return this.jumlahTerpinjam;
    }

    public void setJumlahTerpinjam(int jumlahTerpinjam) {
        this.jumlahTerpinjam = jumlahTerpinjam;
    }

    public int getJumlahRusak() {
        return this.jumlahRusak;
    }

    public void setJumlahRusak(int jumlahRusak) {
        this.jumlahRusak = jumlahRusak;
    }

    public int getJumlahTotal() {
        return this.jumlahTotal;
    }

    public void setJumlahTotal(int jumlahTotal) {
        this.jumlahTotal = jumlahTotal;
    }

    public void setGenre(GenreChoice Genre) {
        this.Genre = Genre;
    }

}

enum Cover {
    Hardcover,
    Softcover
}

enum GenreChoice {
    Horor,
    Romance,
    Mistery,
    Comedy,
    Fantasy,
    Action,
    SciFi,
    Education
}
