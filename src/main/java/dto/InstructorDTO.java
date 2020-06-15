/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;
import entities.Instructor;

/**
 *
 * @author allan
 */
public class InstructorDTO {
    private int id;
    private String name;
    private int salary;
    private int courseID;

    public InstructorDTO(String name, int salary, int courseID) {
        this.name = name;
        this.salary = salary;
        this.courseID = courseID;
    }

    public InstructorDTO(String name) {
        this.name = name;
    }
    
    public InstructorDTO(Instructor i){
       this.name = i.getName();
        this.salary = i.getSalary(); 
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    
    

    @Override
    public String toString() {
        return "InstructorDTO{" + "id=" + id + ", name=" + name + ", salary=" + salary + '}';
    }
    
    
}
