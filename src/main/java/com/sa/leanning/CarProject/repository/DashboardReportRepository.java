package com.sa.leanning.CarProject.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.sa.leanning.CarProject.Entities.Sale;

public interface DashboardReportRepository extends Repository<Sale, Long> {

    // ---- projections ----
    interface KpiRow {
        BigDecimal getTotalSales();
        Long getTotalUnits();
        Long getOrdersToday();
    }

    interface TopProductRow {
        String getName();
        Integer getPopularity();
        BigDecimal getSales();
    }

    // ---- KPI: totalSales, totalUnits, ordersToday ----
    @Query(value = """
        SELECT
          COALESCE(SUM(sd.amount), 0) AS total_sales,
          COALESCE(SUM(sd.unit), 0)   AS total_units,
          COUNT(DISTINCT s.sale_id)   AS orders_today
        FROM sales s
        LEFT JOIN sale_details sd ON s.sale_id = sd.sale_id
        WHERE CAST(s.sold_date AS DATE) = :day
          AND s.status = true
    """, nativeQuery = true)
    KpiRow fetchKpi(@Param("day") LocalDate day);

    // ---- Top products with popularity % ----
    @Query(value = """
        WITH product_sales AS (
          SELECT
            p.name AS name,
            COALESCE(SUM(sd.unit), 0) AS units,
            COALESCE(SUM(sd.amount), 0) AS sales
          FROM sale_details sd
          JOIN sales s    ON s.sale_id = sd.sale_id
          JOIN products p ON p.id = sd.product_id
          WHERE CAST(s.sold_date AS DATE) = :day
            AND s.status = true
          GROUP BY p.name
        )
        SELECT
          name,
          CAST(ROUND(units * 100.0 / NULLIF(MAX(units) OVER (), 0), 0) AS INT) AS popularity,
          sales
        FROM product_sales
        ORDER BY units DESC
        LIMIT 10
    """, nativeQuery = true)
    List<TopProductRow> fetchTopProducts(@Param("day") LocalDate day);
}
