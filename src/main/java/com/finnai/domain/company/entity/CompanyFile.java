package com.finnai.domain.company.entity;

import com.finnai.domain.company.enums.CompanyFileType;
import com.finnai.domain.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "company_file")
public class CompanyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private UserAccount uploadedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "file_type")
    private CompanyFileType fileType;

    private String fileUrl;

    private String originalName;

    @Column(columnDefinition = "TEXT")
    private String rawContent;

    private LocalDateTime createdAt;
}
