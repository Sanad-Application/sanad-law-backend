package com.zkrallah.sanad.entity;

import java.util.ArrayList;
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
@Table(name = "lawyers")
public class Lawyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column
    private double hourlyRate = 0.0;

    @Column
    private boolean isActive = false;

    @Column
    private String location;

    @Column
    private int experienceYears;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "license_id", referencedColumnName = "id")
    private License license;

    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Experience> experiences = new ArrayList<>();

    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Rating> ratings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "lawyer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Request> clientRequests = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "lawyer_tags", joinColumns = @JoinColumn(name = "lawyer_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @OrderBy("id ASC")
    private List<Tag> tags = new ArrayList<>();

    @Transient
    private double avgRate;

    public double getAvgRate() {
        if (ratings.isEmpty()) return 0;

        double numRatings = 0.0;
        double totalRatings = 0.0;

        for (Rating rating : ratings) {
            numRatings++;
            totalRatings += rating.getRating();
        }

        return numRatings / totalRatings;
    }
}
