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

Để thử bộ dữ liệu mẫu có sẵn ba loại sản phẩm, một phiếu nhập và một hóa đơn:

```powershell
.\run.bat data-mau
```

Bộ mẫu có tồn `SP01 = 8`, `SP02 = 5`, `SP03 = 10`; tổng tiền bán `1400000`, tổng tiền nhập `12000000`. Khi chạy với `data-mau`, thao tác lưu sẽ cập nhật bộ mẫu đó. Muốn khôi phục mẫu ban đầu, dùng lại file mẫu từ Git.

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
| `data/khachhang.txt` | Dữ liệu khách hàng ban đầu của repo, giữ nguyên |
| `data-mau/cuahang.txt` | Bộ dữ liệu mẫu để thực hành |
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

Toàn bộ dữ liệu dùng **một file `cuahang.txt`** trong thư mục được chọn. Tài liệu gợi ý chín file; ở đây các mục và dòng chi tiết được đặt trong cùng một file để việc đọc/ghi đơn giản và tránh lệch chứng từ với tồn kho khi lưu. File mẫu thể hiện đầy đủ định dạng.

File mã hóa UTF-8, phân cách bằng `|`, ngày `yyyy-MM-dd`, tiền không có dấu phân cách hàng nghìn. Chương trình chặn dấu `|` và xuống dòng trong trường dữ liệu. Mỗi mục có nhãn và số lượng; sản phẩm có nhãn `GIAY`, `QUANAO` hoặc `DUNGCU` để đọc đúng lớp con.

Đọc loại/người trước, rồi sản phẩm, chứng từ và từng dòng chi tiết. Tồn hiện tại đã nằm trong dòng sản phẩm nên **đọc file không nhập/bán lại hàng**. Đọc vào bộ danh sách tạm; chỉ thay bộ đang dùng khi toàn bộ file hợp lệ. File lỗi được báo kèm dòng; nếu lỗi lúc khởi động, chương trình dừng và không ghi đè file.

Khi lưu, chương trình ghi `cuahang.tmp`, đóng file, đọc thử lại, đổi file cũ thành `cuahang.bak`, rồi thay file chính. Nếu thay thất bại thì phục hồi bản cũ. Nếu lần sau thiếu file chính nhưng còn `.bak`, chương trình phục hồi bản sao. Đây là cách xử lý file thông thường cho ứng dụng console một tiến trình.

Lần chạy đầu chưa có `cuahang.txt`, chương trình đọc `data/khachhang.txt` của repo. File gốc có bốn trường; trường thứ tư được giữ nguyên trong `diaChi` theo mô hình đã chọn, kể cả các giá trị hiện là địa chỉ email. Có thể sửa lại địa chỉ qua menu. File gốc không bị sửa/xóa. Các lần sau đọc bộ dữ liệu đã lưu trong `cuahang.txt`.

## Kiểm thử

```powershell
.\test.bat
```

Hoặc:

```text
javac -encoding UTF-8 -Xlint:all -d out -sourcepath src src/Main.java tests/KiemThu.java
java -cp out KiemThu
```

Kiểm thử dùng Java thuần, tự báo lỗi bằng `AssertionError`; không cần JUnit. Bao gồm mở rộng/dồn mảng, CRUD của bảy danh sách, chi tiết và sao chép, đơn giá lịch sử, thống kê theo ngày, thêm/sửa/xóa chứng từ, thiếu tồn ở dòng sau, tham chiếu trước khi xóa, ghi/đọc UTF-8, đọc lại không đổi tồn, file sai/truncated, phục hồi bản sao và nhập dữ liệu khách hàng cũ. Dữ liệu kiểm thử tạo trong `out/`, không sửa `data/`.
