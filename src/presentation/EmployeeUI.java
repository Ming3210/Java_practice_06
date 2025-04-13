package presentation;

import business.model.Employee;
import business.model.EmployeeStatus;
import business.model.Gender;
import business.service.employee.EmployeeService;
import business.service.employee.EmployeeServiceImpl;


import java.util.List;
import java.util.Scanner;

public class EmployeeUI {
    private static EmployeeService employeeService;

    public EmployeeUI() {
        if (employeeService == null) {
            employeeService = new EmployeeServiceImpl();
        }
    }

    public static void main(String[] args) {
        employeeService = new EmployeeServiceImpl(); // Tạo đối tượng EmployeeServiceImpl
        EmployeeUI ui = new EmployeeUI(); // Tạo đối tượng EmployeeUI
        ui.showMenu(); // Gọi phương thức để hiển thị menu
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== QUẢN LÝ NHÂN VIÊN =====");
            System.out.println("1. Danh sách nhân viên");
            System.out.println("2. Thêm nhân viên");
            System.out.println("3. Cập nhật nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Tìm kiếm nhân viên");
            System.out.println("6. Sắp xếp nhân viên");
            System.out.println("7. Thống kê nhân viên");
            System.out.println("8. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Đọc ký tự newline

            switch (choice) {
                case 1:
                    showEmployeeList(scanner);
                    break;
                case 2:
                    addEmployee(scanner);
                    break;
                case 3:
                    updateEmployee(scanner);
                    break;
                case 4:
                    deleteEmployee(scanner);
                    break;
                case 5:
                    searchEmployee(scanner);
                    break;
                case 6:
                    sortEmployees(scanner);
                    break;
                case 7:
                    statistics(scanner);
                    break;
                case 8:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        } while (choice != 8);
    }

    private void showEmployeeList(Scanner scanner) {
        System.out.print("Nhập số trang: ");
        int page = scanner.nextInt();
        int pageSize = 10;

        List<Employee> employees = employeeService.getEmployees(page, pageSize);
        for (Employee employee : employees) {
            System.out.println(employee.getEmployeeID() + " - " + employee.getUsername() + " - " + employee.getSalary());
        }
    }

    private void addEmployee(Scanner scanner) {
        System.out.println("===== Thêm nhân viên =====");

        System.out.print("Tên nhân viên: ");
        String username = scanner.nextLine();

        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        System.out.print("Phòng ban ID (Phòng ban phải ở trạng thái hoạt động): ");
        int departmentID = scanner.nextInt();
        scanner.nextLine(); // Đọc ký tự newline

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Số điện thoại: ");
        String phone = scanner.nextLine();

        System.out.print("Địa chỉ: ");
        String address = scanner.nextLine();

        System.out.print("Ngày sinh (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("Giới tính (MALE/FEMALE): ");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Chức vụ: ");
        String role = scanner.nextLine();

        System.out.print("Lương: ");
        double salary = scanner.nextDouble();

        System.out.print("Bậc lương: ");
        double salaryTier = scanner.nextDouble();
        scanner.nextLine(); // Đọc ký tự newline

        System.out.print("Trạng thái (ACTIVE/INACTIVE): ");
        EmployeeStatus status = EmployeeStatus.valueOf(scanner.nextLine().toUpperCase());

        Employee employee = new Employee(address, dateOfBirth, departmentID, email, "", gender, password, phone, role, salary, salaryTier, status, username);
        employeeService.addEmployee(employee);
        System.out.println("Thêm nhân viên thành công!");
    }

    private void updateEmployee(Scanner scanner) {
        System.out.println("===== Cập nhật nhân viên =====");

        System.out.print("Nhập mã nhân viên cần cập nhật: ");
        String employeeID = scanner.nextLine();

        // Tìm kiếm nhân viên theo ID
        List<Employee> employees = employeeService.searchEmployeeByNameAndAge(employeeID, 0, 100);
        if (employees.isEmpty()) {
            System.out.println("Không tìm thấy nhân viên với mã ID: " + employeeID);
            return;
        }

        Employee employee = employees.get(0); // Giả sử chỉ có 1 kết quả
        System.out.println("Thông tin nhân viên: " + employee.getUsername());

        // Cập nhật thông tin nhân viên
        System.out.print("Tên nhân viên: ");
        String username = scanner.nextLine();
        employee.setUsername(username);

        System.out.print("Lương mới: ");
        double salary = scanner.nextDouble();
        employee.setSalary(salary);

        employeeService.updateEmployee(employee);
        System.out.println("Cập nhật thông tin nhân viên thành công!");
    }

    private void deleteEmployee(Scanner scanner) {
        System.out.println("===== Xóa nhân viên =====");

        System.out.print("Nhập mã nhân viên cần xóa: ");
        String employeeID = scanner.nextLine();

        employeeService.deleteEmployee(employeeID);
        System.out.println("Xóa nhân viên thành công!");
    }

    private void searchEmployee(Scanner scanner) {
        System.out.println("===== Tìm kiếm nhân viên =====");

        System.out.print("Nhập tên nhân viên: ");
        String name = scanner.nextLine();

        System.out.print("Nhập độ tuổi tối thiểu: ");
        int minAge = scanner.nextInt();

        System.out.print("Nhập độ tuổi tối đa: ");
        int maxAge = scanner.nextInt();

        List<Employee> employees = employeeService.searchEmployeeByNameAndAge(name, minAge, maxAge);
        for (Employee employee : employees) {
            System.out.println(employee.getEmployeeID() + " - " + employee.getUsername() + " - " + employee.getSalary());
        }
    }

    private void sortEmployees(Scanner scanner) {
        System.out.println("===== Sắp xếp nhân viên =====");

        System.out.println("1. Sắp xếp theo lương giảm dần");
        System.out.println("2. Sắp xếp theo tên tăng dần");
        System.out.print("Chọn phương thức sắp xếp: ");
        int choice = scanner.nextInt();

        List<Employee> sortedEmployees = null;
        if (choice == 1) {
            sortedEmployees = employeeService.sortEmployeesBySalary();
        } else if (choice == 2) {
            sortedEmployees = employeeService.sortEmployeesByName();
        }

        if (sortedEmployees != null) {
            for (Employee employee : sortedEmployees) {
                System.out.println(employee.getEmployeeID() + " - " + employee.getUsername() + " - " + employee.getSalary());
            }
        } else {
            System.out.println("Không có kết quả sắp xếp.");
        }
    }

    private void statistics(Scanner scanner) {
        System.out.println("===== Thống kê =====");

        System.out.println("1. Số lượng nhân viên theo phòng ban");
        System.out.println("2. Tổng số nhân viên");
        System.out.println("3. Phòng ban có nhiều nhân viên nhất");
        System.out.println("4. Phòng ban có lương cao nhất");
        System.out.print("Chọn chức năng thống kê: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                List<Employee> departmentStats = employeeService.countEmployeesByDepartment();
                for (Employee e : departmentStats) {
                    System.out.println("Phòng ban: " + e.getDepartmentID() + " - Số lượng nhân viên: " + e.getSalary());
                }
                break;
            case 2:
                int totalEmployees = employeeService.totalEmployeeCount();
                System.out.println("Tổng số nhân viên: " + totalEmployees);
                break;
            case 3:
                String departmentMostEmployees = employeeService.departmentWithMostEmployees();
                System.out.println("Phòng ban có nhiều nhân viên nhất: " + departmentMostEmployees);
                break;
            case 4:
                String departmentHighestSalary = employeeService.departmentWithHighestSalary();
                System.out.println("Phòng ban có lương cao nhất: " + departmentHighestSalary);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                break;
        }
    }
}
