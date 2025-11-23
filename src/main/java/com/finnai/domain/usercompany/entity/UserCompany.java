package com.finnai.domain.usercompany.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.finnai.domain.user.entity.UserAccount;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_company")
public class UserCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount user;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String homepageUrl;
    private String address;
    private LocalDateTime foundedDate;
    private String phoneNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
