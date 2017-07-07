package example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URL;

@RunWith(Arquillian.class)
public class DbTestTest extends BaseDbTest{
    @ArquillianResource
    private URL baseURL;

    @Deployment(testable = false)
    public static WebArchive createTestArchive() {
        return ShrinkWrap
                .create(WebArchive.class, "ROOT.war")
                .addClasses(DbTest.class)
                .addAsLibraries(
                        DependencyResolvers.use(MavenDependencyResolver.class)
                                .loadMetadataFromPom("pom.xml")
                                .goOffline()
                                .artifact("org.glassfish.jersey.containers:jersey-container-servlet-core").resolveAsFiles())
                .setWebXML("in-container-web.xml");
    }


    @Test
    @DataSet("sample.xml")
    @ExpectedDataSet("sample-result.xml")
    public void shouldBeAbleToInvokeServletInDeployedWebApp() throws Exception {
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target(baseURL + "/webapi");
        String responseMsg = target.path("db").request().get(String.class);
        Assert.assertEquals("master", responseMsg);

    }
    @Test
    @DataSet("sample.xml")
    public void test_getById() throws Exception {
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target(baseURL + "/webapi");
        String responseMsg = target.path("db").path("2000").request().get(String.class);
        Assert.assertEquals("oleg", responseMsg);

    }
}
