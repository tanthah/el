package Model;

import jakarta.persistence.*; // Hoặc javax.persistence.* tùy phiên bản Jakarta EE/Java EE
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Cần import Objects cho equals/hashCode

@Entity
@Table(name = "chapters")
public class Chapter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapterId;

    @Column(nullable = false)
    private String title;

    private Integer chapterOrder; // Thứ tự trong khóa học

    // Nhiều Chapter thuộc về một Course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Một Chapter có nhiều Lesson (lưu ý tên "lessons" khớp với "chapter" trong Lesson)
    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("lessonIndex ASC")  // Sử dụng lessonIndex thay vì lessonOrder để đảm bảo tính chính xác
    private List<Lesson> lessons = new ArrayList<>(); // Khởi tạo ngay khi khai báo

    // Constructor không tham số
    public Chapter() {
        // Danh sách lessons đã được khởi tạo ở trên
    }

    // Constructor với các trường cơ bản và mối quan hệ ManyToOne
    public Chapter(Long chapterId, String title, Integer chapterOrder, Course course) {
        this.chapterId = chapterId;
        this.title = title;
        this.chapterOrder = chapterOrder;
        this.course = course;
    }

    // Getters và Setters
    public Long getChapterId() {
        return chapterId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public Course getCourse() {
        return course;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
     public void setLessons(List<Lesson> lessons) {
        this.lessons.clear();
        if (lessons != null) {
            for (Lesson lesson : lessons) {
                lesson.setChapter(this); // Đảm bảo mối quan hệ hai chiều
                this.lessons.add(lesson);
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(chapterId, chapter.chapterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chapterId);
    }
}
