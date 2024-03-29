package io.cyberflux.node.engine;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.common.utils.CyberConfigLoaderUtils;
import io.cyberflux.common.utils.CyberPackageUtils;
import io.cyberflux.node.engine.huaxu.CyberFluxHuaxuEngine;
import io.cyberflux.node.engine.huaxu.annotation.CyberInject;
import io.cyberflux.node.engine.huaxu.annotation.CyberMetaObject;
import io.cyberflux.reactor.mqtt.transport.TcpTransportConfig;

@CyberMetaObject
public class JuintTest {
    private static Logger log = LoggerFactory.getLogger(JuintTest.class);
    @CyberInject
    CustomReactor customImplServer;

    @Test
    public void test() throws InterruptedException {
        CyberFluxHuaxuEngine.run(JuintTest.class);
        log.info("--------------------------");
        log.info("{}", CyberPackageUtils.getStartupClassName());
        log.info("{}", JuintTest.class.getCanonicalName());
        log.info("{}", this);
        log.info("{}", customImplServer);
        Thread.sleep(10000);
    }

	@Test
	public void t() {
		TcpTransportConfig c = CyberConfigLoaderUtils.autoLoad("/test.yaml", TcpTransportConfig.class);
		System.out.println(c.getPort());
	}
}
