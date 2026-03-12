package dto;

import java.sql.Date;

public class Course {

    private int courseId;
    private String name;
    private String description;
    private double tuitionFee;
    private Date startDate;
    private Date endDate;
    private String scheduleText;
    private Integer instructorId;

    // ── existing joined field ──────────────────────────────
    private String instructorName;

    // ── NEW fields for Feature 1, 2, 3 ───────────────────
    private String status;            // "ACTIVE" | "INACTIVE"  (new DB column)
    private int totalSlots;        // new DB column
    private String instructorStatus;  // joined from Users.status
    private int enrolledCount;     // COUNT(*) from Registrations
    private String thumbnailUrl;
    private String videoUrl;
    private String detailDescription;

    // ── existing constructor (unchanged) ──────────────────
    public Course(int courseId, String name, String description, double tuitionFee,
            Date startDate, Date endDate, String scheduleText, Integer instructorId) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.tuitionFee = tuitionFee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scheduleText = scheduleText;
        this.instructorId = instructorId;
    }

    public Course() {
    }

    // ── existing getters/setters (unchanged) ──────────────
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getScheduleText() {
        return scheduleText;
    }

    public void setScheduleText(String scheduleText) {
        this.scheduleText = scheduleText;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    // ── NEW getters/setters ───────────────────────────────
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public String getInstructorStatus() {
        return instructorStatus;
    }

    public void setInstructorStatus(String instructorStatus) {
        this.instructorStatus = instructorStatus;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }
    
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String v) {
        this.detailDescription = v;
    }
}
