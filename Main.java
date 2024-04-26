import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static ArrayList<Mahasiswa> usermhs = new ArrayList<>();
    private static ArrayList<Dosen_Staff> userdosen = new ArrayList<>();
    private static ArrayList<Pustakawan> listpustakawan = new ArrayList<>();
    private static ArrayList<CD> listcd = new ArrayList<>();
    private static ArrayList<Buku> daftarBuku = new ArrayList<>();
    private static ArrayList<Peminjaman> dafPeminjaman = new ArrayList<>();
    private static ArrayList<DetailPeminjaman> dafDetPeminjaman = new ArrayList<>();
    private static ArrayList<Pengembalian> dafPengembalian = new ArrayList<>();
    private static ArrayList<DetailPengembalian> dafDetPengembalian = new ArrayList<>();
    private static ArrayList<DetailPembayaran> dafDetPembayaran = new ArrayList<>();
    public static Pustakawan pustaktif;
    public static Object useraktif;
    // private static ArrayList<DetailPeminjaman> dafDetPeminjaman = new
    // ArrayList<>();
    private static Scanner myScanner = new Scanner(System.in);

    public static String TimeNow() {
        LocalDate tanggalPinjam = LocalDate.now();
        return String.valueOf(tanggalPinjam);
    }

    public static String autokodePust(ArrayList<Pustakawan> pustakawan) {
        if (pustakawan.isEmpty()) {
            return "PUST" + String.format("%03d", 1);
        } else {
            String lastID = pustakawan.get(pustakawan.size() - 1).getIDPustakawan();
            int lastNumber = Integer.parseInt(lastID.substring(4));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "PUST" + formattedNumber;
        }
    }

    public static String autokodeMhs(ArrayList<Mahasiswa> mhs) {
        if (mhs.isEmpty()) {
            return "MHS" + String.format("%03d", 1);
        } else {
            String lastID = mhs.get(mhs.size() - 1).getNIM();
            int lastNumber = Integer.parseInt(lastID.substring(4));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "MHS" + formattedNumber;
        }
    }

    public static String autokodeDosen(ArrayList<Dosen_Staff> dosen) {
        if (dosen.isEmpty()) {
            return "DST" + String.format("%03d", 1);
        } else {
            String lastID = dosen.get(dosen.size() - 1).getNIK();
            int lastNumber = Integer.parseInt(lastID.substring(4));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "DST" + formattedNumber;
        }
    }

    public static String autokodeCD(ArrayList<CD> listcd) {
        if (listcd.isEmpty()) {
            return "CD" + String.format("%03d", 1);
        } else {
            String lastID = listcd.get(listcd.size() - 1).getIDCD();
            int lastNumber = Integer.parseInt(lastID.substring(2));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "CD" + formattedNumber;
        }
    }

    public static String autokodeBuku(ArrayList<Buku> listbuku) {
        if (listbuku.isEmpty()) {
            return "BUKU" + String.format("%03d", 1);
        } else {
            String lastID = listbuku.get(listbuku.size() - 1).getIDBuku();
            int lastNumber = Integer.parseInt(lastID.substring(4));
            lastNumber++;
            String formattedNumber = String.format("%03d", lastNumber);
            return "BUKU" + formattedNumber;
        }
    }

    public static String autoNoTransaksi(String prefix) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String tanggal = dateFormat.format(new Date());
        int maxNum = 0;
        if (prefix == "PNJM") {
            for (Peminjaman kode : dafPeminjaman) {
                if (kode.getNoPinjam().startsWith(prefix + tanggal)) {
                    int num = Integer.parseInt(kode.getNoPinjam().substring(prefix.length() + 6));
                    if (num > maxNum) {
                        maxNum = num;
                    }
                }
            }
        } else {
            for (Pengembalian kode : dafPengembalian) {
                if (kode.getNoKembali().startsWith(prefix + tanggal)) {
                    int num = Integer.parseInt(kode.getNoKembali().substring(prefix.length() + 6));
                    if (num > maxNum) {
                        maxNum = num;
                    }
                }
            }
        }
        return String.format("%s%s%03d", prefix, tanggal, maxNum + 1);

    }

    public static String getInputDate(String message) {
        System.out.print(message + "(yyyy-MM-dd): ");
        String input = myScanner.nextLine().trim();

        while (!isValidDateFormat(input)) {
            System.out.println("Invalid date format. Please re-enter it.");
            getInputDate(message);
        }

        return input;
    }

    public static boolean isValidDateFormat(String input) {
        try {
            LocalDate.parse(input);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String getInput(String message, boolean notelp, boolean yesno, boolean NIM) {
        try {
            System.out.print(message);
            String input = myScanner.nextLine().trim();

            if (notelp != true && NIM != true) {
                if (input.matches("\\d+")) {
                    throw new IllegalArgumentException("Input cannot contain numbers only.");
                }
            } else {
                if (!Pattern.matches("[\\p{Alnum}]+", input)) {
                    System.out.println("Input contains symbols. Please enter an integer.");
                    return getInput(message, notelp, yesno, NIM);
                }

                if (Pattern.matches(".*[a-zA-Z].*", input)) {
                    System.out.println("Input contains letters of the alphabet. Please enter an integer.");
                    return getInput(message, notelp, yesno, NIM);
                }

                if (input.length() > 13) {
                    System.out.println("The maximum telephone number is only 13 numbers.");
                    return getInput(message, notelp, yesno, NIM);
                }
            }

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please re-enter it");
                return getInput(message, notelp, yesno, NIM);
            }

            if (yesno == true) {
                if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.println("Please input [Y/N] only!");
                    return getInput(message, notelp, yesno, NIM);
                }
            }

            return input;
        } catch (IllegalArgumentException ex) {
            System.out.println("Error Detected : " + ex.getMessage());
            return getInput(message, notelp, yesno, NIM);
        } catch (Exception ex) {
            System.out.println("Error Detected : " + ex.getMessage());
            return getInput(message, notelp, yesno, NIM);
        }
    }

    public static String getInputJK(String message) {
        try {
            System.out.print(message);
            String input = myScanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input tidak boleh kosong. Silakan coba lagi.");
                return getInputJK(message);
            }

            if (!input.equalsIgnoreCase("m") && !input.equalsIgnoreCase("f")) {
                System.out.println("Tolong isi hanya M / F.");
                return getInputJK(message);
            }

            return input.toUpperCase();
        } catch (Exception ex) {
            System.out.println("Terjadi kesalahan: " + ex.getMessage());
            return getInputJK(message);
        }
    }

    public static int getInputInt(String message) {
        try {
            System.out.print(message);
            String inputStr = myScanner.nextLine().trim();
            if (inputStr.isEmpty()) {
                System.out.println("Input cannot be empty. Please re-enter it");
                return getInputInt(message);
            }

            if (inputStr.contains(".") || inputStr.contains(",")) {
                System.out.println("Invalid input. Please enter an integers.");
                return getInputInt(message);
            }

            Integer input = Integer.parseInt(inputStr);

            if (input < 0) {
                System.out.println("Input cannot be less than or equal to 0. Please try again.");
                return getInputInt(message);
            }

            return input;
        } catch (NumberFormatException ex) {
            System.out.println("Invalid Input. Please enter an integers.");
            return getInputInt(message);
        } catch (Exception ex) {
            System.out.println("Error Detected : " + ex.getMessage());
            return getInputInt(message);
        }
    }

    public static float getInputFloat(String message) {
        try {
            System.out.print(message);
            String inputStr = myScanner.nextLine().trim();
            if (inputStr.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                return getInputFloat(message);
            }

            if (inputStr.contains(",")) {
                System.out.println("The symbol for decimal numbers is [ . ], not [ , ]");
                return getInputFloat(message);
            }

            Float input = Float.parseFloat(inputStr);

            if (input <= 0) {
                System.out.println("Input cannot be less than or equal to 0. Please try again.");
                return getInputFloat(message);
            }

            return input;
        } catch (Exception ex) {
            System.out.println("Error Detected: " + ex.getMessage());
            return getInputFloat(message);
        }
    }

    public static int getChoice(int max) {
        while (true) {
            try {
                int choice = Main.getInputInt("Choices: ");
                if (choice < 1 || choice > max) {
                    if (choice == 0)
                        return choice;
                    throw new NumberFormatException();
                }
                return choice;
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection.");
            }
        }
    }

    public static void displayBuku() {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();

        String[] header = { "Book ID", "Book Title", "Author", "Publisher", "Publication Year", "Published Edition", "Cover Type",
                "Quantity Available", "Borrowed Amount", "Number of Damaged", "Total Book", "Genre", "Fine Fees" };
        int[] columnLengths = new int[header.length]; 

        for (Buku buku : daftarBuku) {
            String[] data = { buku.getIDBuku(), buku.getJudul(), buku.getPengarang(), buku.getPenerbit(),
                    buku.getTahunTerbit(), buku.getEdisiTerbit(), String.valueOf(buku.getJenisSampul()),
                    String.valueOf(buku.getJumlahTersedia()), String.valueOf(buku.getJumlahTerpinjam()),
                    String.valueOf(buku.getJumlahRusak()),
                    String.valueOf(buku.getJumlahTotal()), String.valueOf(buku.getGenre()),
                    String.valueOf(buku.getHargaDenda()) };

            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Book Data List";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (Buku buku : daftarBuku) {
            String[] data = { buku.getIDBuku(), buku.getJudul(), buku.getPengarang(), buku.getPenerbit(),
                    buku.getTahunTerbit(), buku.getEdisiTerbit(), String.valueOf(buku.getJenisSampul()),
                    String.valueOf(buku.getJumlahTersedia()), String.valueOf(buku.getJumlahTerpinjam()),
                    String.valueOf(buku.getJumlahRusak()),
                    String.valueOf(buku.getJumlahTotal()), String.valueOf(buku.getGenre()),
                    String.valueOf(buku.getHargaDenda()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());
    }

    public static void displayCD() {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();

        String[] header = { "CD ID", "CD Title", "Genre", "Publication Year", "Shelf", "Quantity Available", "Borrowed Amount",
                "Number of Damaged", "CD Total" };
        int[] columnLengths = new int[header.length];

        for (CD cd : listcd) {
            // Mengambil data dari setiap buku
            String[] data = { cd.getIDCD(), cd.getJudul(),
                    String.valueOf(cd.getGenre()), String.valueOf(cd.getTahun_publikasi()),
                    String.valueOf(cd.getRak()),
                    String.valueOf(cd.getJumlahTersedia()), String.valueOf(cd.getJumlahTerpinjam()),
                    String.valueOf(cd.getJumlahRusak()), String.valueOf(cd.getJumlahTotal()) };

            // Memperbarui panjang maksimum untuk setiap kolom
            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "CD Data List";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (CD cd : listcd) {
            String[] data = { cd.getIDCD(), cd.getJudul(),
                    String.valueOf(cd.getGenre()), String.valueOf(cd.getTahun_publikasi()),
                    String.valueOf(cd.getRak()),
                    String.valueOf(cd.getJumlahTersedia()), String.valueOf(cd.getJumlahTerpinjam()),
                    String.valueOf(cd.getJumlahRusak()), String.valueOf(cd.getJumlahTotal()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());
    }

    public static void displayDosen_Staff() {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();

        String[] header = { "NIK", "Name", "Password", "Address", "Phone Number", "Study Program", "Faculty",
                "Have a fine?", "Total Fine" };
        int[] columnLengths = new int[header.length];

        for (Dosen_Staff DS : userdosen) {
            // Mengambil data dari setiap buku
            String[] data = { DS.getNIK(), DS.getNama(), DS.getPassword(), DS.getAlamat(),
                    DS.getNoHP(), String.valueOf(DS.getProdi()),
                    String.valueOf(DS.getFakultas()), String.valueOf(DS.getPDenda()),
                    String.valueOf(DS.getTotalDenda()) };

            // Memperbarui panjang maksimum untuk setiap kolom
            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Dosen/Staff Data List";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (Dosen_Staff DS : userdosen) {
            // Mengambil data dari setiap buku
            String[] data = { DS.getNIK(), DS.getNama(), DS.getPassword(), DS.getAlamat(),
                    DS.getNoHP(), String.valueOf(DS.getProdi()),
                    String.valueOf(DS.getFakultas()), String.valueOf(DS.getPDenda()),
                    String.valueOf(DS.getTotalDenda()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());
    }

    public static void displayMahasiswa() {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();

        String[] header = { "NIM", "Name", "Password", "Adress", "Phone Mnumber", "Study Program", "Faculty",
                "Have a fine?", "Total Fine" };
        int[] columnLengths = new int[header.length];

        for (Mahasiswa mhs : usermhs) {
            // Mengambil data dari setiap buku
            String[] data = { String.valueOf(mhs.getNIM()), String.valueOf(mhs.getNama()),
                    String.valueOf(mhs.getPassword()), String.valueOf(mhs.getAlamat()),
                    mhs.getNoHP(), String.valueOf(mhs.getProdi()),
                    String.valueOf(mhs.getFakultas()), String.valueOf(mhs.getPDenda()),
                    String.valueOf(mhs.getTotalDenda()) };

            // Memperbarui panjang maksimum untuk setiap kolom
            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Mahasiswa Data List";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (Mahasiswa mhs : usermhs) {
            // Mengambil data dari setiap buku
            String[] data = { String.valueOf(mhs.getNIM()), String.valueOf(mhs.getNama()),
                    String.valueOf(mhs.getPassword()), String.valueOf(mhs.getAlamat()),
                    mhs.getNoHP(), String.valueOf(mhs.getProdi()),
                    String.valueOf(mhs.getFakultas()), String.valueOf(mhs.getPDenda()),
                    String.valueOf(mhs.getTotalDenda()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());
    }

    public static void displayPustakawan() {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();

        String[] header = { "Pustakawan ID", "Name", "Email", "Password", "Address", "Phone Number", "Gender" };
        int[] columnLengths = new int[header.length];

        for (Pustakawan pust : listpustakawan) {
            // Mengambil data dari setiap buku
            String[] data = { pust.getIDPustakawan(), pust.getNama(), pust.getEmail(), pust.getPassword(),
                    pust.getAlamat(), pust.getNoHP(), String.valueOf(pust.getJenisKelamin()) };

            // Memperbarui panjang maksimum untuk setiap kolom
            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Daftar Data Pustakawan";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (Pustakawan pust : listpustakawan) {
            String[] data = { pust.getIDPustakawan(), pust.getNama(), pust.getEmail(), pust.getPassword(),
                    pust.getAlamat(), pust.getNoHP(), String.valueOf(pust.getJenisKelamin()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());
    }

    public static void displayPeminjaman() {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();
        String mahasiswa = "", dosen = "";

        String[] header = { "No. Peminjaman", "Nama Pustakawan", "Nama Mahasiswa", "Nama Dosen/Staff",
                "Tanggal Peminjaman", "Tenggat Kembali",
                "Status" };
        int[] columnLengths = new int[header.length];

        for (Peminjaman peminjaman : dafPeminjaman) {
            if (peminjaman.getNIK() != null)
                dosen = peminjaman.getNIK().getNama();
            else if (peminjaman.getNIM() != null)
                mahasiswa = peminjaman.getNIM().getNama();
            String[] data = { peminjaman.getNoPinjam(), peminjaman.getIDPustakawan().getNama(),
                    mahasiswa, dosen,
                    peminjaman.getTglPinjam(), String.valueOf(peminjaman.getDueDate()),
                    String.valueOf(peminjaman.getStatus()) };

            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Peminjaman";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (Peminjaman peminjaman : dafPeminjaman) {
            if (peminjaman.getNIK() != null)
                dosen = peminjaman.getNIK().getNama();
            else if (peminjaman.getNIM() != null)
                mahasiswa = peminjaman.getNIM().getNama();
            String[] data = { peminjaman.getNoPinjam(), peminjaman.getIDPustakawan().getNama(),
                    mahasiswa, dosen,
                    peminjaman.getTglPinjam(), String.valueOf(peminjaman.getDueDate()),
                    String.valueOf(peminjaman.getStatus()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());

        boolean isRunning = true;
        while (isRunning) {
            String input = getInput("Wanna See Detail Peminjaman ? [Y/N]", false, true, false);

            if (input.equalsIgnoreCase("Y")) {
                System.out.println("");
                String noPinjam = getInput("Input the No. Peminajaman to see the details [0 to Exit]:",
                        false, false, false);

                if (noPinjam.equals("0")) {
                    break;
                }

                Peminjaman noPinjamPilih = null;
                for (Peminjaman peminjaman : dafPeminjaman) {
                    if (peminjaman.getNoPinjam().equals(noPinjam)) {
                        noPinjamPilih = peminjaman;
                        break;
                    }
                }

                if (noPinjamPilih != null) {
                    displayDetailPeminjaman(noPinjamPilih);
                } else {
                    System.out.println("No. Peminjaman not found.");
                }
            } else if (input.equalsIgnoreCase("N")) {
                isRunning = false;
            }
        }
    }

    public static void displayDetailPeminjaman(Peminjaman noPinjam) {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();
        String idbuku = "", idCD = "";

        String[] header = { "No. Peminjaman", "Judul Buku", "Judul CD", "Jumlah" };
        int[] columnLengths = new int[header.length];

        for (DetailPeminjaman detpeminjaman : dafDetPeminjaman) {
            if (detpeminjaman.getNoPinjam().equals(noPinjam)) {
                if (detpeminjaman.getIDBuku() != null)
                    idbuku = detpeminjaman.getIDBuku().getJudul();
                else if (detpeminjaman.getIDCD() != null)
                    idCD = detpeminjaman.getIDCD().getJudul();
                String[] data = { String.valueOf(detpeminjaman.getNoPinjam()),
                        idbuku, idCD, String.valueOf(detpeminjaman.getJumlah()) };

                for (int i = 0; i < data.length; i++) {
                    columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                    if (columnLengths[i] < header[i].length()) {
                        columnLengths[i] = header[i].length();
                    }
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Detail Peminjaman";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (DetailPeminjaman detpeminjaman : dafDetPeminjaman) {
            if (detpeminjaman.getNoPinjam().equals(noPinjam)) {
                if (detpeminjaman.getIDBuku() != null)
                    idbuku = detpeminjaman.getIDBuku().getJudul();
                else if (detpeminjaman.getIDCD() != null)
                    idCD = detpeminjaman.getIDCD().getJudul();
                String[] data = { String.valueOf(detpeminjaman.getNoPinjam()),
                        idbuku, idCD, String.valueOf(detpeminjaman.getJumlah()) };

                for (int i = 0; i < data.length; i++) {
                    System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
                }
                System.out.println("|");
            }
        }

        System.out.println(separatorTop.toString());
    }

    public static void displayPengembalian() {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();
        String mahasiswa = "", dosen = "";

        String[] header = { "No. Pengembalian", "No. Peminjaman", "Nama Pustakawan", "Tanggal Pengembalian",
                "Nama Mahasiswa", "Nama Dosen/Staff",
                "Biaya Denda", "Biaya Telat", "Total Bayar" };
        int[] columnLengths = new int[header.length];

        for (Pengembalian pengembalian : dafPengembalian) {
            if (pengembalian.getNIK() != null) {
                dosen = pengembalian.getNIK().getNama();
            } else if (pengembalian.getNIM() != null) {
                mahasiswa = pengembalian.getNIM().getNama();
            }
            String[] data = { String.valueOf(pengembalian.getNoKembali()), String.valueOf(pengembalian.getNoPinjam()),
                    String.valueOf(pengembalian.getIDPustakawan().getNama()),
                    String.valueOf(pengembalian.getTglKembali()), mahasiswa,
                    dosen,
                    String.valueOf(pengembalian.getTotalDendaRusak()), String.valueOf(pengembalian.getTotalTelat()),
                    String.valueOf(pengembalian.getTotalBayar()) };

            // Memperbarui panjang maksimum untuk setiap kolom
            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Pengembalian";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (Pengembalian pengembalian : dafPengembalian) {
            if (pengembalian.getNIK() != null) {
                dosen = pengembalian.getNIK().getNama();
            } else if (pengembalian.getNIM() != null) {
                mahasiswa = pengembalian.getNIM().getNama();
            }
            String[] data = { String.valueOf(pengembalian.getNoKembali()), String.valueOf(pengembalian.getNoPinjam()),
                    String.valueOf(pengembalian.getIDPustakawan().getNama()),
                    String.valueOf(pengembalian.getTglKembali()), mahasiswa,
                    dosen,
                    String.valueOf(pengembalian.getTotalDendaRusak()), String.valueOf(pengembalian.getTotalTelat()),
                    String.valueOf(pengembalian.getTotalBayar()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());
    }

    public static void displayDetailPengembalian(Pengembalian noPengembalian) {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();
        String idbuku = "", idCD = "";

        String[] header = { "No. Pengembalian", "Judul Buku", "Judul CD", "Jumlah" };
        int[] columnLengths = new int[header.length];

        for (DetailPengembalian detpengembalian : dafDetPengembalian) {
            if (detpengembalian.getNoPengembalian().equals(noPengembalian)) {
                if (detpengembalian.getIDBuku() != null) {
                    idbuku = detpengembalian.getIDBuku().getJudul();
                } else if (detpengembalian.getIDCD() != null) {
                    idCD = detpengembalian.getIDCD().getJudul();
                }
            }
            String[] data = { String.valueOf(detpengembalian.getNoPengembalian()),
                    idbuku, idCD, String.valueOf(detpengembalian.getJumlah()) };

            // Memperbarui panjang maksimum untuk setiap kolom
            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Detail Pengembalian";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (DetailPengembalian detpengembalian : dafDetPengembalian) {
            if (detpengembalian.getNoPengembalian().equals(noPengembalian)) {
                if (detpengembalian.getIDBuku() != null) {
                    idbuku = detpengembalian.getIDBuku().getJudul();
                } else if (detpengembalian.getIDCD() != null) {
                    idCD = detpengembalian.getIDCD().getJudul();
                }
            }
            String[] data = { String.valueOf(detpengembalian.getNoPengembalian()),
                    idbuku, idCD, String.valueOf(detpengembalian.getJumlah()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());
    }

    public static void displayDetailPembayaran(Pengembalian noKembali) {
        StringBuilder separatorTop = new StringBuilder();
        StringBuilder teksJudul = new StringBuilder();
        String[] header = { "No. Pengembalian", "Judul Buku", "Judul CD", "Jenis Denda", "Biaya Denda",
                "Jumlah Bayar" };
        int[] columnLengths = new int[header.length];
        String idbuku = "", idcd = "";

        for (DetailPembayaran detpembayaran : dafDetPembayaran) {
            if (detpembayaran.getNoPengembalian().equals(noKembali)) {
                if (detpembayaran.getIDBuku() != null) {
                    idbuku = detpembayaran.getIDBuku().getJudul();
                } else if (detpembayaran.getIDCD() != null) {
                    idcd = detpembayaran.getIDCD().getJudul();
                }
            }

            String[] data = { String.valueOf(detpembayaran.getNoPengembalian()), idbuku, idcd,
                    String.valueOf(detpembayaran.getJenisDenda()),
                    String.valueOf(detpembayaran.getJumlah_denda()),
                    String.valueOf(detpembayaran.getJumlah()) };

            // Memperbarui panjang maksimum untuk setiap kolom
            for (int i = 0; i < data.length; i++) {
                columnLengths[i] = Math.max(columnLengths[i], data[i].length());
                if (columnLengths[i] < header[i].length()) {
                    columnLengths[i] = header[i].length();
                }
            }
        }

        int totalLength = 0;
        for (int length : columnLengths) {
            totalLength += length;
        }
        totalLength += 3 * columnLengths.length + 1;

        String judul = "Detail Pembayaran";
        int judulLength = totalLength - judul.length() - 2;

        for (int i = 0; i < (judulLength / 2); i++) {
            teksJudul.append(" ");
        }

        for (int i = 0; i < totalLength; i++) {
            separatorTop.append("=");
        }

        System.out.println(separatorTop.toString());

        System.out.println("|" + teksJudul.toString() + judul + teksJudul.toString() + "|");

        System.out.println(separatorTop.toString());

        for (int i = 0; i < header.length; i++) {
            System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", header[i]));
        }
        System.out.println("|");

        System.out.println(separatorTop.toString());

        for (DetailPembayaran detpembayaran : dafDetPembayaran) {
            if (detpembayaran.getNoPengembalian().equals(noKembali)) {
                if (detpembayaran.getIDBuku() != null) {
                    idbuku = detpembayaran.getIDBuku().getJudul();
                } else if (detpembayaran.getIDCD() != null) {
                    idcd = detpembayaran.getIDCD().getJudul();
                }
            }

            String[] data = { String.valueOf(detpembayaran.getNoPengembalian()), idbuku, idcd,
                    String.valueOf(detpembayaran.getJenisDenda()),
                    String.valueOf(detpembayaran.getJumlah_denda()),
                    String.valueOf(detpembayaran.getJumlah()) };

            for (int i = 0; i < data.length; i++) {
                System.out.print("| " + String.format("%-" + (columnLengths[i] + 1) + "s", data[i]));
            }
            System.out.println("|");
        }

        System.out.println(separatorTop.toString());
    }

    public static void menuutamapustakawan() {
        boolean sap = true;

        while (sap) {
            System.out.println("Menu :");
            System.out.println("1. Update Book");
            System.out.println("2. Update CD");
            System.out.println("3. Register Peminjam");
            System.out.println("4. Add Peminjaman");
            System.out.println("5. View List Peminjaman");
            System.out.println("0. Exit");
            int menu = getInputInt("Choose Menu [0-5] : ");

            switch (menu) {
                case 1:
                    menubukupustakawan();
                    break;
                case 2:
                    menucdpustakawan();
                    break;
                case 3: {
                    boolean sup = true;
                    while (sup) {
                        System.out.println("Make a Loan : ");
                        System.out.println("1. Mahasiswa");
                        System.out.println("2. Dosen/Staff");
                        System.out.println("View List of Borrowers : ");
                        System.out.println("3. List Mahasiswa");
                        System.out.println("4. List Dosen/Staff");
                        System.out.println("0. Exit");
                        int pilihan = getInputInt("Your Choice [0-4]  ");
                        switch (pilihan) {
                            case 1:
                                createUser(UserType.MAHASISWA);
                                break;
                            case 2:
                                createUser(UserType.DOSEN);
                                break;
                            case 3:
                                displayMahasiswa();
                                break;
                            case 4:
                                displayDosen_Staff();
                                break;
                            case 0:
                                sup = false;
                                break;
                            default:
                                System.out.println("Invalid Menu.");
                        }
                        // break;
                    }
                    break;
                }
                case 4:
                    menutambahpeminjaman(false);
                    break;
                case 5:
                    displayPeminjaman();
                    break;
                case 0:
                    sap = false;
                    break;
                default:
                    System.out.println("Invalid Menu.");
            }
        }
        menulogin();
    }

    public static void menuapprovepinjaman() {
        boolean isRunning = true;
        while (isRunning) {
            displayPeminjaman();
            String input = getInput("Enter the loan number you want to approve OR Press 0 to previous. ",
                    false, false, false);
            if (input.equals("0")) {
                isRunning = false;
            } else {
                try {
                    int choice = Integer.parseInt(input);
                    if (choice > 0 && choice <= dafPeminjaman.size()) {
                        Peminjaman peminjaman = dafPeminjaman.get(choice - 1);
                        peminjaman.setStatus(Status.APPROVED);
                        System.out.println("Loans by number : " + peminjaman.getNoPinjam() + " telah diapprove.");
                    } else {
                        System.out.println("Invalid Np. Peminjaman");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input");
                }
            }
        }
    }

    public static void menuutamauser() {
        boolean isRunning = true;
        String mau;
        while (isRunning) {
            System.out.println("Menu:");
            System.out.println("1. View Book List");
            System.out.println("2. View CD List");
            System.out.println("3. Make a Loan");
            System.out.println("0. Exit");
            int menu = getInputInt("Choose [0-3]: ");

            switch (menu) {
                case 1:
                    displayBuku();
                    mau = getInput("Do you want to borrow a book?? [Y/N] : ", false, true, false);
                    if (mau.equalsIgnoreCase("y"))
                        menutambahpeminjaman(true);
                    else
                        break;
                case 2:
                    displayCD();
                    mau = getInput("Do you want to borrow a CD? [Y/N] : ", false, true, false);
                    if (mau.equalsIgnoreCase("y"))
                        menutambahpeminjaman(true);
                    else
                        break;
                case 3:
                    System.out.println("Make a Loan : ");
                    menutambahpeminjaman(true);
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid Menu");
            }
        }
        
    }

    @SuppressWarnings("unused")
    public static void pengembalianbuku() {
        String nomorPeminjaman = getInput("Input No. Peminjaman : ", false, false, false);
        Pengembalian pengembalian = new Pengembalian();
        Peminjaman peminjaman = cariPeminjaman(nomorPeminjaman);
        Mahasiswa mahasiswa = (Mahasiswa) peminjaman.getNIM();
        Dosen_Staff dosen = (Dosen_Staff) peminjaman.getNIK();

        if (peminjaman != null) {
            System.out.println("Due Date : " + LocalDate.now());

            int selisihHari = hitungSelisihHari(LocalDate.parse(peminjaman.getDueDate()), LocalDate.now());
            dafPengembalian.add(pengembalian);
            if (mahasiswa != null) {
                pengembalian.inputData(peminjaman, mahasiswa, pustaktif, selisihHari);
            } else {
                pengembalian.inputData(peminjaman, dosen, pustaktif, selisihHari);
            }
        } else {
            System.out.println("Nomor peminjaman not found.");
        }
    }

    public static void menubukupustakawan() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Menu:");
            System.out.println("1. Add New Book");
            // System.out.println("2. Update Data Buku");
            // System.out.println("3. Hapus Data Buku");
            System.out.println("2. View Book List");
            System.out.println("0. Exit");
            int menu = getInputInt("Choose [0-2] : ");

            switch (menu) {
                case 1:
                    Buku buku = new Buku();
                    buku.inputData(autokodeBuku(daftarBuku));
                    daftarBuku.add(buku);
                    System.out.println("Book successfully added!");
                    break;
                case 2:
                    displayBuku();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid Menu.");
            }
        }
    }

    static PilihanBukuCD getPilihanBukuCD() {
        System.out.println("Choose : ");
        for (PilihanBukuCD pilihan : PilihanBukuCD.values()) {
            System.out.println((pilihan.ordinal() + 1) + ". " + pilihan);
        }
        int choice = Main.getChoice(PilihanBukuCD.values().length);
        return PilihanBukuCD.values()[choice - 1];
    }

    public static void menutambahpeminjaman(boolean user) {
        String noTrans = autoNoTransaksi("PNJM");
        Peminjaman newPeminjaman = new Peminjaman();
        if (user == true) {
            if (useraktif instanceof Mahasiswa)
                newPeminjaman.inputDataUser(noTrans, (Mahasiswa) useraktif, Status.WAITING);
            else
                newPeminjaman.inputDataUser(noTrans, (Dosen_Staff) useraktif, Status.WAITING);
        } else {
            newPeminjaman.inputData(noTrans, pustaktif, usermhs, userdosen, Status.APPROVED);
        }
        String ulang;
        System.out.println("Input detail peminjaman: ");
        for (int i = 0; i < 2; i++) {
            int choice;
            System.out.println("Enter the type of collection you want to borrow : ");
            PilihanBukuCD pilihan = getPilihanBukuCD();
            if (pilihan == PilihanBukuCD.Buku) {
                System.out.println("Book List :");
                for (int j = 0; j < daftarBuku.size(); j++) {
                    System.out.println((j + 1) + ". " + daftarBuku.get(j).getIDBuku() + " - "
                            + daftarBuku.get(j).getJudul());
                }

                choice = Main.getChoice(daftarBuku.size());
                Buku IDBuku = daftarBuku.get(choice - 1);

                DetailPeminjaman newDetailPeminjaman = new DetailPeminjaman();
                newDetailPeminjaman.inputData(newPeminjaman, IDBuku);
                dafDetPeminjaman.add(newDetailPeminjaman);

            } else if (pilihan == PilihanBukuCD.CD) {
                System.out.println("CD List :");
                for (int j = 0; j < listcd.size(); j++) {
                    System.out.println((j + 1) + ". " + listcd.get(j).getIDCD() + " - "
                            + listcd.get(j).getJudul());
                }

                choice = Main.getChoice(listcd.size());
                CD IDCD = listcd.get(choice - 1);

                DetailPeminjaman newDetailPeminjaman = new DetailPeminjaman();
                newDetailPeminjaman.inputData(newPeminjaman, IDCD);
                dafDetPeminjaman.add(newDetailPeminjaman);
            } else {
                System.out.println("Invalid Choice.");
            }

            ulang = getInput("Add Book / CD again? [Y/N] : ", false, true, false);
            if (ulang.equalsIgnoreCase("n"))
                break;
        }
        dafPeminjaman.add(newPeminjaman);
    }

    public static Object login() {
        System.out.println("WELCOME TO PERPUSTAKAN\n\t\t\tCINEMA!!!");
        System.out.println("Please select your user type to log in :");
        System.out.println("1. Pustakawan");
        System.out.println("2. Mahasiswa");
        System.out.println("3. Dosen/Staff");
        System.out.println("9. Signup");
        System.out.println("0. Exit");

        int choice = getInputInt("Masukkan pilihan anda: ");

        switch (choice) {
            case 1:
                System.out.println("=== Login sebagai Pustakawan ===");
                break;
            case 2:
                System.out.println("=== Login sebagai Mahasiswa ===");
                break;
            case 3:
                System.out.println("=== Login sebagai Dosen/Staff ===");
                break;
            case 9:
                signup();
                break;
            case 0:
                break;
        }

        System.out.print("Kode: ");
        String kode = myScanner.nextLine();
        System.out.print("Password: ");
        String password = myScanner.nextLine();

        switch (choice) {
            case 1:
                for (Pustakawan pustakawan : listpustakawan) {
                    if (pustakawan.getIDPustakawan().equals(kode) && pustakawan.getPassword().equals(password)) {
                        System.out.println("Login berhasil!");
                        return pustakawan;
                    }
                }
                break;
            case 2:
                for (Mahasiswa mahasiswa : usermhs) {
                    if (mahasiswa.getNIM().equals(kode) && mahasiswa.getPassword().equals(password)) {
                        System.out.println("Login berhasil!");
                        return mahasiswa;
                    }
                }
                break;
            case 3:
                for (Dosen_Staff dosen : userdosen) {
                    if (dosen.getNIK().equals(kode) && dosen.getPassword().equals(password)) {
                        System.out.println("Login berhasil!");
                        return dosen;
                    }
                }
                break;
            default:
                System.out.println("Jenis user tidak valid.");
                return login();
        }

        System.out.println("Kode atau password salah.");
        return login();
    }

    public static void menucdpustakawan() {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Menu:");
            System.out.println("1. Add new CD");
            System.out.println("2. View CD List");
            System.out.println("0. Exit");
            int menu = getInputInt("Choose [0-2] : ");

            switch (menu) {
                case 1:
                    CD cd = new CD();
                    cd.inputData(autokodeCD(listcd));
                    listcd.add(cd);
                    System.out.println("CD successfully added!");
                    break;
                case 2:
                    displayCD();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid Menu.");
            }
        }
    }

    public static void main(String[] args) {
        if (signup() == true) {
            menulogin();
        }
    }

    public static void menulogin() {
        Object pengguna = login();
        if (pengguna instanceof Pustakawan) {
            pustaktif = (Pustakawan) pengguna;
            menuutamapustakawan();
        } else if (pengguna instanceof Mahasiswa) {
            useraktif = (Mahasiswa) pengguna;
            menuutamauser();
        } else if (pengguna instanceof Dosen_Staff) {
            useraktif = (Dosen_Staff) pengguna;
            menuutamauser();
        }
    }

    public static Peminjaman cariPeminjaman(String nomorPeminjaman) {
        for (Peminjaman peminjaman : dafPeminjaman) {
            if (peminjaman.getNoPinjam() == nomorPeminjaman) {
                return peminjaman;
            }
        }
        return null;
    }

    public static int hitungSelisihHari(LocalDate dueDate, LocalDate currentDate) {
        return currentDate.until(dueDate).getDays();
    }

    public static int hitungTotalDenda(long selisihHari) {
        // Denda per hari: Rp 5000
        return (int) (selisihHari * 5000);
    }

    private static boolean signup() {
        while (true) {
            System.out.println("WELCOME TO PERPUSTAKAN\n\t\t\tCINEMA!!!");
            System.out.println("Please select the type of user you want to create :");
            System.out.println("1. Pustakawan");
            System.out.println("0. Exit");

            int choice = getInputInt("Choice [0-1] : ");

            switch (choice) {
                case 1:
                    createUser(UserType.PUSTAKAWAN);
                    return true;
                case 0:
                    System.out.println("Thankyou! See yaaa!");
                    return false;
                default:
                    System.out.println("Invalid choice. Try again!");
                    return signup();
            }
        }
    }

    private static void createUser(UserType userType) {
        switch (userType) {
            case PUSTAKAWAN:
                Pustakawan pustakawan = new Pustakawan();
                pustakawan.inputData(autokodePust(listpustakawan));
                listpustakawan.add(pustakawan);
                System.out.println("Pustakawan successfully created!");
                break;
            case MAHASISWA:
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.inputData();
                usermhs.add(mahasiswa);
                System.out.println("Mahasiswa successfully created!");
                break;
            case DOSEN:
                Dosen_Staff dosen = new Dosen_Staff();
                dosen.inputData();
                userdosen.add(dosen);
                System.out.println("Dosen successfully created!");
                break;
            default:
                System.out.println("Invalid User type.");
        }
    }
}

enum UserType {
    MAHASISWA,
    DOSEN,
    PUSTAKAWAN
}

enum PilihanBukuCD {
    Buku,
    CD
}

enum Status {
    WAITING,
    APPROVED
}