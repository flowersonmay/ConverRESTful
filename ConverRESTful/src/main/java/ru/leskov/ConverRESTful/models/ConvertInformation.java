package ru.leskov.ConverRESTful.models;


import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "information")
public class ConvertInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "input")
    private String input;

    @Column(name = "output")
    private String output;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public ConvertInformation(String input, String output, Date createdAt) {
        this.input = input;
        this.output = output;
        this.createdAt = createdAt;
    }

    public ConvertInformation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ConvertInfo{" +
                "id=" + id +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}