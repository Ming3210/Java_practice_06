package presentation;

import business.service.login.LoginService;
import business.service.login.LoginServiceImp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginService loginService = new LoginServiceImp();

        System.out.println("Nhập tên đăng nhập: ");
        String username = scanner.nextLine();
        System.out.println("Nhập mật khẩu: ");
        String password = scanner.nextLine();

        int result = loginService.checkLogin(username, password);

        if (result == 1) {
            System.out.println("Đăng nhập thành công!");

            while (true) {
                System.out.println("=========================MENU=========================");
                System.out.println("1. Quản lý phòng ban");
                System.out.println("2. Quản lý nhân viên");
                System.out.println("3. Đăng xuất");
                System.out.println("4. Thoát");

                System.out.print("Chọn chức năng: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Đang quản lý phòng ban...");
                        DepartmentUI departmentUI = new DepartmentUI();
                        departmentUI.showMenu();
                        break;
                    case 2:
                        System.out.println("Đang quản lý nhân viên...");
                        EmployeeUI employeeUI = new EmployeeUI();
                        employeeUI.showMenu();
                        break;
                    case 3:
                        System.out.println("Đăng xuất...");
                        return;
                    case 4:
                        System.out.println("Thoát chương trình...");
                        System.exit(0);
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            }

        } else {
            System.out.println("Đăng nhập thất bại! Vui lòng thử lại.");
        }
    }
}
