package com.finnai.domain.usercompany.entity;

import com.finnai.domain.usercompany.enums.UserCompanyFileType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_company_file")
public class UserCompanyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_company_id")
    private UserCompany userCompany;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "file_type")
    private UserCompanyFileType fileType;


    private String fileUrl;

    @Column(columnDefinition = "TEXT")
    private String rawContent;

    private LocalDateTime createdAt;
}
