/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.YogaClassDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author allan
 */
@Entity
@NamedQuery(name = "YogaClass.deleteAllRows", query = "DELETE from YogaClass")
public class YogaClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String courseTime;
    private int room;
    @ManyToOne
    private Course course;

    public YogaClass() {
    }

    public YogaClass(String courseTime, int room){
        this.courseTime = courseTime;
        this.room = room;
    }
    
    public YogaClass(int id, String  courseTime, int room){
        this.id = id;
        this.courseTime = courseTime;
        this.room = room;
    }
    public YogaClass(int id, String courseTime, int room, Course course) {
        this.id = id;
        this.courseTime = courseTime;
        this.room = room;
        this.course = course;
    }
    
    public YogaClass(YogaClassDTO ycdto) {
        this.id = ycdto.getId();
        this.courseTime = ycdto.getCourseTime();
        this.room = ycdto.getRoom();
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String  getCourseTime() {
        return courseTime;
    }

    public void setcourseTime(String  courseTime) {
        this.courseTime = courseTime;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.courseTime);
        hash = 29 * hash + this.room;
        hash = 29 * hash + Objects.hashCode(this.course);
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
        final YogaClass other = (YogaClass) obj;
        if (this.room != other.room) {
            return false;
        }
        if (!Objects.equals(this.courseTime, other.courseTime)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "YogaClass{" + "id=" + id + ", courseTime=" + courseTime + ", room=" + room + ", course=" + course + '}';
    }

    
    
    
    
}
