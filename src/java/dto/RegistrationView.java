package dto;

import java.sql.Date;

public class RegistrationView {

    private int regId;
    private int studentId;
    private int courseId;
    private Date regDate;
    private String status;
    private String note;
    private int enrolledCount;
    private String paymentStatus;

    private String studentName;   // joined from Users.fullName
    private String studentEmail;  // joined from Users.email
    private String courseName;    // joined from Courses.name

    // ── existing constructor (unchanged) ──────────────────
    public RegistrationView(int regId, int studentId, int courseId,
            Date regDate, String status, String note) {
        this.regId = regId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.regDate = regDate;
        this.status = status;
        this.note = note;
    }

    public RegistrationView() {
    }

    // ── existing getters/setters (unchanged) ──────────────
    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(int count) {
        this.enrolledCount = count;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
