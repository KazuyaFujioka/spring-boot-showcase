package com.example.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
class IndexController {

  // faviconで404エラーになってしまう問題を回避する為に用意
  @GetMapping("favicon.ico")
  void returnNoFavicon() {}
}
