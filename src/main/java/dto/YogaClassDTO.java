/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.YogaClass;
import java.util.Date;

/**
 *
 * @author allan
 */
public class YogaClassDTO {
    private int id;
    private Date date;
    private int room;
    private int courseID;

    public YogaClassDTO(Date date, int room, int courseID) {
        this.date = date;
        this.room = room;
        this.courseID = courseID;
    }

    
    
    public YogaClassDTO(YogaClass yc) {
        this.id = yc.getId();
        this.date = yc.getDate();
        this.room = yc.getRoom();
        this.courseID = (yc.getCourse() != null ? yc.getCourse().getId() : 0);
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getRoom() {
        return room;
    }

    public int getCourseID() {
        return courseID;
    }

    @Override
    public String toString() {
        return "YogaClassDTO{" + "id=" + id + ", date=" + date + ", room=" + room + ", courseID=" + courseID + '}';
    }

    
    
    
    
    
}
