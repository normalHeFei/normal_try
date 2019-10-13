package com.normal.trysth.spring.springmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Random;

/**
 * @author hf
 * @date 18-10-8
 */
@RestController
public class TestHttpCacheController {
    private int version = 1;

    @GetMapping("/testHttpCache")
    public ResponseEntity<String> testHttpCache(WebRequest webRequest) {
        HttpHeaders headers = new HttpHeaders();
        String eTag = "W/" + String.valueOf(version) + "\"";
        //not modified return 304
        if (webRequest.checkNotModified(eTag)) {
            return null;
        }
        headers.setETag(eTag);
        return new ResponseEntity(new Random().nextInt(), headers, HttpStatus.OK);
    }

    @RequestMapping("/updateBook")
    public String updateBook() {
        version++;
        return "updateWithSimpleParam ok";
    }

}
