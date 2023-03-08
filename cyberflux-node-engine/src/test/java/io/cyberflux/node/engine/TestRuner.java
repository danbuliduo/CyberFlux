package io.cyberflux.node.engine;

import io.cyberflux.node.engine.annotation.CyberInject;
import io.cyberflux.node.engine.annotation.CyberReactor;

@CyberReactor
public class TestRuner {
    @CyberInject
    CustomImplServer customImplServer;
    public static void main(String[] args) throws InterruptedException {
       CyberFluxHuaxuEngine.run(TestRuner.class);
       Thread.sleep(100000);
    }
}
