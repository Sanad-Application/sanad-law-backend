package com.zkrallah.sanad.entity;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String attachment;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Timestamp timestamp;

    @ElementCollection
    @CollectionTable(name = "request_keywords", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private Tag tag;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lawyer_id", referencedColumnName = "id")
    private Lawyer lawyer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Transient
    private String lawyerName;

    public String getLawyerName() {
        return lawyer.getUser().getFirstName() + lawyer.getUser().getLastName();
    }
}
