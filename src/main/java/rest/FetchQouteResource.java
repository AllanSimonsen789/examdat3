package rest;

import com.google.gson.Gson;
import dto.CompleteDTO;
import facades.FetchFacade;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("fetch")
public class FetchQouteResource {
    private Gson gson = new Gson();
    private FetchFacade facade = FetchFacade.getFetchFacade();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getQoutes() throws IOException, InterruptedException {
        CompleteDTO cDTO = facade.runParalel();
        return gson.toJson(cDTO);
    }
}
