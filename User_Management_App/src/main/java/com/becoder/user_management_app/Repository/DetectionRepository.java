package com.becoder.user_management_app.Repository;

import com.becoder.user_management_app.Entity.DetectionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Map;

public interface DetectionRepository extends JpaRepository<DetectionLog, Long> {

    @Query(value = "SELECT * FROM detection_log ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    DetectionLog findLatestDetection();

    @Query(value = "SELECT DATE(created_at) as date, SUM(total_count) as total, SUM(small_count) as small, SUM(medium_count) as medium, SUM(large_count) as large " +
            "FROM detection_log " +
            "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE(created_at) ORDER BY date ASC", nativeQuery = true)
    List<Map<String, Object>> getWeeklyAnalytics();

    @Query(value = "SELECT DATE(created_at) as date, SUM(total_count) as total, SUM(small_count) as small, SUM(medium_count) as medium, SUM(large_count) as large " +
            "FROM detection_log " +
            "WHERE created_at >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
            "GROUP BY DATE(created_at) ORDER BY date ASC", nativeQuery = true)
    List<Map<String, Object>> getMonthlyAnalytics();
}