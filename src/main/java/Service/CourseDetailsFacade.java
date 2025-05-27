
package Service;



import dao.*;

import Model.*;

import DTO.*;

import ENum.ScheduleDay;
import java.util.List;
import java.util.Set;

public class CourseDetailsFacade {
    private final CourseDAO courseDAO;
    private final DescriptionDAO descriptionDAO;
    private final TeacherDAO teacherDAO;

    // Constructor khởi tạo các DAO
    public CourseDetailsFacade() {
        this.courseDAO = new CourseDAO();
        this.descriptionDAO = new DescriptionDAO();
        this.teacherDAO = new TeacherDAO();
    }

    // Constructor cho phép tiêm DAO (dùng khi kiểm thử)
    public CourseDetailsFacade(CourseDAO courseDAO, DescriptionDAO descriptionDAO, TeacherDAO teacherDAO) {
        this.courseDAO = courseDAO;
        this.descriptionDAO = descriptionDAO;
        this.teacherDAO = teacherDAO;
    }

    // Lấy chi tiết khóa học theo tiêu đề
    public CourseDetailsDTO getCourseDetailsByTitle(String courseTitle) {
        try {
            // Tìm khóa học theo tiêu đề
            List<Course> courses = courseDAO.findCourseByTitle(courseTitle);
            if (courses.isEmpty()) {
                return null; // Không tìm thấy khóa học
            }
            Course course = courses.get(0); // Lấy khóa học đầu tiên

            // Lấy mô tả khóa học
            Description description = descriptionDAO.findByCourseId(course.getCourseId());

            // Lấy lịch học
            Set<ScheduleDay> applicableDays = descriptionDAO.getScheduleDay(course.getCourseId());

            // Lấy thông tin giáo viên
            Teacher teacher = teacherDAO.findTeacherByCourseId(course.getCourseId());

            // Trả về DTO chứa tất cả thông tin
            return new CourseDetailsDTO(course, description, applicableDays, teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}