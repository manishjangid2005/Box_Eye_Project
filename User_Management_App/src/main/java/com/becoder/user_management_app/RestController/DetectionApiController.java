package com.becoder.user_management_app.RestController;

import com.becoder.user_management_app.Entity.DetectionLog;
import com.becoder.user_management_app.Repository.DetectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DetectionApiController {

    private final DetectionRepository detectionRepository;

    @PostMapping("/detection")
    public ResponseEntity<String> saveDetection(@RequestBody DetectionLog detectionLog) {
        detectionRepository.save(detectionLog);
        return ResponseEntity.ok("Detection data saved successfully");
    }

    @GetMapping("/live")
    public ResponseEntity<DetectionLog> getLiveDetection() {
        DetectionLog latest = detectionRepository.findLatestDetection();
        if (latest == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(latest);
    }

    @GetMapping("/analytics/weekly")
    public ResponseEntity<List<Map<String, Object>>> getWeeklyData() {
        return ResponseEntity.ok(detectionRepository.getWeeklyAnalytics());
    }

    @GetMapping("/analytics/monthly")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyData() {
        return ResponseEntity.ok(detectionRepository.getMonthlyAnalytics());
    }
}