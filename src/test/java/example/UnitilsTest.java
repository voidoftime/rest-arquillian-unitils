package example;

import example.util.DataBase;
import org.hamcrest.MatcherAssert;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.hamcrest.CoreMatchers.is;


public class UnitilsTest  extends BaseDbTest{
    @Test
    @DataSet("sample.xml")
    public void test(){
        DataSource datasource = DataBase.getDataSource();
        try (Connection conn = datasource.getConnection()) {
            System.out.println("GOT CONNECTION");
            DSLContext db = DSL.using(conn, SQLDialect.MYSQL);
            String clientName = db.select(DSL.field("name")).from("client").limit(1).fetchOneInto(String.class);
            MatcherAssert.assertThat(clientName, is("oleg"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
