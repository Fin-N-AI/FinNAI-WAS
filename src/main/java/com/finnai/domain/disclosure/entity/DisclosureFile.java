package com.finnai.domain.disclosure.entity;

import com.finnai.domain.disclosure.enums.DisclosureFileType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


/**
 * DisclosureList여기서 가져온 거에 /api/document.xml보내야함.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "disclosure_file",
        uniqueConstraints = @UniqueConstraint(columnNames = {"disclosure_id", "file_type", "file_url"})
)
public class DisclosureFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disclosure_id", nullable = false)
    private DisclosureList disclosure;

    // 파일 타입 (내부 enum: HTML/PDF/XBRL, DART 첨부파일 포맷과 매핑)
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private DisclosureFileType fileType;

    // 파일 URL (DART 원본 또는 변환 파일 저장 위치)
    private String fileUrl;

    // 원문/파싱된 내용 (내부 저장)
    @Column(columnDefinition = "TEXT")
    private String rawContent;

    private LocalDateTime createdAt;
}
