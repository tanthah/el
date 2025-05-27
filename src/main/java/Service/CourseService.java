package Service;

import DTO.ChapterAddResultDTO;
import DTO.CourseDetailsResultDTO;
import DTO.CourseSearchResultDTO;
import DTO.CourseUpdateResultDTO;
import Model.*;
import dao.*;
import DTO.CourseUpdateResultDTO;
import DTO.CreateCourseResultDTO;
import DTO.JoinCourseResultDTO;
import DTO.OrderPaymentResultDTO;
import DTO.PurchasedCoursesResultDTO;
import ENum.PaymentMethod;
import ENum.PaymentStatus;
import ENum.ScheduleDay;

import java.math.BigDecimal;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CourseService {

    private final CourseDAO courseDAO;
    private final ChapterDAO chapterDAO;
    private final LessonDAO lessonDAO;
    private final FileMediaDAO fileMediaDAO;
    private final CategoryDAO categoryDAO;
    private PaymentDAO paymentDAO ;
    private StudentDAO studentDAO ; 
    private OrderDAO orderDAO ; 
    private AccountDAO accountDAO ; 
    
    // Constructor for dependency injection
    public CourseService() {
        this.courseDAO = new CourseDAO();
        this.chapterDAO = new ChapterDAO();
        this.lessonDAO = new LessonDAO();
        this.fileMediaDAO = new FileMediaDAO();
        this.categoryDAO = CategoryDAO.getInstance();
        this.paymentDAO =  new PaymentDAO();
        this.studentDAO = new StudentDAO();
        this.orderDAO = new OrderDAO();
        this.accountDAO = new AccountDAO();
    }

    
    
    public CourseDetailsResultDTO getCourseWithChaptersAndLessons(String courseIdStr) {
        CourseDetailsResultDTO result = new CourseDetailsResultDTO();

        try {
            // Kiểm tra courseId
            if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
                result.setSuccess(false);
                result.setErrorMessage("ID khóa học không hợp lệ.");
                return result;
            }

            Long courseId = Long.parseLong(courseIdStr);
            Course course = courseDAO.findById(courseId);

            if (course == null) {
                result.setSuccess(false);
                result.setErrorMessage("Không tìm thấy khóa học.");
                return result;
            }

            // Lấy danh sách chương
            List<Chapter> chapters = chapterDAO.findByCourseId(courseId);
            for (Chapter chapter : chapters) {
                // Lấy danh sách bài học cho mỗi chương
                List<Lesson> lessons = lessonDAO.findByChapterId(chapter.getChapterId());
                for (Lesson lesson : lessons) {
                    // Gán file media cho mỗi bài học
                    lesson.setFileMedias(fileMediaDAO.findByLessonId(lesson.getLessonId()));
                }
                chapter.setLessons(lessons);
            }
            course.setChapters(chapters);

            result.setCourse(course);
            result.setSuccess(true);
            result.setMessage("Lấy thông tin khóa học thành công.");
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setErrorMessage("Định dạng ID khóa học không hợp lệ: " + courseIdStr);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi khi tải thông tin khóa học: " + e.getMessage());
        }

        return result;
    }
    
    public CreateCourseResultDTO getAllCategories(Long accountId) {
        CreateCourseResultDTO result = new CreateCourseResultDTO();

        try {
            // Kiểm tra quyền giáo viên
            if (!accountDAO.isTeacher(accountId)) {
                result.setSuccess(false);
                result.setErrorMessage("Bạn không có quyền tạo khóa học.");
                return result;
            }

            // Lấy danh sách danh mục
            CategoryDAO categoryDAO = CategoryDAO.getInstance();
            List<Category> categories = categoryDAO.findAll();
            result.setCategories(categories);
            result.setSuccess(true);
            result.setMessage("Lấy danh sách danh mục thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi khi lấy danh sách danh mục: " + e.getMessage());
        }

        return result;
    }

    public CreateCourseResultDTO createCourse(Long accountId, String title, String priceStr, String thumbnail,
                                             String categoryIdStr, String descriptionContent, String[] applicableDaysArray) {
        CreateCourseResultDTO result = new CreateCourseResultDTO();

        try {
            // Kiểm tra quyền giáo viên
            if (!accountDAO.isTeacher(accountId)) {
                result.setSuccess(false);
                result.setErrorMessage("Bạn không có quyền tạo khóa học.");
                CategoryDAO categoryDAO = CategoryDAO.getInstance();
                result.setCategories(categoryDAO.findAll());
                return result;
            }

            // Validate dữ liệu
            if (title == null || title.trim().isEmpty() || priceStr == null || categoryIdStr == null || descriptionContent == null) {
                result.setSuccess(false);
                result.setErrorMessage("Tất cả các trường đều bắt buộc.");
                CategoryDAO categoryDAO = CategoryDAO.getInstance();
                result.setCategories(categoryDAO.findAll());
                return result;
            }

            // Tạo đối tượng Course
            Course course = new Course();
            course.setTitle(title);
            try {
                course.setPrice(new BigDecimal(priceStr));
            } catch (NumberFormatException e) {
                result.setSuccess(false);
                result.setErrorMessage("Định dạng giá không hợp lệ.");
                CategoryDAO categoryDAO = CategoryDAO.getInstance();
                result.setCategories(categoryDAO.findAll());
                return result;
            }
            course.setThumbnail(thumbnail);

            // Gán Category
            CategoryDAO categoryDAO = CategoryDAO.getInstance();
            Category category = categoryDAO.findById(Long.parseLong(categoryIdStr));
            if (category == null) {
                result.setSuccess(false);
                result.setErrorMessage("Danh mục được chọn không hợp lệ.");
                result.setCategories(categoryDAO.findAll());
                return result;
            }
            course.setCategory(category);

            // Lấy Teacher
            Teacher teacher = accountDAO.getTeacherByAccountId(accountId);
            if (teacher == null) {
                result.setSuccess(false);
                result.setErrorMessage("Không tìm thấy giáo viên cho tài khoản này.");
                result.setCategories(categoryDAO.findAll());
                return result;
            }
            course.setTeacher(teacher);

            // Tạo Description
            Description description = new Description();
            description.setContent(descriptionContent);

            // Xử lý applicableDays
            Set<ScheduleDay> applicableDays = new HashSet<>();
            if (applicableDaysArray != null) {
                for (String day : applicableDaysArray) {
                    try {
                        applicableDays.add(ScheduleDay.valueOf(day));
                    } catch (IllegalArgumentException e) {
                        result.setSuccess(false);
                        result.setErrorMessage("Ngày lịch không hợp lệ: " + day);
                        result.setCategories(categoryDAO.findAll());
                        return result;
                    }
                }
            }
            description.getApplicableDays().addAll(applicableDays);
            course.setDescription(description);

            // Lưu Course
            boolean saved = courseDAO.saveCourse(course);
            if (!saved) {
                result.setSuccess(false);
                result.setErrorMessage("Tạo khóa học thất bại.");
                result.setCategories(categoryDAO.findAll());
                return result;
            }

            result.setSuccess(true);
            result.setMessage("Khóa học được tạo thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi khi tạo khóa học: " + e.getMessage());
            CategoryDAO categoryDAO = CategoryDAO.getInstance();
            result.setCategories(categoryDAO.findAll());
        }

        return result;
    }
    
    
    public JoinCourseResultDTO joinCourse(Long accountId, String courseIdStr) {
        JoinCourseResultDTO result = new JoinCourseResultDTO();

        try {
            // Kiểm tra sinh viên
            Student student = accountDAO.getStudentByAccountId(accountId);
            if (student == null) {
                result.setSuccess(false);
                result.setErrorMessage("Bạn phải là sinh viên để tham gia khóa học.");
                return result;
            }

            // Kiểm tra courseId
            if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
                result.setSuccess(false);
                result.setErrorMessage("ID khóa học không hợp lệ.");
                return result;
            }

            Long courseId = Long.parseLong(courseIdStr);
            Course course = courseDAO.findById(courseId);
            if (course == null) {
                result.setSuccess(false);
                result.setErrorMessage("Không tìm thấy khóa học.");
                return result;
            }

            // Tạo và lưu đơn hàng
            Order order = new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setTotalPrice(course.getPrice());
            order.setStudent(student);
            order.setCourse(course);

            boolean saved = orderDAO.saveOrder(order);
            if (!saved) {
                result.setSuccess(false);
                result.setErrorMessage("Tham gia khóa học thất bại.");
                return result;
            }

            result.setSuccess(true);
            result.setMessage("Tham gia khóa học thành công: " + course.getTitle());
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setErrorMessage("ID khóa học không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi khi tham gia khóa học: " + e.getMessage());
        }

        return result;
    }
    
    public PurchasedCoursesResultDTO getPurchasedCourses(Long accountId) {
        PurchasedCoursesResultDTO result = new PurchasedCoursesResultDTO();

        try {
            // Lấy Student từ accountId
            Student student = accountDAO.getStudentByAccountId(accountId);
            if (student == null) {
                result.setSuccess(false);
                result.setErrorMessage("Tài khoản sinh viên không hợp lệ.");
                return result;
            }

            // Lấy danh sách Order của Student
            List<Order> orders = orderDAO.findByStudentId(accountId);
            // Lấy danh sách khóa học đã thanh toán
            List<Course> purchasedCourses = courseDAO.findPaidCourse(orders);

            result.setPurchasedCourses(purchasedCourses);
            result.setSuccess(true);
            result.setMessage("Lấy danh sách khóa học đã mua thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi khi tải danh sách khóa học: " + e.getMessage());
            result.setPurchasedCourses(new ArrayList<>());
        }

        return result;
    }
    
    public OrderPaymentResultDTO createOrderAndPayment(Long studentId, String courseIdStr) {
        OrderPaymentResultDTO result = new OrderPaymentResultDTO();

        try {
            // Kiểm tra courseId
            if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
                result.setSuccess(false);
                result.setErrorMessage("ID khóa học bị thiếu.");
                return result;
            }

            Long courseId = Long.parseLong(courseIdStr);
            Course course = courseDAO.findById(courseId);
            if (course == null) {
                result.setSuccess(false);
                result.setErrorMessage("Không tìm thấy khóa học với ID: " + courseId);
                return result;
            }

            // Kiểm tra sinh viên
            Student student = studentDAO.findById(studentId);
            if (student == null) {
                result.setSuccess(false);
                result.setErrorMessage("Tài khoản sinh viên không hợp lệ.");
                return result;
            }

            // Tạo đơn hàng
            Order order = new Order(LocalDateTime.now(), course.getPrice(), student, course);
            orderDAO.saveOrder(order);

            // Tạo thanh toán
            Payment payment = new Payment(
                    course.getPrice(), LocalDateTime.now(), PaymentMethod.MOMO, PaymentStatus.PENDING, order
            );
            paymentDAO.savePayment(payment);

            // Thiết lập kết quả
            result.setOrder(order);
            result.setPayment(payment);
            result.setCourse(course);
            result.setSuccess(true);
            result.setMessage("Đơn hàng và thanh toán được tạo thành công.");
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setErrorMessage("Định dạng ID khóa học không hợp lệ: " + courseIdStr);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi không mong muốn khi xử lý: " + e.getMessage());
        }

        return result;
    }

    public OrderPaymentResultDTO updatePaymentStatus(Long paymentId) {
        OrderPaymentResultDTO result = new OrderPaymentResultDTO();

        try {
            // Tìm thanh toán
            Payment payment = paymentDAO.findById(paymentId);
            if (payment == null) {
                result.setSuccess(false);
                result.setErrorMessage("Không tìm thấy thanh toán với ID: " + paymentId);
                return result;
            }

            // Kiểm tra trạng thái
            if (payment.getStatus() != PaymentStatus.PENDING) {
                result.setSuccess(false);
                result.setErrorMessage("Trạng thái thanh toán đã là " + payment.getStatus() + ". Không thể cập nhật.");
                return result;
            }

            // Cập nhật trạng thái
            payment.setStatus(PaymentStatus.PAID);
            paymentDAO.savePayment(payment);

            result.setPayment(payment);
            result.setSuccess(true);
            result.setMessage("Trạng thái thanh toán được cập nhật thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi không mong muốn khi cập nhật thanh toán: " + e.getMessage());
        }

        return result;
    }
    
    public CourseSearchResultDTO searchCoursesByTitleSortedByPrice(String searchKeyword, String sortOrder) {
        CourseSearchResultDTO result = new CourseSearchResultDTO();

        // Xử lý sortOrder mặc định
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "asc";
        }

        try {
            // Tìm khóa học theo tiêu đề và sắp xếp theo giá
            List<Course> courses = courseDAO.findCoursesByTitleSortedByPrice(searchKeyword, sortOrder);
            result.setCourses(courses);
            result.setSuccess(true);
            result.setShowCourses(true);
            result.setMessage("Tìm kiếm khóa học thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi khi tìm kiếm khóa học: " + e.getMessage());
            result.setCourses(new ArrayList<>());
            result.setShowCourses(true);
        }

        return result;
    }

    public CourseSearchResultDTO getAllCourses() {
        CourseSearchResultDTO result = new CourseSearchResultDTO();

        try {
            // Lấy tất cả khóa học
            List<Course> courses = courseDAO.findAll();
            if (courses == null) {
                courses = new ArrayList<>();
            }
            result.setCourses(courses);
            result.setSuccess(true);
            result.setShowCourses(true);
            result.setMessage("Lấy danh sách khóa học thành công.");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setErrorMessage("Lỗi khi lấy danh sách khóa học: " + e.getMessage());
            result.setCourses(new ArrayList<>());
            result.setShowCourses(true);
        }

        return result;
    }
    
    public Course getCourseWithDetails(Long courseId) {
        if (courseId == null) {
            return null; // Hoặc ném IllegalArgumentException
        }
        Course course = courseDAO.findById(courseId);

        if (course != null) {
            List<Chapter> chapters = chapterDAO.findByCourseId(courseId);
            for (Chapter chapter : chapters) {
                List<Lesson> lessons = lessonDAO.findByChapterId(chapter.getChapterId());
                for (Lesson lesson : lessons) {
                    lesson.setFileMedias(fileMediaDAO.findByLessonId(lesson.getLessonId()));
                }
                chapter.setLessons(lessons);
            }
            course.setChapters(chapters);
        }
        return course;
    }

    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    public CourseUpdateResultDTO updateCourse(Long courseId, String title, String priceParam, String thumbnail, Long categoryId, String descriptionContent) {
        CourseUpdateResultDTO result = new CourseUpdateResultDTO();

        // Kiểm tra đầu vào
        if (courseId == null || categoryId == null) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu Course ID hoặc Category ID.");
            return result;
        }

        // Chuyển đổi giá
        BigDecimal price;
        try {
            price = (priceParam != null && !priceParam.isEmpty()) ? new BigDecimal(priceParam) : BigDecimal.ZERO;
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setErrorMessage("Định dạng giá không hợp lệ.");
            return result;
        }

        // Lấy khóa học
        Course course = courseDAO.findById(courseId);
        if (course == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy khóa học.");
            return result;
        }

        // Lấy danh mục
        Category category = categoryDAO.findById(categoryId);
        if (category == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy danh mục.");
            return result;
        }

        // Cập nhật thông tin khóa học
        course.setTitle(title);
        course.setPrice(price);
        course.setThumbnail(thumbnail);
        course.setCategory(category);
        if (course.getDescription() != null) {
            course.getDescription().setContent(descriptionContent);
        }

        // Lưu khóa học
        boolean success = courseDAO.saveCourse(course);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Cập nhật khóa học thất bại.");
            return result;
        }

        // Lấy chi tiết khóa học đã cập nhật
        course = getCourseWithDetails(courseId);
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Cập nhật khóa học thành công.");
        return result;
    }

    public ChapterAddResultDTO addChapter(Long courseId, String title, Integer chapterOrder) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (courseId == null || title == null || title.isEmpty() || chapterOrder == null) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin khóa học, tiêu đề hoặc thứ tự chương.");
            return result;
        }

        // Lấy khóa học
        Course course = courseDAO.findById(courseId);
        if (course == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy khóa học.");
            return result;
        }

        // Tạo và thiết lập chương mới
        Chapter chapter = new Chapter();
        chapter.setTitle(title);
        chapter.setChapterOrder(chapterOrder);
        chapter.setCourse(course);

        // Lưu chương
        boolean success = chapterDAO.saveChapter(chapter);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Thêm chương thất bại.");
            return result;
        }

        // Lấy chi tiết khóa học đã cập nhật
        course = getCourseWithDetails(courseId);
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Thêm chương thành công.");
        return result;
    }

    public ChapterAddResultDTO updateChapter(Long chapterId, String title, Integer chapterOrder) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (chapterId == null || title == null || title.isEmpty() || chapterOrder == null) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin chapter ID, tiêu đề hoặc thứ tự chương.");
            return result;
        }

        // Lấy chương
        Chapter chapter = chapterDAO.findById(chapterId);
        if (chapter == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy chương.");
            return result;
        }

        // Cập nhật thông tin chương
        chapter.setTitle(title);
        chapter.setChapterOrder(chapterOrder);

        // Lưu chương
        boolean success = chapterDAO.saveChapter(chapter);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Cập nhật chương thất bại.");
            return result;
        }

        // Lấy khóa học với chi tiết
        Course course = getCourseWithDetails(chapter.getCourse().getCourseId());
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Cập nhật chương thành công.");
        return result;
    }
    
    public ChapterAddResultDTO deleteChapter(Long chapterId, Long courseId) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (chapterId == null || courseId == null) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin chapter ID hoặc course ID.");
            return result;
        }

        // Xóa chương
        boolean success = chapterDAO.deleteChapter(chapterId);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Xóa chương thất bại.");
            return result;
        }

        // Lấy khóa học với chi tiết
        Course course = getCourseWithDetails(courseId);
        if (course == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy khóa học.");
            return result;
        }

        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Xóa chương thành công.");
        return result;
    }
    
    public ChapterAddResultDTO addLesson(Long chapterId, String lessonId, String title, Integer lessonIndex, String description) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (chapterId == null || title == null || title.isEmpty() || lessonIndex == null) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin chapter ID, tiêu đề hoặc thứ tự bài học.");
            return result;
        }

        // Lấy chương
        Chapter chapter = chapterDAO.findById(chapterId);
        if (chapter == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy chương.");
            return result;
        }

        // Tạo và thiết lập bài học mới
        Lesson lesson = new Lesson(title, description, lessonIndex, chapter);
        lesson.setLessonId(lessonId != null && !lessonId.isEmpty() ? lessonId : UUID.randomUUID().toString());
        

        // Lưu bài học
        boolean success = lessonDAO.saveLesson(lesson);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Thêm bài học thất bại.");
            return result;
        }

        // Lấy khóa học với chi tiết
        Course course = getCourseWithDetails(chapter.getCourse().getCourseId());
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Thêm bài học thành công.");
        return result;
    }

    public ChapterAddResultDTO updateLesson(String lessonId, String title, Integer lessonIndex, String description, Long chapterId) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (lessonId == null || lessonId.isEmpty() || title == null || title.isEmpty() || lessonIndex == null || chapterId == null) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin lesson ID, tiêu đề, thứ tự bài học hoặc chapter ID.");
            return result;
        }

        // Lấy bài học và chương
        Lesson lesson = lessonDAO.findById(lessonId);
        Chapter chapter = chapterDAO.findById(chapterId);
        if (lesson == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy bài học.");
            return result;
        }
        if (chapter == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy chương.");
            return result;
        }

        // Cập nhật thông tin bài học
        lesson.setTitle(title);
        lesson.setLessonIndex(lessonIndex);
        lesson.setDescription(description);
        lesson.setChapter(chapter);

        // Lưu bài học
        boolean success = lessonDAO.saveLesson(lesson);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Cập nhật bài học thất bại.");
            return result;
        }

        // Lấy khóa học với chi tiết
        Course course = getCourseWithDetails(chapter.getCourse().getCourseId());
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Cập nhật bài học thành công.");
        return result;
    }

    public ChapterAddResultDTO deleteLesson(String lessonId, Long chapterId) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (lessonId == null || lessonId.isEmpty() || chapterId == null) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin lesson ID hoặc chapter ID.");
            return result;
        }

        // Xóa bài học
        boolean success = lessonDAO.deleteLesson(lessonId);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Xóa bài học thất bại.");
            return result;
        }

        // Lấy chương để truy xuất courseId
        Chapter chapter = chapterDAO.findById(chapterId);
        if (chapter == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy chương.");
            return result;
        }

        // Lấy khóa học với chi tiết
        Course course = getCourseWithDetails(chapter.getCourse().getCourseId());
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Xóa bài học thành công.");
        return result;
    }

    public ChapterAddResultDTO addFileMedia(String lessonId, Long chapterId, String fileId, String fileName, String fileType, String fileUrl) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (lessonId == null || lessonId.isEmpty() || chapterId == null || fileName == null || fileName.isEmpty() || fileType == null || fileType.isEmpty() || fileUrl == null || fileUrl.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin lesson ID, chapter ID, tên file, loại file hoặc URL file.");
            return result;
        }

        // Lấy bài học và chương
        Lesson lesson = lessonDAO.findById(lessonId);
        Chapter chapter = chapterDAO.findById(chapterId);
        if (lesson == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy bài học.");
            return result;
        }
        if (chapter == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy chương.");
            return result;
        }

        // Tạo và thiết lập file media mới
        FileMedia fileMedia = new FileMedia(fileId, fileName, fileType, fileUrl, lesson);

        // Lưu file media
        boolean success = fileMediaDAO.saveFileMedia(fileMedia);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Thêm file media thất bại.");
            return result;
        }

        // Lấy khóa học với chi tiết
        Course course = getCourseWithDetails(chapter.getCourse().getCourseId());
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Thêm file media thành công.");
        return result;
    }

    public ChapterAddResultDTO updateFileMedia(Long fileMediaId, String lessonId, Long chapterId, String fileName, String fileType, String fileUrl) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (fileMediaId == null || lessonId == null || lessonId.isEmpty() || chapterId == null || fileName == null || fileName.isEmpty() || fileType == null || fileType.isEmpty() || fileUrl == null || fileUrl.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin file media ID, lesson ID, chapter ID, tên file, loại file hoặc URL file.");
            return result;
        }

        // Lấy file media, bài học và chương
        FileMedia fileMedia = fileMediaDAO.findById(fileMediaId);
        Lesson lesson = lessonDAO.findById(lessonId);
        Chapter chapter = chapterDAO.findById(chapterId);
        if (fileMedia == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy file media.");
            return result;
        }
        if (lesson == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy bài học.");
            return result;
        }
        if (chapter == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy chương.");
            return result;
        }

        // Cập nhật thông tin file media
        fileMedia.setFileName(fileName);
        fileMedia.setFileType(fileType);
        fileMedia.setFileUrl(fileUrl);
        fileMedia.setLesson(lesson);

        // Lưu file media
        boolean success = fileMediaDAO.saveFileMedia(fileMedia);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Cập nhật file media thất bại.");
            return result;
        }

        // Lấy khóa học với chi tiết
        Course course = getCourseWithDetails(chapter.getCourse().getCourseId());
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Cập nhật file media thành công.");
        return result;
    }

    public ChapterAddResultDTO deleteFileMedia(Long fileMediaId, String lessonId, Long chapterId) {
        ChapterAddResultDTO result = new ChapterAddResultDTO();

        // Kiểm tra đầu vào
        if (fileMediaId == null || lessonId == null || lessonId.isEmpty() || chapterId == null) {
            result.setSuccess(false);
            result.setErrorMessage("Thiếu thông tin file media ID, lesson ID hoặc chapter ID.");
            return result;
        }

        // Xóa file media
        boolean success = fileMediaDAO.deleteFileMedia(fileMediaId);
        if (!success) {
            result.setSuccess(false);
            result.setErrorMessage("Xóa file media thất bại.");
            return result;
        }

        // Lấy chương để truy xuất courseId
        Chapter chapter = chapterDAO.findById(chapterId);
        if (chapter == null) {
            result.setSuccess(false);
            result.setErrorMessage("Không tìm thấy chương.");
            return result;
        }

        // Lấy khóa học với chi tiết
        Course course = getCourseWithDetails(chapter.getCourse().getCourseId());
        result.setCourse(course);
        result.setSuccess(true);
        result.setMessage("Xóa file media thành công.");
        return result;
    }
    
}