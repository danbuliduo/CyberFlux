package io.cyberflux.node.engine;

import io.cyberflux.node.engine.huaxu.CyberFluxHuaxuEngine;
import io.cyberflux.node.engine.huaxu.annotation.CyberMetaObjectScan;

@CyberMetaObjectScan({"org.junit"})
public class TestRuner {
    public static void main(String[] args) throws InterruptedException {
        CyberFluxHuaxuEngine engine = CyberFluxHuaxuEngine.run(TestRuner.class);
        System.out.println(engine.nodeName());
        Thread.sleep(1000000);
    }
}
