import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Project {
    public final static String[] VALIDFORMATS= {"yyyy-MM-d","yyyy/MM/d","yyyy;MM;d",
            "yyyy:MM:d", "d-MM-yyyy","d/MM/yyyy", "d;MM;yyyy","d:MM:yyyy", "MMM d yy", "MMM-d-yy","MMM;d;yy","MMM:d:yy",
            "yyyy MMMM dd","yyyy-MMMM-dd","yyyy:MMMM:dd","yyyy/MMMM/dd"};
    public static class Period {
        private LocalDate dateFrom;
        private LocalDate dateTo;

        public Period(String dateFrom, String dateTo) throws Exception {
            setDateFrom(dateFrom);
            setDateTo(dateTo);
        }

        private void setDateFrom(String dateFrom) throws Exception {
            this.dateFrom = validateDate(dateFrom);
        }

        private void setDateTo(String inputDate) throws Exception {
            if (inputDate.equals("NULL")) {
                this.dateTo = LocalDate.now();
                return;
            }
            LocalDate date = validateDate(inputDate);
            if (this.dateFrom.compareTo(date) > 0) {
                throw new IllegalArgumentException("End date can't be earlier than start date" + dateFrom);
            }
            this.dateTo = date;
        }

        private LocalDate validateDate(String inputDate) throws Exception {
            for (String validator : VALIDFORMATS) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(validator);
                return LocalDate.parse(inputDate.trim(), formatter);
            }
            throw new Exception("Date format is not supported!");
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        @Override
        public String toString() {
            return String.format("%s %s", dateFrom, dateTo);
        }
    }
    private final int projectID;
    private final Period workedFromDateToDate;

    public Project(int projectID, String dateFrom, String dateTo) throws Exception {
        this.projectID = projectID;
        this.workedFromDateToDate = new Period(dateFrom, dateTo);
    }

    public int getProjectID() {
        return projectID;
    }

    public Period getWorkedFromDateToDate() {
        return workedFromDateToDate;
    }

    @Override
    public int hashCode() {
        final int code = 23;
        return code * Integer.hashCode(projectID);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (getClass() != otherObject.getClass()) {
            return false;
        }
        Project other = (Project) otherObject;
        if (projectID == 0) {
            return other.projectID == 0;
        } else return projectID == other.projectID;

    }

    @Override
    public String toString() {
        return String.format("%d %s %s", projectID, workedFromDateToDate.getDateFrom(), workedFromDateToDate.getDateTo());
    }
}
