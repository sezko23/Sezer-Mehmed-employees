import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.Scanner;

public class MainImplementation {
    private static final String READFROMFILE = "SirmaTaskDates.txt";
    private static List<Employee> fromFileGetEmployees() throws Exception {
        FileInputStream inputStream = new FileInputStream(READFROMFILE);
        Scanner scanner = new Scanner(inputStream);
        List<Employee> listOfAllEmployees = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String[] inputData = scanner.nextLine().split(", ");

            int EmployeeID = Integer.parseInt(inputData[0]);
            int projectID = Integer.parseInt(inputData[1]);

            String dateFrom = inputData[2];
            String dateTo = inputData[3];

            Employee employee = new Employee(EmployeeID);

            if (!listOfAllEmployees.contains(employee)) {
                employee.addProject(projectID, dateFrom, dateTo);
                listOfAllEmployees.add(employee);
            } else {
                listOfAllEmployees.stream().filter(e -> e.getEmployeeID() == employee.getEmployeeID())
                        .forEach(e -> {
                            try {
                                e.addProject(projectID, dateFrom, dateTo);
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        });
            }
        }
        scanner.close();
        return listOfAllEmployees;
    }

    private static Set<PairOfEmployees> setPairs(List<Employee> employees) {
        Set<PairOfEmployees> setOfAllPairs = new HashSet<>();
        for (int i = 0; i < employees.size() - 1; i++) {
            Set<Project> currentEmployeeProjects = employees.get(i).getWorkedProjects();
            for (int j = i + 1; j < employees.size(); j++) {
                Set<Project> nextEmployeeProjects = employees.get(j).getWorkedProjects();
                Set<Integer> mutualIDs = nextEmployeeProjects.stream().filter(currentEmployeeProjects::contains)
                        .map(Project::getProjectID).collect(Collectors.toSet());
                setOfAllPairs.add(new PairOfEmployees(employees.get(i), employees.get(j), mutualIDs));
            }
        }
        return setOfAllPairs;
    }

    public static String togetherWorkedPairs(Set<PairOfEmployees> pairs) {
        if (pairs == null || pairs.isEmpty()) {
            throw new IllegalArgumentException("No pair of employees worked together");
        }

        Iterator<PairOfEmployees> iterator = pairs.iterator();
        PairOfEmployees pairWorkingLongestTogether = iterator.next();

        while (iterator.hasNext()) {
            PairOfEmployees currentPair = iterator.next();
            if (pairWorkingLongestTogether.getTogetherWorkedTime() < currentPair.getTogetherWorkedTime()) {
                pairWorkingLongestTogether = currentPair;
            }
        }
        return "Longest working pair of employees: Employee with ID "
                + pairWorkingLongestTogether.getEmployeeOne().getEmployeeID()
                + " and Employee with ID "
                + pairWorkingLongestTogether.getEmployeeTwo().getEmployeeID()
                + ". They have worked together for total of "
                + pairWorkingLongestTogether.getTogetherWorkedTime()
                + " days.";

    }

    public void run() throws Exception {
        List<Employee> employees = fromFileGetEmployees();
        Set<PairOfEmployees> pairs = setPairs(employees);
        System.out.println(togetherWorkedPairs(pairs));
    }
}
