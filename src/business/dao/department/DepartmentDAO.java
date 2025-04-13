package business.dao.department;

import business.model.Department;
import java.util.List;

public interface DepartmentDAO {
    List<Department> getDepartments(int page, int pageSize);
    void addDepartment(Department department);
    void updateDepartment(Department department);
    boolean deleteDepartment(int id);
    List<Department> searchByName(String keyword);
}
