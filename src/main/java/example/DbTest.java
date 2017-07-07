package example;

import example.util.DataBase;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;

@Path("/db")
public class DbTest {
    private DataSource datasource = DataBase.getDataSource();


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String testDb(){
//        String userName = "rps";
//        String password = "1374";
//        String url = "jdbc:mysql://127.0.0.1:3306/rps_sg";
        System.out.println("datasource="+datasource);
        try (Connection conn = datasource.getConnection()) {
            System.out.println("GOT CONNECTION");
            DSLContext db = DSL.using(conn, SQLDialect.MYSQL);
//            db.query("show tables").execute();
            db.update(DSL.table("client")).set(DSL.field("name"), "master").execute();
            String clientName = db.select(DSL.field("name")).from("client").limit(1).fetchOneInto(String.class);
            return clientName;
        }catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
    }
     @GET
     @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getById(@PathParam("id") int id){
        try (Connection conn = datasource.getConnection()) {
            System.out.println("GOT CONNECTION");
            DSLContext db = DSL.using(conn, SQLDialect.MYSQL);
            return db.select(DSL.field("name")).from("client")
                    .where(DSL.field("id").eq(id))
                    .limit(1)
                    .fetchOneInto(String.class);
        }catch (Exception e) {
            e.printStackTrace();
            return "error: " + e.getMessage();
        }
     }
}
