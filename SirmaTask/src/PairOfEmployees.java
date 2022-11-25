import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class PairOfEmployees {
    private final Employee employeeOne;
    private final Employee employeeTwo;
    private final Set<Integer> togetherWorkedProjectsIDs;
    private final long togetherWorkedTime;

    public PairOfEmployees(Employee employeeOne, Employee employeeTwo, Set<Integer> togetherWorkedProjectsIDs) {
        this.employeeOne = employeeOne;
        this.employeeTwo = employeeTwo;
        this.togetherWorkedProjectsIDs = togetherWorkedProjectsIDs;
        this.togetherWorkedTime = findTogetherWorkedTime();
    }

    public Employee getEmployeeOne() {
        return employeeOne;
    }

    public Employee getEmployeeTwo() {
        return employeeTwo;
    }

    public long getTogetherWorkedTime() {
        return togetherWorkedTime;
    }

    public long findTogetherWorkedTime() {
        int timeBothSpent = 0;
        for (int projectID : togetherWorkedProjectsIDs) {
            LocalDate pairStartedTogether;
            LocalDate pairFinishedTogether;
            int startedTogether = employeeOne.getProjectByID(projectID).getWorkedFromDateToDate().getDateFrom().compareTo
                    (employeeTwo.getProjectByID(projectID).getWorkedFromDateToDate().getDateFrom());

            if (startedTogether >= 0) {
                pairStartedTogether = employeeOne.getProjectByID(projectID).getWorkedFromDateToDate().getDateFrom();
            } else {
                pairStartedTogether = employeeTwo.getProjectByID(projectID).getWorkedFromDateToDate().getDateFrom();
            }

            int finishedTogether = employeeOne.getProjectByID(projectID).getWorkedFromDateToDate().getDateTo().compareTo
                    (employeeTwo.getProjectByID(projectID).getWorkedFromDateToDate().getDateTo());

            if (finishedTogether <= 0) {
                pairFinishedTogether = employeeOne.getProjectByID(projectID).getWorkedFromDateToDate().getDateTo();
            } else {
                pairFinishedTogether = employeeTwo.getProjectByID(projectID).getWorkedFromDateToDate().getDateTo();
            }

            if(ChronoUnit.DAYS.between(pairStartedTogether,pairFinishedTogether) > 0){
                timeBothSpent += ChronoUnit.DAYS.between(pairStartedTogether, pairFinishedTogether);
            }
        }
        return timeBothSpent;
    }

    public int hashCode() {
        final int code = 23;
        return code * Integer.hashCode(employeeOne.getEmployeeID()) * Integer.hashCode(employeeTwo.getEmployeeID());
    }

    @Override
    public boolean equals(Object otherObject) {
        if (getClass() != otherObject.getClass()) {
            return false;
        }

        PairOfEmployees other = (PairOfEmployees) otherObject;
        if (this.getEmployeeOne().equals(other.getEmployeeOne()) &&
                this.getEmployeeTwo().equals(other.getEmployeeTwo())) {
            return true;
        }
        return this.getEmployeeTwo().equals(other.getEmployeeOne()) &&
                this.getEmployeeOne().equals(other.getEmployeeOne());
    }
}
