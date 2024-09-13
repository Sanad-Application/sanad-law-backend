package com.zkrallah.sanad.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "raitngs")
class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int rating;

    @Column
    private String type;

    @Column
    private String tag;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lawyer_id", referencedColumnName = "id")
    private Lawyer lawyer;
}
