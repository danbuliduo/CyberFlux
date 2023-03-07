package io.cyberflux.spring;

enum EngineCore {
    HUAXU, SPRING
}

public class CyberFluxEngineConfig {
    private EngineCore core = EngineCore.SPRING;
    public EngineCore getCore() {
        return core;
    }
    public CyberFluxEngineConfig setCore(EngineCore core) {
        this.core = core;
        return this;
    }
}
