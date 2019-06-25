package com.github.vole.demo.controller;

import com.github.vole.common.bean.resolver.CurrentUser;
import com.github.vole.common.utils.FastJsonUtils;
import com.github.vole.common.vo.MemberVO;
import com.github.vole.demo.fegin.TraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class DemoController {

    @Resource
    TraceService traceService;

    /**
     * 样例
     * 实际调用的是vole-mps模块下的rest/trace/{name} ,实现类为RestTraceController
     *
     * @param name     参数
     * @param memberVO 当前登录的用户信息,需要有CurrentUser注解才是注入的当前登录用户
     * @param headers  当前请求的headers (测试)
     * @return
     */
    @GetMapping("/trace/{name}")
    public String demoTrace(@PathVariable String name, @CurrentUser MemberVO memberVO, @RequestHeader HttpHeaders headers) {
        log.error("{}", FastJsonUtils.convertObjectToJSON(memberVO));
        headers.forEach((key, v) -> log.debug("{} :{}", key, v));
        return traceService.trace(name);
    }
}
