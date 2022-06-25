package com.demo;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author shenguangyang
 * @date 2022-01-03 9:48
 */
class MethodParseTestJavaPost {
    private static final Logger log = LoggerFactory.getLogger(MethodParseTestJavaPost.class);
    @Test
    public void parse() {
        MethodParse methodParse = new MethodParse(TestInfer.class);
        List<MapObjectDefinition> parse = methodParse.parse();
        log.info("parse end");
    }
}