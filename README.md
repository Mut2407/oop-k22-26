# Quản lý cửa hàng dụng cụ thể thao

Đồ án Java console, gồm **23 class và 1 interface**, tổ chức theo các tài liệu trong `artifacts/`. Dùng mảng đối tượng, constructor, get/set, `this`, kế thừa, đa hình, abstract, interface và static. Các hàm trong `src` có phần thân không quá 10 dòng không trống, không ghép nhiều lệnh để rút dòng.

## Chạy trên Windows

Cài **JDK 17 trở lên**, kiểm tra `java -version` và `javac -version`. Mở terminal tại thư mục repo:

```powershell
.\run.bat
```

Hoặc biên dịch và chạy trực tiếp:

```powershell
javac -encoding UTF-8 -d out -sourcepath src src/Main.java
java -Dfile.encoding=UTF-8 -cp out Main
```

Trên Linux/macOS: `sh run.sh`. Chương trình không cần thư viện bên ngoài.

Chạy bình thường sẽ đọc ngay dữ liệu mẫu trong `data/`: **3 loại, 9 sản phẩm thuộc ba lớp con, 10 khách hàng, 5 nhân viên, 5 nhà cung cấp, 5 hóa đơn và 5 phiếu nhập**. Các hóa đơn có 10 dòng chi tiết; các phiếu nhập có 11 dòng chi tiết.

Tổng tiền bán mẫu là **8.950.000 đồng**, tổng tiền nhập là **44.400.000 đồng**. Tồn hiện tại của từng sản phẩm khớp tổng nhập trừ tổng bán; ví dụ `SP01 = 10`, `SP03 = 8`. Có thể vào từng menu và chọn `1. Xem danh sách`, hoặc chọn `9. Thong ke tong tien` ở menu chính.

Muốn dùng thư mục dữ liệu khác: `.\run.bat ten-thu-muc`. Thao tác lưu và thoát sẽ cập nhật các file trong thư mục đã chọn. Có thể sửa file bằng trình soạn thảo khi chương trình đã đóng, rồi chạy lại để nạp thay đổi.

## Các chức năng

| Nhóm | Chức năng |
|---|---|
| Sản phẩm | Xem, thêm một/nhiều, sửa, xóa; tìm chính xác theo mã, tìm gần đúng mã/tên, lọc loại, lọc khoảng giá |
| Loại, khách hàng, nhân viên, nhà cung cấp | Xem, thêm một/nhiều, sửa, xóa, tìm mã/tên; nhóm người còn tìm theo số điện thoại |
| Hóa đơn, phiếu nhập | Xem, thêm, sửa, xóa, tìm theo mã chứng từ/mã đối tác, xem chi tiết |
| Chi tiết chứng từ | Thêm, sửa số lượng/đơn giá, xóa, xem; mỗi sản phẩm có một dòng |
| Kho | Tra tồn; cập nhật khi thêm/sửa/xóa chứng từ; chặn thiếu hàng, âm tồn và tràn số nguyên |
| Thống kê | Tổng tiền bán và tổng tiền nhập, toàn bộ hoặc trong khoảng ngày gồm cả hai đầu |
| File | Đọc lúc khởi động; lưu theo menu hoặc khi thoát; giữ tiếng Việt và các liên kết bằng mã |

Luồng thao tác lần đầu: tạo loại → khách/nhân viên/nhà cung cấp → sản phẩm → phiếu nhập → hóa đơn. Sản phẩm mới có tồn bằng 0, nhập hàng bằng phiếu nhập. Khi sửa sản phẩm phải giữ nguyên loại lớp Java (giày/quần áo/dụng cụ); có thể đổi loại phân loại `LoaiSP`.

Trong màn hình soạn chứng từ, `0` lưu bản soạn; chứng từ phải có ít nhất một dòng hợp lệ. Nhập dữ liệu sai sẽ dừng thao tác đang làm và quay về menu chính. Những phần tử đã thêm thành công trước đó trong thao tác thêm nhiều vẫn được giữ. Sửa chứng từ làm trên bản sao, nên bản đang lưu không đổi khi nhập sai hoặc không đủ tồn.

## Cấu trúc và trách nhiệm

| Vị trí | Nội dung |
|---|---|
| `src/models` | 12 thực thể và `Kho` theo khung repo |
| `src/services` | 7 danh sách, `QuanLyCuaHang`, `LuuTruFileText` và interface `IQuanLyDanhSach<T>` |
| `src/Main.java` | Tạo lớp quản lý và gọi `chay()`; `main` có 3 dòng lệnh |
| `artifacts/Sport_Store.drawio.xml` | Sơ đồ được đồng bộ với 23 class thực tế |
| `data/*.txt` | Chín file dữ liệu đang dùng; giữ dữ liệu khách hàng, nhân viên và nhà cung cấp đã nhập |
| `tests/KiemThu.java` | Chương trình kiểm thử riêng, không thuộc 23 class ứng dụng |

- Các danh sách dùng `T[]` cụ thể và `soLuong`; mở rộng bằng `Arrays.copyOf`, xóa bằng vòng lặp dồn mảng. Một `SanPham[]` chứa cả ba lớp con và gọi `hienThiThongTin()` bằng đa hình.
- Interface chỉ quy định bốn thao tác CRUD/tìm kiếm. Menu thêm loại sản phẩm gọi qua biến `IQuanLyDanhSach<LoaiSP>`; các menu còn lại dùng kiểu cụ thể để dễ đọc.
- Giao dịch từ menu gọi `QuanLyCuaHang.them/sua/xoaHoaDon` hoặc `...PhieuNhap`, để phối hợp danh sách với kho. CRUD trực tiếp ở danh sách chứng từ là thao tác mảng, được dùng riêng khi khôi phục file.
- `Kho` dùng chung danh sách sản phẩm; tồn chỉ lưu ở `SanPham`. Kho tính tất cả chênh lệch và kiểm tra hết trước khi thay đổi tồn. Hóa đơn có chênh lệch `số lượng cũ - số lượng mới`; phiếu nhập dùng chiều ngược lại.
- Sửa loại/người/sản phẩm cập nhật đối tượng hiện có để giữ tham chiếu. Mã định danh không có setter. Không cho xóa các đối tượng còn được tham chiếu, hoặc sản phẩm còn tồn.
- `BigDecimal` cho tiền và `LocalDate` cho ngày theo tài liệu `To_Chuc_Class_Sport_Store.txt`. Chỉ dùng các phép cộng, nhân, so sánh và đọc ngày thông thường; không tự viết một hệ thống tính tiền/ngày khác. Đơn giá chi tiết độc lập với giá sản phẩm hiện tại.
- `static final` dùng cho sức chứa mặc định/tên file; các hàm kiểm tra chuỗi dùng `static`. Hai hàm `thongKeTongTien()` và `thongKeTongTien(tu, den)` minh họa overload.

Khung `DanhSachKho` rỗng được bỏ vì không có thực thể kho cần quản lý thành danh sách. `LuuTruFile` rỗng được thay bằng `LuuTruFileText` cho đúng tên trong tài liệu. Các lớp hỗ trợ bổ sung ở trang cuối sơ đồ cũ được bỏ; mọi xử lý nằm trong 23 class đã thống nhất.

## Lưu và đọc file text

Chương trình đọc và ghi **chín file riêng** trong `data/`. Mỗi file bắt đầu bằng nhãn ở dòng đầu; các dòng sau là bản ghi phân cách bằng `|`. Dùng UTF-8, ngày `yyyy-MM-dd`, số tiền không có dấu phân cách hàng nghìn; không nhập dấu `|` hay xuống dòng trong một trường. Chấp nhận dòng trống và dấu BOM UTF-8 ở đầu file.

| File | Nhãn dòng đầu | Các trường trên một dòng dữ liệu |
|---|---|---|
| `loaisp.txt` | `LOAI_SP` | Mã loại, tên loại, mô tả |
| `khachhang.txt` | `KHACH_HANG` | Mã khách, họ tên, số điện thoại, địa chỉ |
| `nhanvien.txt` | `NHAN_VIEN` | Mã nhân viên, họ tên, số điện thoại, ca làm |
| `nhacungcap.txt` | `NHA_CUNG_CAP` | Mã nhà cung cấp, tên, số điện thoại, địa chỉ |
| `sanpham.txt` | `SAN_PHAM` | Nhãn lớp, mã, tên, thương hiệu, giá bán, tồn hiện tại, mã loại, thuộc tính riêng |
| `hoadon.txt` | `HOA_DON` | Mã hóa đơn, ngày lập, mã khách, mã nhân viên |
| `chitiethoadon.txt` | `CHI_TIET_HOA_DON` | Mã hóa đơn, mã sản phẩm, số lượng, đơn giá bán |
| `phieunhap.txt` | `PHIEU_NHAP` | Mã phiếu nhập, ngày lập, mã nhà cung cấp, mã nhân viên |
| `chitietphieunhap.txt` | `CHI_TIET_PHIEU_NHAP` | Mã phiếu nhập, mã sản phẩm, số lượng, đơn giá nhập |

Trong `sanpham.txt`, phần thuộc tính riêng phụ thuộc nhãn lớp:

- `GIAY`: cỡ giày, màu sắc, chất liệu, loại đế giày.
- `QUAN_AO`: cỡ quần áo, màu sắc, chất liệu.
- `DUNG_CU`: môn thể thao, vật liệu, khối lượng kg.

Ví dụ một hóa đơn nằm trong hai file:

```text
# hoadon.txt
HOA_DON
HD000001|2026-11-07|KH01|NV01

# chitiethoadon.txt
CHI_TIET_HOA_DON
HD000001|SP01|2|350000
HD000001|SP03|1|1200000
```

Các dòng bắt đầu bằng `#` ở ví dụ chỉ chú thích tên file; không ghi chúng vào dữ liệu thật. Tổng hóa đơn trên là 1.900.000 đồng, được tính từ các dòng chi tiết.

Đọc loại và người trước, rồi sản phẩm, chứng từ và chi tiết. Mã tham chiếu phải tồn tại, mỗi chứng từ có ít nhất một dòng chi tiết và không lặp mã sản phẩm trong cùng chứng từ. Mô tả loại và chất liệu giày không được để trống. Tồn hiện tại đã nằm trong dòng sản phẩm nên **đọc file không nhập/bán lại hàng**.

Đọc vào bộ danh sách tạm; chỉ thay bộ đang dùng khi toàn bộ dữ liệu hợp lệ. Khi có lỗi, chương trình báo tên file và dòng tương ứng. Lỗi lúc khởi động sẽ dừng chương trình, không ghi đè dữ liệu.

Khi lưu, chương trình ghi đủ chín file vào thư mục tạm `.luu-tam/`, đóng và đọc kiểm tra lại, rồi sao lưu các file cũ trước khi thay. Nếu có lỗi khi thay file, chương trình cố phục hồi các file đã thay từ bản sao. Các bản sao nằm trong `.luu-tam/*.txt.bak`; chúng thuộc lần lưu trước và không tự được nạp. Đây là cách lưu cho ứng dụng console một tiến trình; không bảo đảm thay cả chín file cùng lúc khi mất điện hoặc tiến trình bị buộc dừng.

Các file riêng được ưu tiên kể cả khi còn `cuahang.txt` của phiên bản cũ. Nếu chưa có file riêng nào, chương trình vẫn đọc định dạng `cuahang.txt` cũ (hoặc phục hồi `cuahang.bak` khi thiếu file chính); lần lưu sau sẽ tạo chín file riêng. Thư mục mới chưa có file được hiểu là bộ dữ liệu rỗng.

Trường thứ tư trong `khachhang.txt` hiện có địa chỉ email do dữ liệu đã nhập; chương trình giữ nguyên trong thuộc tính `diaChi`. File `khachhang copy.txt` là bản sao của người dùng, không được nạp thêm.

## Kiểm thử

```powershell
.\test.bat
```

Hoặc:

```text
javac -encoding UTF-8 -Xlint:all -d out -sourcepath src src/Main.java tests/KiemThu.java
java -cp out KiemThu
```

Kiểm thử dùng Java thuần, tự báo lỗi bằng `AssertionError`; không cần JUnit. Bao gồm mở rộng/dồn mảng, CRUD của bảy danh sách, chi tiết và sao chép, đơn giá lịch sử, thống kê theo ngày, thêm/sửa/xóa chứng từ, thiếu tồn ở dòng sau, tham chiếu trước khi xóa, ghi/đọc UTF-8, đọc lại không đổi tồn, file sai/truncated, đọc định dạng cũ, ưu tiên file riêng khi sửa bằng trình soạn thảo, kiểm tra liên kết chi tiết, dữ liệu mẫu và tồn kho khớp nhập trừ bán. Dữ liệu kiểm thử tạo trong `out/`, không sửa `data/`.
