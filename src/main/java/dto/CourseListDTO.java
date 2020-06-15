/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;

/**
 *
 * @author allan
 */
public class CourseListDTO {
    private List<CourseDTO> courses;

    public CourseListDTO(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }
    

    @Override
    public String toString() {
        return "CourseListDTO{" + "courses=" + courses + '}';
    }
    
    
}
