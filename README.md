Hướng dẫn cài đặt:
1. Tải về JDK bản 17.0.5 và Apache Tomcat bản 10.0.27

2. Cấu hình CLASS PATH cho JDK theo link hướng dẫn sau:
https://youtu.be/7dc-Ea8HGGM?si=LNI0Eb88dOfx2zXm&t=300

3. Tải về Eclipse (bản mới nhất) và chạy file setup. Lưu ý:
- Khi mở file setup: chọn Eclipse IDE for Enterprise Java and Web Developers
- Phần Java 17+ VM: dẫn tới file JDK
- Các thiết lập khác để mặc định

4. Tạo workspace và cài Apache Tomcat server vào workspace theo link hướng dẫn sau:
https://youtu.be/7dc-Ea8HGGM?si=H3e0o-f4A9Ix-9Xd&t=545

5. Tải project từ github về và import project vào workspace:
Trên thanh công cụ trong workspace Chọn File > Open Projects from File System > Diectory (Dẫn tới folder chứa project) > Finish

6. Chạy project:
Trong Project Explorer Chuột phải và project > Run As > Run On Server (> Chọn Apache Tomcat 10.0.27 nếu chưa có) > Run
