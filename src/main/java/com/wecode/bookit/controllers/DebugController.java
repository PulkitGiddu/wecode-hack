// java - file: `wecode-hack/src/main/java/com/wecode/bookit/controllers/DebugController.java`
package com.wecode.bookit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/debug")
@CrossOrigin(origins = "*")
public class DebugController {
    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> body) {
        logger.info("Received login body: {}", body);
        System.out.println("Received login body : " + body);
        // Echo back the exact JSON received
        return ResponseEntity.ok(body);
    }
}
