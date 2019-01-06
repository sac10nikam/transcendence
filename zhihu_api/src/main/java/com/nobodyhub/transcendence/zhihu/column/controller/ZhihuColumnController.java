package com.nobodyhub.transcendence.zhihu.column.controller;


import com.nobodyhub.transcendence.zhihu.column.service.ZhihuColumnApiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zhihu/column")
public class ZhihuColumnController {
    private final ZhihuColumnApiService columnApiService;

    public ZhihuColumnController(ZhihuColumnApiService columnApiService) {
        this.columnApiService = columnApiService;
    }

    @GetMapping(path = "/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    void getColumnById(String columnId) {
        columnApiService.getById(columnId);
    }
}
