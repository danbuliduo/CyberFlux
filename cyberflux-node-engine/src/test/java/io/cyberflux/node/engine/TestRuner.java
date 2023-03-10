package io.cyberflux.node.engine;

import io.cyberflux.node.engine.annotation.CyberInject;
import io.cyberflux.node.engine.annotation.CyberPackageScan;
import io.cyberflux.node.engine.annotation.CyberReactor;
//import io.cyberflux.node.engine.utils.CyberPackageUtils;

@CyberPackageScan({"org.junit"})
@CyberReactor
public class TestRuner {
    @CyberInject
    CustomImplServer customImplServer;
    public static void main(String[] args) throws InterruptedException {
       CyberFluxHuaxuEngine.run(TestRuner.class);
        //System.out.println(CyberPackageUtils.scanClassName("org.junit", true));
       Thread.sleep(1000000);
    }
}
