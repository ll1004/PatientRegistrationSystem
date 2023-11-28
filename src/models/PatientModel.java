package models;

public class PatientModel extends PersonModel{
    protected String patientStatus = "Ongoing";

    public String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        this.patientStatus = patientStatus;
    }
}
