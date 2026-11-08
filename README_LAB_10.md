# Lab 10 · Load ảnh từ Internet

Ứng dụng Jetpack Compose tải danh sách URL từ một file trên GitHub rồi dùng Coil để tải và hiển thị ảnh Internet.

## Tính năng

- Đọc các URL trong file `Lab 10. Mars Photos app/img/image-links.txt` trên GitHub Raw.
- Hiển thị tối đa 20 ảnh bằng `LazyVerticalGrid`, mỗi hàng có một ảnh.
- Nút tải lại lấy danh sách URL mới sau khi file GitHub được cập nhật.
- Hiển thị trạng thái đang tải, lỗi ảnh, lỗi mạng và nút thử lại.
- Giao diện và trạng thái được viết bằng Kotlin/Jetpack Compose, không dùng XML layout.

## Ảnh ứng dụng

| Tải ảnh thành công | Một ảnh không tải được |
|:---:|
| <img src="video/lab10_1.png" alt="Màn hình ảnh Internet tải thành công" width="260"> | <img src="video/lab10_2.png" alt="Màn hình Lab 10 có một ảnh không tải được" width="260"> |

## Ảnh mẫu

Mỗi dòng trong `img/image-links.txt` là một URL ảnh HTTPS. Hiện file có 3 ảnh. Để thêm ảnh, thêm URL mới vào file rồi push lên GitHub; bấm nút tải lại trong app để lấy danh sách mới. App bỏ URL trùng và chỉ hiển thị tối đa 20 ảnh.

| Hồ núi lúc hoàng hôn |
|:---:|
| ![Hồ núi lúc hoàng hôn](Lab%2010.%20Mars%20Photos%20app/img/ddg_1775238082972349461.jpg) |
| Đường núi |
| ![Đường núi](Lab%2010.%20Mars%20Photos%20app/img/ddg_1774780397475447773.jpg) |
| Hồ Wanaka |
| ![Hồ Wanaka](Lab%2010.%20Mars%20Photos%20app/img/ken-cheung-KonWFWUaAuk-unsplash.jpg) |

## Chạy ứng dụng

Mở thư mục `Lab 10. Mars Photos app` trong Android Studio, đồng bộ Gradle và chạy trên máy ảo hoặc thiết bị có Internet.

Ứng dụng chỉ cần quyền Internet. Lab này không dùng Firebase hoặc cấu hình dịch vụ Firebase.

## Tài liệu tham khảo

- [Android Basics with Compose: Load and display images from the internet](https://developer.android.com/codelabs/basic-android-kotlin-compose-load-images?hl=vi)
- [Coil](https://coil-kt.github.io/coil/)
