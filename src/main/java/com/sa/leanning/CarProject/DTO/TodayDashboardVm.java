package com.sa.leanning.CarProject.DTO;


import java.math.BigDecimal;
import java.util.List;

public class TodayDashboardVm {
    public BigDecimal totalSales;
    public long totalUnits;

    public long ordersToday;          // volume (count of sales)
    public double avgServiceMinutes;  // 0 for now
    public double onTimeRate;         // 0 for now

    public List<TopProductVm> topProducts;
}
