package edu.tdtu.lab8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @GetMapping("/employees")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "index";
    }

    @GetMapping("/employees/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add";
    }

    @PostMapping("/employees/add")
    public String addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String editEmployeeForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + id));
        model.addAttribute("employee", employee);
        return "employee-edit";
    }

    @PostMapping("/employees/edit/{id}")
    public String updateEmployee(@PathVariable("id") Long id, Employee employee,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            employee.setId(id);
            return "employee-edit";
        }
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + id));
        employeeRepository.delete(employee);
        return "redirect:/employees";
    }
}
