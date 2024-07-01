package ChurchManagementSystem.CMS.modules.news.entity;

import ChurchManagementSystem.CMS.core.enums.Category;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "master_news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Nullable
    @Column(name = "thumbnail", columnDefinition = "text")
    private String thumbnail; //image .png

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @CreationTimestamp
    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
}
