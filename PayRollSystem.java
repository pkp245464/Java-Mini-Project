import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

abstract class Employee {
    private int id;
    private String name;
    private Date joinDate;

    public Employee(String name, int id, Date joinDate) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public abstract double calculateSalary();

    public abstract String getEmployeeType();

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return "Employee[name=" + name + ",id=" + id + ",joinDate=" + dateFormat.format(joinDate) + 
               ",type=" + getEmployeeType() + ",salary=" + calculateSalary() + "]";
    }
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;
    private double bonus;

    public FullTimeEmployee(String name, int id, Date joinDate, double monthlySalary, double bonus) {
        super(name, id, joinDate);
        this.monthlySalary = monthlySalary;
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary + bonus;
    }

    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }
}

class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;
    private double overtime;

    public PartTimeEmployee(String name, int id, Date joinDate, int hoursWorked, double hourlyRate, double overtime) {
        super(name, id, joinDate);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.overtime = overtime;
    }

    @Override
    public double calculateSalary() {
        return (hoursWorked * hourlyRate) + (overtime * (hourlyRate * 1.5));
    }

    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }
}

class ContractEmployee extends Employee {
    private double contractAmount;
    private Date contractEndDate;

    public ContractEmployee(String name, int id, Date joinDate, double contractAmount, Date contractEndDate) {
        super(name, id, joinDate);
        this.contractAmount = contractAmount;
        this.contractEndDate = contractEndDate;
    }

    @Override
    public double calculateSalary() {
        return contractAmount;
    }

    @Override
    public String getEmployeeType() {
        return "Contract-Type";
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return super.toString() + ",contractEndDate=" + dateFormat.format(contractEndDate) + "]";
    }
}

class PayrollCal {
    private ArrayList<Employee> employeesList;

    public PayrollCal() {
        employeesList = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employeesList.add(employee);
    }

    public void removeEmployee(int id) {
        Employee employeeToRemove = null;
        for (Employee employee : employeesList) {
            if (employee.getId() == id) {
                employeeToRemove = employee;
                break;
            }
        }
        if (employeeToRemove != null) {
            employeesList.remove(employeeToRemove);
        }
    }

    public void displayEmployees() {
        for (Employee employee : employeesList) {
            System.out.println(employee);
        }
    }

    public double calculateTotalPayroll() {
        double totalPayroll = 0;
        for (Employee employee : employeesList) {
            totalPayroll += employee.calculateSalary();
        }
        return totalPayroll;
    }

    public void displayEmployeesByType(String type) {
        for (Employee employee : employeesList) {
            if (employee.getEmployeeType().equalsIgnoreCase(type)) {
                System.out.println(employee);
            }
        }
    }

    public Employee findEmployeeById(int id) {
        for (Employee employee : employeesList) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }
}

public class PayRollSystem {
    public static void main(String[] args) {
    
        PayrollCal PayrollCal = new PayrollCal();

        FullTimeEmployee emp1 = new FullTimeEmployee("Thorin Okenshield", 1, new Date(), 81000.00, 5000.00);
        PartTimeEmployee emp2 = new PartTimeEmployee("Gandalf The Gray", 2, new Date(), 2700, 243.09, 27.00);
        ContractEmployee emp3 = new ContractEmployee("Bilbo Baggins", 3, new Date(), 50000.00, new Date(System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000));

        PayrollCal.addEmployee(emp1);
        PayrollCal.addEmployee(emp2);
        PayrollCal.addEmployee(emp3);

        System.out.println("Initial Employee Details:");
        PayrollCal.displayEmployees();

        System.out.println("\nTotal Payroll: " + PayrollCal.calculateTotalPayroll());

        System.out.println("\nFull-Time Employees:");
        PayrollCal.displayEmployeesByType("Full-Time");

        System.out.println("\nAfter Removing Employee (ID: 2):");
        PayrollCal.removeEmployee(2);
        PayrollCal.displayEmployees();

        System.out.println("\nFind Employee by ID (ID: 1):");
        Employee foundEmployee = PayrollCal.findEmployeeById(1);
        System.out.println(foundEmployee != null ? foundEmployee : "Employee not found");

    }
}
