import java.util.HashSet;
import java.util.Set;

public class Employee {
    private final int employeeID;
    private final Set<Project> workedProjects;

    public Employee(int employeeID) {
        this.employeeID = employeeID;
        workedProjects = new HashSet<>();
    }

    public void addProject(int projectID, String dateFrom, String dateTo) throws Exception {
        workedProjects.add(new Project(projectID, dateFrom, dateTo));
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public Set<Project> getWorkedProjects() {
        return workedProjects;
    }

    public Project getProjectByID(int projectID) {
        for (Project project : workedProjects) {
            if (projectID == project.getProjectID()) {
                return project;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int code = 23;
        return code * Integer.hashCode(employeeID);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (getClass() != otherObject.getClass()) {
            return false;
        }
        Employee other = (Employee) otherObject;
        if (employeeID == 0) {
            return other.employeeID == 0;
        } else return employeeID == other.employeeID;
    }

    @Override
    public String toString() {
        return String.format("%s %s", employeeID, workedProjects);
    }
}
