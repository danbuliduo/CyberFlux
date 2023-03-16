package io.cyberflux.node.engine;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.node.engine.annotation.CyberInject;
import io.cyberflux.node.engine.annotation.CyberReactor;
import io.cyberflux.node.engine.utils.CyberPackageUtils;

@CyberReactor
public class JuintBeanTest {
    private static Logger log = LoggerFactory.getLogger(JuintBeanTest.class);
    @CyberInject
    CustomReactor customImplServer;
    @Test
    public void test() throws InterruptedException {
        CyberFluxHuaxuEngine.run(JuintBeanTest.class);
        log.info("--------------------------");
        log.info("{}", CyberPackageUtils.getStartupClassName());
        log.info("{}", JuintBeanTest.class.getCanonicalName());
        log.info("{}", this);
        log.info("{}", customImplServer);
        Thread.sleep(10000);
    }
}
