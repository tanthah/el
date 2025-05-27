package DTO;

import Model.Course;
import java.util.List;

public class PurchasedCoursesResultDTO {
    private boolean success;
    private String message;
    private String errorMessage;
    private List<Course> purchasedCourses;

    // Constructor
    public PurchasedCoursesResultDTO() {
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Course> getPurchasedCourses() {
        return purchasedCourses;
    }

    public void setPurchasedCourses(List<Course> purchasedCourses) {
        this.purchasedCourses = purchasedCourses;
    }
}