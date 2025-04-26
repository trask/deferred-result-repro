package com.github.trask.repro;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class TestController {

  @GetMapping("/direct")
  public String direct() {
    return "hello world";
  }

  @GetMapping("/deferred")
  public DeferredResult<String> deferred() {
    DeferredResult<String> deferredResult = new DeferredResult<>();
    deferredResult.setResult("hello world");
    return deferredResult;
  }
}
