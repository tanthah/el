/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;



import java.util.Set;
import ENum.ScheduleDay;
import Model.*;

public class CourseDetailsDTO {
    private Course course;
    private Description description;
    private Set<ScheduleDay> applicableDays;
    private Teacher teacher;

    // Constructor
    public CourseDetailsDTO(Course course, Description description, Set<ScheduleDay> applicableDays, Teacher teacher) {
        this.course = course;
        this.description = description;
        this.applicableDays = applicableDays;
        this.teacher = teacher;
    }

    // Getters
    public Course getCourse() {
        return course;
    }

    public Description getDescription() {
        return description;
    }

    public Set<ScheduleDay> getApplicableDays() {
        return applicableDays;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}