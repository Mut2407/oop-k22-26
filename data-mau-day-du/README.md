# Dữ liệu mẫu đầy đủ – Sport Store

Đây là dữ liệu giả lập để học và kiểm thử; tên, số điện thoại và giao dịch chỉ dùng minh họa.

| Nhóm | Số lượng |
|---|---:|
| Khách hàng | 10 |
| Nhân viên | 5 |
| Nhà cung cấp | 5 |
| Hóa đơn | 10 |
| Phiếu nhập | 5 |
| Sản phẩm | 9 |
| Loại sản phẩm | 3 |

Toàn bộ dữ liệu nằm trong `cuahang.txt`, đúng định dạng mà `LuuTruFileText` hiện đọc được.

Tại thư mục gốc repo, chạy:

```powershell
.\run.bat data-mau-day-du
```

Nếu đã biên dịch:

```powershell
java -Dfile.encoding=UTF-8 -cp out Main data-mau-day-du
```

Các phiếu nhập từ 01/11/2026 đến 05/11/2026; hóa đơn từ 06/11/2026 đến 09/11/2026. Chi tiết nằm ngay sau dòng đầu mỗi chứng từ.

- Tổng tiền bán: **18.650.000 đồng**.
- Tổng tiền nhập: **76.350.000 đồng**.
- Tồn cuối các mã SP01–SP09: **26, 12, 22, 24, 15, 10, 13, 17, 24**.

Tồn đã tính từ tổng nhập trừ tổng bán. Khi đọc file, chương trình lấy tồn này, không cộng/trừ lại giao dịch. Mỗi mã khách, nhân viên, nhà cung cấp và sản phẩm trong chứng từ đều có dữ liệu tương ứng.

Định dạng các dòng:

| Nhóm | Thứ tự trường |
|---|---|
| Khách hàng | mã khách, họ tên, số điện thoại, địa chỉ |
| Nhân viên | mã nhân viên, họ tên, số điện thoại, ca làm việc |
| Nhà cung cấp | mã nhà cung cấp, tên, số điện thoại, địa chỉ |
| Đầu hóa đơn | mã hóa đơn, ngày, mã khách, mã nhân viên, số dòng chi tiết |
| Đầu phiếu nhập | mã phiếu, ngày, mã nhà cung cấp, mã nhân viên, số dòng chi tiết |
| Dòng chi tiết | mã sản phẩm, số lượng, đơn giá tại giao dịch |

Các trường cách nhau bằng `|`; tiền không chèn dấu phân cách hàng nghìn. Các dòng như `KHACHHANG|10` là nhãn mục và số phần tử, không phải một khách hàng.

Ví dụ hóa đơn HD01:

```text
HD01|2026-11-06|KH01|NV01|2
SP01|2|700000
SP04|1|300000
```

HD01 do NV01 lập cho KH01, có hai dòng sản phẩm. Tổng tiền là `2 × 700000 + 1 × 300000 = 1700000` đồng.
