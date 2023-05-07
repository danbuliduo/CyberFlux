package io.cyberflux.node.engine;

import io.cyberflux.node.engine.huaxu.CyberFluxHuaxuEngine;
import io.cyberflux.node.engine.huaxu.annotation.CyberMetaObjectScan;

@CyberMetaObjectScan({"org.junit"})
public class TestRuner {
    public static void main(String[] args) throws InterruptedException {
        CyberFluxHuaxuEngine.run(TestRuner.class);
        Thread.sleep(1000000);
    }
}
