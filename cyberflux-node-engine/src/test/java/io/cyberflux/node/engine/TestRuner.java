package io.cyberflux.node.engine;

import io.cyberflux.node.engine.annotation.CyberPackageScan;

@CyberPackageScan({"org.junit"})
public class TestRuner {
    public static void main(String[] args) throws InterruptedException {
       CyberFluxHuaxuEngine.run(TestRuner.class);
       Thread.sleep(1000000);
    }
}
