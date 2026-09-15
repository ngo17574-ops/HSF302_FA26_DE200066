package fu.de200066;

import fu.de200066.pojo.Department;
import fu.de200066.pojo.Employee;
import fu.de200066.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Department dept = new Department("IT", "Ha Noi");
        Employee emp = new Employee("test@company.com", "Test", Gender.OTHER,
                new BigDecimal("1000"), LocalDate.now());
        dept.addEmployee(emp);
        System.out.println(dept.getEmployees().contains(emp)); // phải true
        System.out.println(emp.getDepartment() == dept); // phải true


    }
}