package Endpoints;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("")
@Singleton
public class About {
    @GET
    public InputStream aboutMe() {
        File content = new File("notified/index.jsp");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try{
            return new FileInputStream(content);
        }

        catch(FileNotFoundException e){
            return new ByteArrayInputStream("Error".getBytes(StandardCharsets.UTF_8));
        }
    }
}
