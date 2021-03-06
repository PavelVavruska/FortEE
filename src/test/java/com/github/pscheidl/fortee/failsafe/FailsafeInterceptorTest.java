package com.github.pscheidl.fortee.failsafe;

import java.util.Optional;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Pscheidl
 */
@RunWith(Arquillian.class)
public class FailsafeInterceptorTest {

    @Inject
    private FailingBean failingBean;

    @Inject
    private NotFailingBean notFailingBean;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.github.pscheidl.fortee")
                .addAsWebInfResource("beans.xml");
    }

    @Test
    public void testFailingBean() {
        Optional<String> emptyOptional = failingBean.throwError();
        Assert.assertNotNull(emptyOptional);
        Assert.assertFalse(failingBean.throwError().isPresent());
    }

    @Test
    public void testNotFailingBean() {
        Optional<String> optionalWithStringInside = notFailingBean.returnOptionalWithStringInside();
        Assert.assertNotNull(optionalWithStringInside);
        Assert.assertTrue(optionalWithStringInside.isPresent());
    }

}
