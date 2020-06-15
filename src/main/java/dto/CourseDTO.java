/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Course;

/**
 *
 * @author allan
 */
public class CourseDTO {
    private int id;
    private String courseName;
    private String description;
    private int maxParticipants;
    private double price;

    public CourseDTO(Course c) {
        this.id = c.getId();
        this.courseName = c.getCourseName();
        this.description = c.getDescription();
        this.maxParticipants = c.getMaxParticipants();
        this.price = c.getPrice();
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "CourseDTO{" + "id=" + id + ", courseName=" + courseName + ", Description=" + description + ", maxParticipants=" + maxParticipants + ", price=" + price + '}';
    }
    
    
}
