package Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "lessons")
public class Lesson implements Serializable {

    @Id
    
    private String lessonId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer lessonIndex;

    // Nhiều Lesson liên kết tới một Chapter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    // Một Lesson có nhiều FileMedia
  @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FileMedia> fileMedias = new ArrayList<>();

    // Constructor không tham số
    public Lesson() {
    }

    // Constructor với các trường cơ bản và mối quan hệ ManyToOne
    public Lesson( String title, String description, Integer lessonIndex, Chapter chapter) {
        
        this.title = title;
        this.description = description;
        this.lessonIndex = lessonIndex;
        this.chapter = chapter;
    }

    // Getters và Setters
    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getLessonIndex() {
        return lessonIndex;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public List<FileMedia> getFileMedias() {
        return fileMedias;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLessonIndex(Integer lessonIndex) {
        this.lessonIndex = lessonIndex;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
     public void setFileMedias(List<FileMedia> fileMedias) {
        this.fileMedias.clear();
        if (fileMedias != null) {
            for (FileMedia fileMedia : fileMedias) {
                fileMedia.setLesson(this);
                this.fileMedias.add(fileMedia);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(lessonId, lesson.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }
}
