package sample;

public class filterBalance {
    String admission;
    String balance;
    String firstName;
    String accommodation;
    String classStream;
    String term;
    double limit;

    public filterBalance(String admission, String balance, String firstName, String accommodation, String classStream, String term, double limit) {
        this.admission = admission;
        this.balance = balance;
        this.firstName = firstName;
        this.accommodation = accommodation;
        this.classStream = classStream;
        this.term = term;
        this.limit = limit;
    }

    public String getAdmission() {
        return admission;
    }

    public void setAdmission(String admission) {
        this.admission = admission;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getClassStream() {
        return classStream;
    }

    public void setClassStream(String classStream) {
        this.classStream = classStream;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
