package io.cyberflux.node.engine.juint;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cyberflux.node.engine.utils.CyberPackageUtils;
import io.cyberflux.node.engine.utils.CyberStringUtils;


public class BeanTest {
    private static Logger log = LoggerFactory.getLogger(BeanTest.class);

    @Test
    public void test() {
        log.info("--------------------------");
        log.info("{}", CyberPackageUtils.getMainClassName());
        log.info("{}", BeanTest.class.getCanonicalName());
        System.out.println(CyberStringUtils.cosFirstSplit("ada-sff-afa", "-"));
        System.out.println(CyberStringUtils.firstSplit("ada-sff-afa", "-"));
    }
}
