/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.CourseDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author allan
 */
@Entity
@NamedQuery(name = "Course.deleteAllRows", query = "DELETE from Course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseName;
    private String description;
    private int maxParticipants;
    private double price;
    @OneToMany(
        mappedBy = "course",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<YogaClass> classes;
    @ManyToMany
    private Set<Instructor> instructors;

    public Course() {
    }

    public Course(CourseDTO cdto) {
        this.id = cdto.getId();
        this.courseName = cdto.getCourseName();
        this.description = cdto.getDescription();
        this.maxParticipants = cdto.getMaxParticipants();
        this.price = cdto.getPrice();
        this.classes = new ArrayList<>();
        this.instructors = new HashSet<>();
    }

    public Course(String courseName, String description, int maxParticipants, double price) {
        this.courseName = courseName;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.price = price;
        this.classes = new ArrayList<>();
        this.instructors = new HashSet<>();
        

    }

    public Course(int id, String courseName, String description, int maxParticipants, double price) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.price = price;
        this.classes = new ArrayList<>();
        this.instructors = new HashSet<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<YogaClass> getClasses() {
        return classes;
    }

    public void setClasses(List<YogaClass> classes) {
        this.classes = classes;
    }

    public Set<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<Instructor> instructors) {
        this.instructors = instructors;
    }
    
    public void addClasses(YogaClass yc) {
        classes.add(yc);
    }
    
    public void addInstructor(Instructor i){
        instructors.add(i);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.courseName);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + this.maxParticipants;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.classes);
        hash = 71 * hash + Objects.hashCode(this.instructors);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (this.maxParticipants != other.maxParticipants) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.courseName, other.courseName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.classes, other.classes)) {
            return false;
        }
        if (!Objects.equals(this.instructors, other.instructors)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", courseName=" + courseName + ", description=" + description + ", maxParticipants=" + maxParticipants + ", price=" + price + ", classes=" + classes + ", instructors=" + instructors + '}';
    }

    

}
