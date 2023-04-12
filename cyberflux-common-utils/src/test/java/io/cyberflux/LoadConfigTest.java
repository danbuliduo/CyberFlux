package io.cyberflux;

import org.junit.Test;

import io.cyberflux.common.utils.CyberJsonUtils;
import io.cyberflux.common.utils.CyberPropsUtils;
import io.cyberflux.common.utils.CyberConfigLoaderUtils;
import io.cyberflux.common.utils.CyberXmlUtils;
import io.cyberflux.common.utils.CyberYamlUtils;

public class LoadConfigTest {

    @Test
    public void yaml(){
        Config c = CyberConfigLoaderUtils.fromResource("/application.yaml", Config.class);
        System.out.println(CyberJsonUtils.toJsonString(c));
        System.out.println(CyberYamlUtils.toYamlString(c));
        System.out.println(CyberPropsUtils.toPropsString(c));
        System.out.println(CyberXmlUtils.toXmlString(c));
    }

    @Test
    public void yml() {
        Config c = CyberConfigLoaderUtils.fromResource("/application.yml", Config.class);
        System.out.println(c.getData());
    }

    @Test
    public void json() {
        Config c = CyberConfigLoaderUtils.fromResource("/application.json", Config.class);
        System.out.println(c.getData());
    }

    @Test
    public void properties() {
        Config c = CyberConfigLoaderUtils.fromResource("/application.properties", Config.class);
        System.out.println(c.getData());
    }

    @Test
    public void xml() {
        Config c = CyberConfigLoaderUtils.fromResource("/application.xml", Config.class);
        System.out.println(c.getData());
    }

}
