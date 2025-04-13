package presentation;


import business.dao.department.DepartmentDAO;
import business.dao.department.DepartmentDAOImpl;
import business.model.Department;

import java.util.List;
import java.util.Scanner;

public class DepartmentUI {
    private final DepartmentDAO departmentDAO = new DepartmentDAOImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n===== QUẢN LÝ PHÒNG BAN =====");
            System.out.println("1. Danh sách phòng ban (có phân trang)");
            System.out.println("2. Thêm mới phòng ban");
            System.out.println("3. Cập nhật phòng ban");
            System.out.println("4. Xoá phòng ban");
            System.out.println("5. Tìm kiếm phòng ban theo tên");
            System.out.println("6. Quay lại");
            System.out.print("Chọn chức năng: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    listDepartments();
                    break;
                case 2:
                    addDepartment();
                    break;
                case 3:
                    updateDepartment();
                    break;
                case 4:
                    deleteDepartment();
                    break;
                case 5:
                    searchDepartment();
                    break;
                case 6:
                    System.out.println("Quay lại menu chính.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 6);
    }

    private void listDepartments() {
        int page = 1;
        int pageSize = 5;

        while (true) {
            List<Department> departments = departmentDAO.getDepartments(page, pageSize);
            if (departments.isEmpty()) {
                System.out.println("Không có phòng ban nào.");
                break;
            }

            System.out.printf("Trang %d:\n", page);
            for (Department d : departments) {
                System.out.printf("ID: %d | Tên: %s | Mô tả: %s | Trạng thái: %s\n",
                        d.getId(), d.getName(), d.getDescription(), d.isStatus() ? "Hoạt động" : "Không hoạt động");
            }

            System.out.print("Nhấn (n) để tiếp, (p) để quay lại, (q) để thoát: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("n")) page++;
            else if (input.equalsIgnoreCase("p") && page > 1) page--;
            else break;
        }
    }

    private void addDepartment() {
        System.out.print("Tên phòng ban: ");
        String name = scanner.nextLine();
        System.out.print("Mô tả: ");
        String desc = scanner.nextLine();
        System.out.print("Trạng thái (true/false): ");
        boolean status = Boolean.parseBoolean(scanner.nextLine());

        Department d = new Department(0, name, desc, status);
        departmentDAO.addDepartment(d);
        System.out.println("Thêm phòng ban thành công!");
    }

    private void updateDepartment() {
        System.out.print("Nhập ID phòng ban cần cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Tên mới: ");
        String name = scanner.nextLine();
        System.out.print("Mô tả mới: ");
        String desc = scanner.nextLine();
        System.out.print("Trạng thái mới (true/false): ");
        boolean status = Boolean.parseBoolean(scanner.nextLine());

        Department d = new Department(id, name, desc, status);
        departmentDAO.updateDepartment(d);
        System.out.println("Cập nhật thành công.");
    }

    private void deleteDepartment() {
        System.out.print("Nhập ID phòng ban cần xoá: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean success = departmentDAO.deleteDepartment(id);
        if (success) {
            System.out.println("Xoá phòng ban thành công.");
        } else {
            System.out.println("Không thể xoá vì phòng ban đang có nhân viên.");
        }
    }

    private void searchDepartment() {
        System.out.print("Nhập tên phòng ban cần tìm: ");
        String keyword = scanner.nextLine();

        List<Department> results = departmentDAO.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy phòng ban nào.");
        } else {
            System.out.println("Kết quả tìm kiếm:");
            for (Department d : results) {
                System.out.printf("ID: %d | Tên: %s | Mô tả: %s | Trạng thái: %s\n",
                        d.getId(), d.getName(), d.getDescription(), d.isStatus() ? "Hoạt động" : "Không hoạt động");
            }
        }
    }
}
