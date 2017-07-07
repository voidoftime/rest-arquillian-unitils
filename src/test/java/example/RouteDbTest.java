package example;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;

import static org.hamcrest.CoreMatchers.is;

public class RouteDbTest extends BaseDbTest {
    @Test
    @DataSet("sample.xml")
    @ExpectedDataSet("sample-result.xml")
    public void when(){
        new DbTest().testDb();
    }
    @Test
    @DataSet("sample.xml")
    public void test_getById(){
        MatcherAssert.assertThat(new DbTest().getById(2000), is("oleg"));
    }
}
