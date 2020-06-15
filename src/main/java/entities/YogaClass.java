/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.YogaClassDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    @Temporal(TemporalType.DATE)
    private Date date;
    private int room;
    @ManyToOne
    private Course course;

    public YogaClass() {
    }

    public YogaClass(Date date, int room){
        this.date = date;
        this.room = room;
    }
    
    public YogaClass(int id, Date date, int room){
        this.id = id;
        this.date = date;
        this.room = room;
    }
    public YogaClass(int id, Date date, int room, Course course) {
        this.id = id;
        this.date = date;
        this.room = room;
        this.course = course;
    }
    
    public YogaClass(YogaClassDTO ycdto) {
        this.id = ycdto.getId();
        this.date = ycdto.getDate();
        this.room = ycdto.getRoom();
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRoom() {
        return room;
    }

    public void setRoome(int room) {
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
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + this.room;
        hash = 97 * hash + Objects.hashCode(this.course);
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
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "YogaClass{" + "id=" + id + ", date=" + date + ", room=" + room + ", course=" + course + '}';
    }

    
    
    
    
}
