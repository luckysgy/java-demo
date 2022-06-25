package com.demo;

import com.concise.component.core.entity.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shenguangyang
 * @date 2022-01-02 10:13
 */
@RestController
public class PackagePomController {

    @GetMapping
    public Response test() {
        return Response.buildSuccess();
    }
}
