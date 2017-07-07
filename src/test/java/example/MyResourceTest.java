package example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;



import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class MyResourceTest
{
    @ArquillianResource
    private URL baseURL;

    // -------------------------------------------------------------------------------------||
    // Class Members -----------------------------------------------------------------------||
    // -------------------------------------------------------------------------------------||

    /**
     * Logger
     */
    private static final Logger log = Logger.getLogger(MyResourceTest.class.getName());

    // -------------------------------------------------------------------------------------||
    // Instance Members --------------------------------------------------------------------||
    // -------------------------------------------------------------------------------------||

    /**
     * Define the deployment
     */
    @Deployment(testable=false)
    public static WebArchive createTestArchive()
    {
        return ShrinkWrap
                .create(WebArchive.class, "ROOT.war")
                .addClasses(MyResource.class)
                .addAsLibraries(
                        DependencyResolvers.use(MavenDependencyResolver.class)
                                .loadMetadataFromPom("pom.xml")
                                .goOffline()
                                .artifact("org.glassfish.jersey.containers:jersey-container-servlet-core").resolveAsFiles())
                .setWebXML("in-container-web.xml");
    }


    @Test
    public void shouldBeAbleToInvokeServletInDeployedWebApp() throws Exception
    {
    	
    	
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target(baseURL+"/webapi");
        String responseMsg = target.path("myresource").request().get(String.class);
        Assert.assertEquals("Got it!", responseMsg);

    }
}