/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.YogaClass;

/**
 *
 * @author allan
 */
public class YogaClassDTO {
    private int id;
    private String courseTime;
    private int room;
    private int courseID;

    public YogaClassDTO(String courseTime, int room, int courseID) {
        this.courseTime = courseTime;
        this.room = room;
        this.courseID = courseID;
    }
    
    public YogaClassDTO(int id, String courseTime, int room) {
        this.id = id;
        this.courseTime = courseTime;
        this.room = room;
    }

    
    
    public YogaClassDTO(YogaClass yc) {
        this.id = yc.getId();
        this.courseTime = yc.getCourseTime();
        this.room = yc.getRoom();
        this.courseID = (yc.getCourse() != null ? yc.getCourse().getId() : 0);
    }

    public int getId() {
        return id;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public int getRoom() {
        return room;
    }

    public int getCourseID() {
        return courseID;
    }

    @Override
    public String toString() {
        return "YogaClassDTO{" + "id=" + id + ", courseTime=" + courseTime + ", room=" + room + ", courseID=" + courseID + '}';
    }

    
    
    
    
    
}
