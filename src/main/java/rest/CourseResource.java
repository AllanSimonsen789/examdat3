package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CourseDTO;
import facades.CourseFacade;
import java.sql.SQLException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

//Todo Remove or change relevant parts before ACTUAL use
@Path("course")
public class CourseResource {

    private static EntityManagerFactory EMF
            = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final CourseFacade FACADE = CourseFacade.getCourseFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getCourseCount();
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }
    
    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCourses() {
        String returnString = GSON.toJson(FACADE.getAllCourses());
        return Response.ok(returnString).build();
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addCourse(String body) {
        String returnRating = GSON.toJson(FACADE.addCourse(GSON.fromJson(body, CourseDTO.class)));
        return Response.ok(returnRating).build();
    }

    @PUT
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editCourse(String body) throws SQLException {
        String returnRating = GSON.toJson(FACADE.editCourse(GSON.fromJson(body, CourseDTO.class)));
        return Response.ok(returnRating).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteCourse(@PathParam("id") int id) {
        try{
            FACADE.deleteCourse(id);
            return Response.ok("Deleted").build();
        }catch(SQLException e){
            return Response.noContent().build();
        }
    }

}
