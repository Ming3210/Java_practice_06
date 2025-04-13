package business.service.department;

import business.dao.department.DepartmentDAO;
import business.dao.department.DepartmentDAOImpl;
import business.model.Department;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDAO departmentDAO;

    // Constructor: Khởi tạo DAO
    public DepartmentServiceImpl() {
        this.departmentDAO = new DepartmentDAOImpl();
    }

    @Override
    public List<Department> getDepartments(int page, int pageSize) {
        return departmentDAO.getDepartments(page, pageSize);
    }

    @Override
    public void addDepartment(Department department) {
        departmentDAO.addDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) {
        departmentDAO.updateDepartment(department);
    }

    @Override
    public boolean deleteDepartment(int id) {
        return departmentDAO.deleteDepartment(id);
    }

    @Override
    public List<Department> searchByName(String keyword) {
        return departmentDAO.searchByName(keyword);
    }
}
