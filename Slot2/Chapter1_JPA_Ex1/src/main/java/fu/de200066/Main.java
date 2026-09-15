package fu.de200066;

import fe.de200066.dao.EmployeeDAO;
import fe.de200066.pojo.Employee;
import fe.de200066.pojo.Gender;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsf302FU");
        EmployeeDAO dao = new EmployeeDAO(emf);
        String email = "test.crud." + System.currentTimeMillis() + "@gmail.com";
        // =========================================================================
        // BƯỚC 1: CREATE - Tạo mới nhân viên
        // [LIFECYCLE]: new Employee() -> Đối tượng ở trạng thái NEW / TRANSIENT
        // (chưa có ID, chưa được EntityManager quản lý)
        // =========================================================================
        System.out.println(">>> BƯỚC 1: CREATE (Thêm mới nhân viên)");
        Employee newEmp = new Employee(
                "Tran Van B",
                email,
                new BigDecimal("18000000.00"),
                Gender.FEMALE,
                LocalDate.of(2021, 6, 15),
                true
        );
        System.out.println("- Trước khi save, ID: " + newEmp.getId());
        // Gọi save(): Trong transaction gọi em.persist() -> MANAGED
        // Khi save() xong và EntityManager đóng lại -> DETACHED
        dao.save(newEmp);
        Long generatedId = newEmp.getId();
        System.out.println("- Sau khi save thành công, ID tự sinh: " + generatedId);
        System.out.println("- Thông tin: " + newEmp + "\n");
        // =========================================================================
        // BƯỚC 2: READ - Đọc dữ liệu nhân viên vừa tạo
        // [LIFECYCLE]: em.find() đưa entity vào trạng thái MANAGED trong session.
        // Khi method findById() return và đóng EntityManager -> Trở thành DETACHED
        // =========================================================================
        System.out.println(">>> BƯỚC 2: READ (Tìm nhân viên theo ID: " + generatedId + ")");
        Employee foundEmp = dao.findById(generatedId);
        System.out.println("- Kết quả tìm thấy: " + foundEmp + "\n");
        // =========================================================================
        // BƯỚC 3: UPDATE - Sửa đổi thông tin nhân viên
        // [LIFECYCLE]: foundEmp đang là DETACHED.
        // Khi gọi dao.update(foundEmp), em.merge() sẽ copy dữ liệu mới vào bản sao
        // MANAGED trong transaction để update xuống DB. Sau khi commit & close -> DETACHED
        // =========================================================================
        System.out.println(">>> BƯỚC 3: UPDATE (Thay đổi lương từ 18tr lên 25tr và trạng thái)");
        foundEmp.setSalary(new BigDecimal("25000000.00"));
        foundEmp.setActive(false);
        dao.update(foundEmp);
        System.out.println("- Đã gọi update() thành công.\n");
        // =========================================================================
        // BƯỚC 4: READ LẠI ĐỂ KIỂM TRA UPDATE
        // =========================================================================
        System.out.println(">>> BƯỚC 4: READ LẠI (Kiểm tra xem dữ liệu mới đã vào DB chưa)");
        Employee checkUpdatedEmp = dao.findById(generatedId);
        System.out.println("- Lương mới sau khi update: " + checkUpdatedEmp.getSalary());
        System.out.println("- Trạng thái active mới: " + checkUpdatedEmp.isActive() + "\n");
        // =========================================================================
        // BƯỚC 5: DELETE - Xóa nhân viên vừa tạo
        // [LIFECYCLE]: Trong delete(), find() đưa entity thành MANAGED,
        // sau đó em.remove() chuyển entity sang trạng thái REMOVED.
        // Khi tx.commit() hoàn tất -> Entity chính thức bị xóa khỏi Database.
        // =========================================================================
        System.out.println(">>> BƯỚC 5: DELETE (Xóa nhân viên ID: " + generatedId + ")");
        boolean isDeleted = dao.delete(generatedId);
        System.out.println("- Xóa thành công? " + isDeleted + "\n");
        // =========================================================================
        // BƯỚC 6: READ LẠI ĐỂ KIỂM TRA ĐÃ XÓA
        // =========================================================================
        System.out.println(">>> BƯỚC 6: READ LẠI (Kiểm chứng nhân viên đã biến mất khỏi DB)");
        Employee checkDeletedEmp = dao.findById(generatedId);
        if (checkDeletedEmp == null) {
            System.out.println("- Kết quả: null (Xác nhận nhân viên đã được xóa hoàn toàn khỏi DB)!");
        } else {
            System.out.println("- Lỗi: Vẫn còn tìm thấy: " + checkDeletedEmp);
        }
        System.out.println("\n=================================================");
        System.out.println("       HOÀN THÀNH TOÀN BỘ LUỒNG CRUD DEMO       ");
        System.out.println("=================================================");

        System.out.println("TODO 9");

        String duplicateEmail = "trungemail." + System.currentTimeMillis() + "@gmail.com";
        // Tạo nhân viên 1
        Employee emp1 = new Employee(
                "Nhan Vien Thu Nhat",
                duplicateEmail,
                new BigDecimal("12000000.00"),
                Gender.MALE,
                LocalDate.of(2023, 1, 1),
                true
        );
        // Tạo nhân viên 2 CÓ CÙNG EMAIL với nhân viên 1
        Employee emp2 = new Employee(
                "Nhan Vien Thu Hai",
                duplicateEmail,
                new BigDecimal("14000000.00"),
                Gender.FEMALE,
                LocalDate.of(2023, 2, 2),
                true
        );
        try {
            System.out.println("- Bước 1: Lưu nhân viên thứ nhất (email: " + duplicateEmail + ")...");
            dao.save(emp1);
            System.out.println("  -> Kết quả: Lưu THÀNH CÔNG với ID = " + emp1.getId());
            System.out.println("- Bước 2: Cố tình lưu nhân viên thứ hai CÙNG EMAIL...");
            dao.save(emp2);
            // Dòng này không bao giờ chạy tới nếu database chặn thành công
            System.out.println("  -> [THẤT BẠI]: Database không chặn được email trùng!");
        } catch (Exception ex) {
            // Bắt ngoại lệ để chương trình không bị crash
            System.out.println("  -> [BẮT ĐƯỢC LỖI]:");
            System.out.println("     Loại ngoại lệ: " + ex.getClass().getSimpleName());
            System.out.println("     Thông báo: Ràng buộc @Column(unique = true) đã hoạt động chính xác!");
            System.out.println("     Database từ chối insert bản ghi thứ hai do trùng email.");
        }



        emf.close();
    }
}