package services;

import models.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.DateTimeException;

// File text UTF-8; doc theo thu tu de noi lai cac tham chieu bang ma.
public class LuuTruFileText {
    private String thuMuc;
    private int soDong;
    public static final String TEN_FILE = "cuahang.txt";

    public LuuTruFileText() {
        this("data");
    }

    public LuuTruFileText(String thuMuc) {
        if (thuMuc == null || thuMuc.trim().isEmpty())
            throw new IllegalArgumentException("Thu muc khong hop le.");
        this.thuMuc = thuMuc;
    }

    public void docDuLieu(QuanLyCuaHang quanLy) throws IOException {
        File tep = new File(thuMuc, TEN_FILE);
        phucHoiNeuCan(tep);
        QuanLyCuaHang tam = new QuanLyCuaHang(thuMuc);
        if (tep.exists())
            docTuFile(tep, tam);
        else
            docKhachHangCu(tam);
        quanLy.thayDuLieu(tam);
    }

    private void phucHoiNeuCan(File tep) throws IOException {
        File banSao = new File(thuMuc, "cuahang.bak");
        if (!tep.exists() && banSao.exists() && !banSao.renameTo(tep))
            throw new IOException("Khong phuc hoi duoc cuahang.bak.");
    }

    private void docTuFile(File tep, QuanLyCuaHang tam) throws IOException {
        soDong = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(tep), "UTF-8"));
        try {
            docNoiDung(in, tam);
        } catch (IllegalArgumentException | DateTimeException e) {
            throw new IOException(tep.getName() + ": dong " + soDong + ": " + e.getMessage(), e);
        } finally {
            in.close();
        }
    }

    private void docNoiDung(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        docDauFile(in);
        docDanhMucVaNguoi(in, tam);
        docSanPham(in, tam);
        docHoaDon(in, tam);
        docPhieuNhap(in, tam);
        yeuCau(docDong(in, 1)[0].equals("KET_THUC"), "Thieu dong ket thuc.");
        yeuCau(in.readLine() == null, "Du du lieu cuoi file.");
    }

    private void docDauFile(BufferedReader in) throws IOException {
        String[] f = docDong(in, 2);
        yeuCau(f[0].equals("SPORT_STORE") && f[1].equals("1"), "Sai dau file/phien ban.");
    }

    private void docDanhMucVaNguoi(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        docLoaiSP(in, tam);
        docKhachHang(in, tam);
        docNhanVien(in, tam);
        docNhaCungCap(in, tam);
    }

    private String[] docDong(BufferedReader in, int soTruong) throws IOException {
        String dong = in.readLine();
        soDong++;
        yeuCau(dong != null, "File bi cat ngan.");
        String[] f = dong.split("\\|", -1);
        yeuCau(soTruong == 0 || f.length == soTruong, "Sai so truong.");
        return f;
    }

    private int docSoLuong(BufferedReader in, String nhan) throws IOException {
        String[] f = docDong(in, 2);
        yeuCau(f[0].equals(nhan), "Sai muc " + nhan);
        int n = Integer.parseInt(f[1]);
        yeuCau(n >= 0 && n <= 100000, "So phan tu ngoai gioi han.");
        return n;
    }

    private void yeuCau(boolean dung, String thongBao) throws IOException {
        if (!dung)
            throw new IOException("Dong " + soDong + ": " + thongBao);
    }

    private void docLoaiSP(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        int n = docSoLuong(in, "LOAI");
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 3);
            LoaiSP x = new LoaiSP(f[0], f[1], f[2]);
            yeuCau(tam.getDanhSachLoaiSP().them(x), "Trung ma/du lieu LoaiSP khong hop le.");
        }
    }

    private void docKhachHang(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        int n = docSoLuong(in, "KHACHHANG");
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 4);
            KhachHang x = new KhachHang(f[0], f[1], f[2], f[3]);
            yeuCau(tam.getDanhSachKhachHang().them(x), "Trung ma/du lieu KhachHang khong hop le.");
        }
    }

    private void docNhanVien(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        int n = docSoLuong(in, "NHANVIEN");
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 4);
            NhanVien x = new NhanVien(f[0], f[1], f[2], f[3]);
            yeuCau(tam.getDanhSachNhanVien().them(x), "Trung ma/du lieu NhanVien khong hop le.");
        }
    }

    private void docNhaCungCap(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        int n = docSoLuong(in, "NHACUNGCAP");
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 4);
            NhaCungCap x = new NhaCungCap(f[0], f[1], f[2], f[3]);
            yeuCau(tam.getDanhSachNhaCungCap().them(x), "Trung ma/du lieu NhaCungCap khong hop le.");
        }
    }

    private void docSanPham(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        int n = docSoLuong(in, "SANPHAM");
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 0);
            SanPham sp = taoSanPham(f, tam);
            sp.capNhatTon(Integer.parseInt(f[5]));
            yeuCau(tam.getDanhSachSanPham().them(sp), "Trung ma/du lieu SP khong hop le.");
        }
    }

    private SanPham taoSanPham(String[] f, QuanLyCuaHang tam) throws IOException {
        yeuCau(f.length >= 7, "Thieu truong san pham.");
        LoaiSP loai = tam.getDanhSachLoaiSP().timTheoMa(f[6]);
        if (f[0].equals("GIAY"))
            return docGiay(f, loai);
        if (f[0].equals("QUANAO"))
            return docQuanAo(f, loai);
        yeuCau(f[0].equals("DUNGCU"), "Sai nhan lop san pham.");
        return docDungCu(f, loai);
    }

    private GiayTheThao docGiay(String[] f, LoaiSP loai) throws IOException {
        yeuCau(f.length == 11, "Sai so truong GIAY.");
        return new GiayTheThao(f[1], f[2], f[3], new BigDecimal(f[4]), loai,
                Double.parseDouble(f[7]), f[8], f[9], f[10]);
    }

    private QuanAoTheThao docQuanAo(String[] f, LoaiSP loai) throws IOException {
        yeuCau(f.length == 10, "Sai so truong QUANAO.");
        return new QuanAoTheThao(f[1], f[2], f[3], new BigDecimal(f[4]), loai, f[7], f[8], f[9]);
    }

    private DungCuTheThao docDungCu(String[] f, LoaiSP loai) throws IOException {
        yeuCau(f.length == 10, "Sai so truong DUNGCU.");
        return new DungCuTheThao(f[1], f[2], f[3], new BigDecimal(f[4]), loai,
                f[7], f[8], Double.parseDouble(f[9]));
    }

    private void docHoaDon(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        int n = docSoLuong(in, "HOADON");
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 5);
            HoaDon phieu = new HoaDon(f[0], LocalDate.parse(f[1]),
                    tam.getDanhSachKhachHang().timTheoMa(f[2]), tam.getDanhSachNhanVien().timTheoMa(f[3]));
            docChiTiet(in, phieu, demChiTiet(f[4]), tam);
            yeuCau(tam.getDanhSachHoaDon().them(phieu), "Chung tu rong/trung ma.");
        }
    }

    private void docChiTiet(BufferedReader in, HoaDon phieu, int n, QuanLyCuaHang tam) throws IOException {
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 3);
            SanPham sp = tam.getDanhSachSanPham().timTheoMa(f[0]);
            ChiTietHoaDon ct = new ChiTietHoaDon(sp, Integer.parseInt(f[1]), new BigDecimal(f[2]));
            yeuCau(phieu.themChiTiet(ct), "Chi tiet trung SP/khong hop le.");
        }
    }

    private void docPhieuNhap(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        int n = docSoLuong(in, "PHIEUNHAP");
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 5);
            PhieuNhap phieu = new PhieuNhap(f[0], LocalDate.parse(f[1]),
                    tam.getDanhSachNhaCungCap().timTheoMa(f[2]), tam.getDanhSachNhanVien().timTheoMa(f[3]));
            docChiTiet(in, phieu, demChiTiet(f[4]), tam);
            yeuCau(tam.getDanhSachPhieuNhap().them(phieu), "Chung tu rong/trung ma.");
        }
    }

    private void docChiTiet(BufferedReader in, PhieuNhap phieu, int n, QuanLyCuaHang tam) throws IOException {
        for (int i = 0; i < n; i++) {
            String[] f = docDong(in, 3);
            SanPham sp = tam.getDanhSachSanPham().timTheoMa(f[0]);
            ChiTietPhieuNhap ct = new ChiTietPhieuNhap(sp, Integer.parseInt(f[1]), new BigDecimal(f[2]));
            yeuCau(phieu.themChiTiet(ct), "Chi tiet trung SP/khong hop le.");
        }
    }

    private int demChiTiet(String so) throws IOException {
        int n = Integer.parseInt(so);
        yeuCau(n > 0 && n <= 100000, "So dong chi tiet khong hop le.");
        return n;
    }

    private void docKhachHangCu(QuanLyCuaHang tam) throws IOException {
        File tep = new File(thuMuc, "khachhang.txt");
        if (!tep.exists())
            return;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(tep), "UTF-8"));
        try {
            napKhachHangCu(in, tam);
        } finally {
            in.close();
        }
    }

    private void napKhachHangCu(BufferedReader in, QuanLyCuaHang tam) throws IOException {
        soDong = 0;
        yeuCau(docDong(in, 1)[0].equals("KHACH_HANG"), "Sai dau khachhang.txt.");
        String dong = in.readLine();
        while (dong != null) {
            soDong++;
            if (!dong.trim().isEmpty())
                napMotKhachHangCu(dong, tam);
            dong = in.readLine();
        }
    }

    private void napMotKhachHangCu(String dong, QuanLyCuaHang tam) throws IOException {
        String[] f = dong.split("\\|", -1);
        yeuCau(f.length == 4, "Sai so truong khachhang.txt.");
        try {
            yeuCau(tam.getDanhSachKhachHang().them(new KhachHang(f[0], f[1], f[2], f[3])), "Ma KH trung.");
        } catch (IllegalArgumentException e) {
            throw new IOException("khachhang.txt dong " + soDong + ": " + e.getMessage(), e);
        }
    }

    public void ghiDuLieu(QuanLyCuaHang quanLy) throws IOException {
        File thuMucFile = new File(thuMuc);
        if (!thuMucFile.isDirectory() && !thuMucFile.mkdirs())
            throw new IOException("Khong tao duoc thu muc du lieu.");
        File tam = new File(thuMuc, "cuahang.tmp");
        ghiRaFile(tam, quanLy);
        docTuFile(tam, new QuanLyCuaHang(thuMuc));
        thayFile(tam);
    }

    private void ghiRaFile(File tep, QuanLyCuaHang quanLy) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tep), "UTF-8"));
        try {
            ghiNoiDung(out, quanLy);
        } finally {
            out.close();
        }
    }

    private void ghiNoiDung(BufferedWriter out, QuanLyCuaHang q) throws IOException {
        ghiDong(out, new String[] {"SPORT_STORE", "1"});
        ghiLoaiSP(out, q);
        ghiKhachHang(out, q);
        ghiNhanVien(out, q);
        ghiNhaCungCap(out, q);
        ghiSanPham(out, q);
        ghiHoaDon(out, q);
        ghiPhieuNhap(out, q);
        ghiDong(out, new String[] {"KET_THUC"});
    }

    private void ghiDong(BufferedWriter out, String[] f) throws IOException {
        for (int i = 0; i < f.length; i++)
            if (f[i] == null || f[i].contains("|") || f[i].contains("\n") || f[i].contains("\r"))
                throw new IOException("Truong du lieu chua ky tu phan cach.");
        out.write(String.join("|", f));
        out.newLine();
    }

    private void ghiSoLuong(BufferedWriter out, String nhan, int n) throws IOException {
        ghiDong(out, new String[] {nhan, String.valueOf(n)});
    }

    private void ghiLoaiSP(BufferedWriter out, QuanLyCuaHang q) throws IOException {
        LoaiSP[] ds = q.getDanhSachLoaiSP().getTatCa();
        ghiSoLuong(out, "LOAI", ds.length);
        for (int i = 0; i < ds.length; i++) {
            LoaiSP x = ds[i];
            ghiDong(out, new String[] {x.getMaLoaiSP(), x.getTenLoaiSP(), x.getMoTa()});
        }
    }

    private void ghiKhachHang(BufferedWriter out, QuanLyCuaHang q) throws IOException {
        KhachHang[] ds = q.getDanhSachKhachHang().getTatCa();
        ghiSoLuong(out, "KHACHHANG", ds.length);
        for (int i = 0; i < ds.length; i++) {
            KhachHang x = ds[i];
            ghiDong(out, new String[] {x.getMaKhachHang(), x.getHoTen(), x.getSoDienThoai(), x.getDiaChi()});
        }
    }

    private void ghiNhanVien(BufferedWriter out, QuanLyCuaHang q) throws IOException {
        NhanVien[] ds = q.getDanhSachNhanVien().getTatCa();
        ghiSoLuong(out, "NHANVIEN", ds.length);
        for (int i = 0; i < ds.length; i++) {
            NhanVien x = ds[i];
            ghiDong(out, new String[] {x.getMaNhanVien(), x.getHoTen(), x.getSoDienThoai(), x.getCaLamViec()});
        }
    }

    private void ghiNhaCungCap(BufferedWriter out, QuanLyCuaHang q) throws IOException {
        NhaCungCap[] ds = q.getDanhSachNhaCungCap().getTatCa();
        ghiSoLuong(out, "NHACUNGCAP", ds.length);
        for (int i = 0; i < ds.length; i++) {
            NhaCungCap x = ds[i];
            ghiDong(out, new String[] {x.getMaNhaCungCap(), x.getTenNhaCungCap(), x.getSoDienThoai(), x.getDiaChi()});
        }
    }

    private void ghiSanPham(BufferedWriter out, QuanLyCuaHang q) throws IOException {
        SanPham[] ds = q.getDanhSachSanPham().getTatCa();
        ghiSoLuong(out, "SANPHAM", ds.length);
        for (int i = 0; i < ds.length; i++)
            ghiMotSanPham(out, ds[i]);
    }

    private void ghiMotSanPham(BufferedWriter out, SanPham sp) throws IOException {
        if (sp instanceof GiayTheThao)
            ghiGiay(out, (GiayTheThao) sp);
        else if (sp instanceof QuanAoTheThao)
            ghiQuanAo(out, (QuanAoTheThao) sp);
        else if (sp instanceof DungCuTheThao)
            ghiDungCu(out, (DungCuTheThao) sp);
        else
            throw new IOException("Lop SP khong duoc ho tro.");
    }

    private String[] truongChung(SanPham sp, String nhan, int soTruong) {
        String[] f = new String[soTruong];
        f[0] = nhan;
        f[1] = sp.getMaSanPham();
        f[2] = sp.getTenSanPham();
        f[3] = sp.getThuongHieu();
        f[4] = sp.getGiaBan().toPlainString();
        f[5] = String.valueOf(sp.getSoLuongTon());
        f[6] = sp.getLoaiSP().getMaLoaiSP();
        return f;
    }

    private void ghiGiay(BufferedWriter out, GiayTheThao sp) throws IOException {
        String[] f = truongChung(sp, "GIAY", 11);
        f[7] = String.valueOf(sp.getKichCoGiay());
        f[8] = sp.getMauSac();
        f[9] = sp.getChatLieu();
        f[10] = sp.getLoaiDeGiay();
        ghiDong(out, f);
    }

    private void ghiQuanAo(BufferedWriter out, QuanAoTheThao sp) throws IOException {
        String[] f = truongChung(sp, "QUANAO", 10);
        f[7] = sp.getKichCoQuanAo();
        f[8] = sp.getMauSac();
        f[9] = sp.getChatLieu();
        ghiDong(out, f);
    }

    private void ghiDungCu(BufferedWriter out, DungCuTheThao sp) throws IOException {
        String[] f = truongChung(sp, "DUNGCU", 10);
        f[7] = sp.getMonTheThao();
        f[8] = sp.getVatLieu();
        f[9] = String.valueOf(sp.getKhoiLuongKg());
        ghiDong(out, f);
    }

    private void ghiHoaDon(BufferedWriter out, QuanLyCuaHang q) throws IOException {
        HoaDon[] ds = q.getDanhSachHoaDon().getTatCa();
        ghiSoLuong(out, "HOADON", ds.length);
        for (int i = 0; i < ds.length; i++)
            ghiMotHoaDon(out, ds[i]);
    }

    private void ghiMotHoaDon(BufferedWriter out, HoaDon phieu) throws IOException {
        ghiDong(out, new String[] {phieu.getMaHoaDon(), phieu.getNgayLap().toString(),
                phieu.getKhachHang().getMaKhachHang(), phieu.getNhanVien().getMaNhanVien(),
                String.valueOf(phieu.getSoLuongChiTiet())});
        ChiTietHoaDon[] ds = phieu.getChiTiet();
        for (int i = 0; i < ds.length; i++)
            ghiChiTiet(out, ds[i]);
    }

    private void ghiChiTiet(BufferedWriter out, ChiTietHoaDon ct) throws IOException {
        ghiDong(out, new String[] {ct.getSanPham().getMaSanPham(), String.valueOf(ct.getSoLuong()),
                ct.getDonGiaBan().toPlainString()});
    }

    private void ghiPhieuNhap(BufferedWriter out, QuanLyCuaHang q) throws IOException {
        PhieuNhap[] ds = q.getDanhSachPhieuNhap().getTatCa();
        ghiSoLuong(out, "PHIEUNHAP", ds.length);
        for (int i = 0; i < ds.length; i++)
            ghiMotPhieuNhap(out, ds[i]);
    }

    private void ghiMotPhieuNhap(BufferedWriter out, PhieuNhap phieu) throws IOException {
        ghiDong(out, new String[] {phieu.getMaPhieuNhap(), phieu.getNgayLap().toString(),
                phieu.getNhaCungCap().getMaNhaCungCap(), phieu.getNhanVien().getMaNhanVien(),
                String.valueOf(phieu.getSoLuongChiTiet())});
        ChiTietPhieuNhap[] ds = phieu.getChiTiet();
        for (int i = 0; i < ds.length; i++)
            ghiChiTiet(out, ds[i]);
    }

    private void ghiChiTiet(BufferedWriter out, ChiTietPhieuNhap ct) throws IOException {
        ghiDong(out, new String[] {ct.getSanPham().getMaSanPham(), String.valueOf(ct.getSoLuong()),
                ct.getDonGiaNhap().toPlainString()});
    }

    private void thayFile(File tam) throws IOException {
        File chinh = new File(thuMuc, TEN_FILE);
        File banSao = new File(thuMuc, "cuahang.bak");
        saoLuu(chinh, banSao);
        if (!tam.renameTo(chinh)) {
            if (banSao.exists() && !banSao.renameTo(chinh))
                throw new IOException("Luu loi. Du lieu cu con trong cuahang.bak.");
            throw new IOException("Khong thay duoc file chinh; da giu du lieu cu.");
        }
    }

    private void saoLuu(File chinh, File banSao) throws IOException {
        if (!chinh.exists())
            return;
        if (banSao.exists() && !banSao.delete())
            throw new IOException("Khong thay duoc ban sao cu.");
        if (!chinh.renameTo(banSao))
            throw new IOException("Khong sao luu duoc file chinh.");
    }

}
