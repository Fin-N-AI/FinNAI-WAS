package com.finnai.domain.disclosure.entity;

import com.finnai.domain.disclosure.enums.DisclosureFileType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "disclosure_file")
public class DisclosureFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disclosure_id", nullable = false)
    private DisclosureList disclosure;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private DisclosureFileType fileType;

    private String fileUrl;

    @Column(columnDefinition = "TEXT")
    private String rawContent;

    private LocalDateTime createdAt;
}
