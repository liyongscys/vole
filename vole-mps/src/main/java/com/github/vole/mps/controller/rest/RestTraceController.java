package com.github.vole.mps.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RestTraceController {

    @GetMapping("/rest/trace/{name}")
    public String trace(@PathVariable("name") String name,@RequestHeader HttpHeaders headers){
        headers.forEach((key, v) -> log.error("{} :{}", key, v));
        return "hello:"+name+" this is the mps trace service";
    }
}
