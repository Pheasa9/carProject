package com.sa.leanning.CarProject.service.Imp;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.DTO.TodayDashboardVm;
import com.sa.leanning.CarProject.DTO.TopProductVm;
import com.sa.leanning.CarProject.repository.DashboardReportRepository;
import com.sa.leanning.CarProject.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImp implements DashboardService {

    private final DashboardReportRepository repo;

    public TodayDashboardVm getTodayDashboard(LocalDate day) {

        var kpi = repo.fetchKpi(day);
        var top = repo.fetchTopProducts(day);

        TodayDashboardVm vm = new TodayDashboardVm();
        vm.totalSales = (kpi == null || kpi.getTotalSales() == null) ? java.math.BigDecimal.ZERO : kpi.getTotalSales();
        vm.totalUnits = (kpi == null || kpi.getTotalUnits() == null) ? 0 : kpi.getTotalUnits();
        vm.ordersToday = (kpi == null || kpi.getOrdersToday() == null) ? 0 : kpi.getOrdersToday();

        // Your DB has no processing timestamps -> cannot compute these yet
        vm.avgServiceMinutes = 0;
        vm.onTimeRate = 0;

        List<TopProductVm> mapped = top.stream().map(r -> {
            TopProductVm p = new TopProductVm();
            p.name = r.getName();
            p.popularity = (r.getPopularity() == null) ? 0 : r.getPopularity();
            p.sales = (r.getSales() == null) ? java.math.BigDecimal.ZERO : r.getSales();
            return p;
        }).collect(Collectors.toList());

        vm.topProducts = mapped;
        return vm;
    }
}