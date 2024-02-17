package com.lcsserver.controller;

import com.lcsserver.dto.LcsRequest;
import com.lcsserver.dto.LcsResponse;
import com.lcsserver.service.LcsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lcs")
public class LcsController {

    private final LcsService lcsService;

    @Autowired
    public LcsController(LcsService lcsService) {
        this.lcsService = lcsService;
    }

    @PostMapping
    public LcsResponse getLcs(@RequestBody LcsRequest request) {
        return new LcsResponse().setLcs(lcsService.getAllLcs(request.getValues()));
    }
}
