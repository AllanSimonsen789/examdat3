/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Course;
import entities.YogaClass;
import java.util.ArrayList;
import java.util.List;

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
    private List<YogaClassDTO> classes;

    public CourseDTO(Course c) {
        this.id = c.getId();
        this.courseName = c.getCourseName();
        this.description = c.getDescription();
        this.maxParticipants = c.getMaxParticipants();
        this.price = c.getPrice();
        classes = new ArrayList<YogaClassDTO>();
        for(YogaClass yc : c.getClasses()){
            classes.add(new YogaClassDTO(yc));
        }
        
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
