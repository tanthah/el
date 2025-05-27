package DTO;

import Model.Course;
import java.util.List;

public class CourseSearchResultDTO {
    private boolean success;
    private String message;
    private String errorMessage;
    private List<Course> courses;
    private boolean showCourses;

    // Constructor
    public CourseSearchResultDTO() {
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public boolean isShowCourses() {
        return showCourses;
    }

    public void setShowCourses(boolean showCourses) {
        this.showCourses = showCourses;
    }
}