package com.sa.leanning.CarProject.service.Imp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.DTO.ExpenseReportDto;
import com.sa.leanning.CarProject.DTO.ProductReportDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Entities.ProductImportHistory;
import com.sa.leanning.CarProject.Entities.Sale;
import com.sa.leanning.CarProject.Entities.SaleDetail;
import com.sa.leanning.CarProject.repository.ProductImportHistoryRepository;
import com.sa.leanning.CarProject.repository.SaleDetailRepository;
import com.sa.leanning.CarProject.service.ProductService;
import com.sa.leanning.CarProject.service.ReportService;
import com.sa.leanning.CarProject.spe.ProductImportHistoryFilter;
import com.sa.leanning.CarProject.spe.ProductImportHistorySpecification;
import com.sa.leanning.CarProject.spe.ReportFilter;
import com.sa.leanning.CarProject.spe.ReportSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportServiceImp implements ReportService {

    private final SaleDetailRepository saleDetailRepository;
    private final ProductService productService;
    private final ProductImportHistoryRepository productImportHistoryRepository;

    @Override
    public List<ProductReportDto> getReport(LocalDate startDate, LocalDate endDate) {
        List<ProductReportDto> listDto = new ArrayList<>();

        // 1. Prepare specification for filtering sale details
        ReportFilter reportFilter = new ReportFilter();
        reportFilter.setStartDate(startDate);
        reportFilter.setEndDate(endDate);
        reportFilter.setStatus(true); // ✅ only status=true
        ReportSpecification reportSpecification = new ReportSpecification(reportFilter);

        // 2. Get all SaleDetails within the date range (and status=true via spec)
        List<SaleDetail> saleDetails = saleDetailRepository.findAll(reportSpecification);

        if (saleDetails.isEmpty()) {
            return listDto;
        }

        // 3. Get all product IDs from sale details
        List<Long> productIds = saleDetails.stream()
                .map(sd -> sd.getProduct().getId())
                .distinct()
                .toList();

        // 4. Fetch products in a map for easy lookup
        Map<Long, Product> productMap = productService.getAllPrroductsByIdMap(productIds);

        // 5. Group sale details by productId
        Map<Long, List<SaleDetail>> groupedByProduct = saleDetails.stream()
                .collect(Collectors.groupingBy(sd -> sd.getProduct().getId()));

        // 6. Build report
        for (var entry : groupedByProduct.entrySet()) {
            Long productId = entry.getKey();
            Product product = productMap.get(productId);
            if (product == null) continue;

            List<SaleDetail> details = entry.getValue();

            // ✅ Sum units safely (handles null unit)
            int totalUnit = details.stream()
                    .map(SaleDetail::getUnit)
                    .filter(java.util.Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();

            // ✅ Sum total amount safely
            BigDecimal totalAmount = details.stream()
                    .map(sd -> {
                        BigDecimal price = sd.getAmount();
                        if (price == null) {
                            // fallback: use product salePrice
                            price = (sd.getProduct() != null && sd.getProduct().getSalePrice() != null)
                                    ? sd.getProduct().getSalePrice()
                                    : BigDecimal.ZERO;
                        }

                        int unit = sd.getUnit() == null ? 0 : sd.getUnit();
                        return price.multiply(BigDecimal.valueOf(unit));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // ✅ Collect sale IDs safely (handles null sale)
            List<Long> saleIds = details.stream()
                    .map(SaleDetail::getSale)
                    .filter(java.util.Objects::nonNull)
                    .map(Sale::getId)
                    .distinct()
                    .toList();

            // Build DTO
            ProductReportDto dto = new ProductReportDto();
            dto.setProductId(product.getId());
            dto.setProductName(product.getName());
            dto.setTotalUnit(totalUnit);
            dto.setTotalAmount(totalAmount);
            dto.setSaleIds(saleIds);

            listDto.add(dto);
        }

        return listDto;
    }

    @Override
    public List<ExpenseReportDto> getExpenseReport(LocalDate startDate, LocalDate endDate) {
        ProductImportHistoryFilter filter = new ProductImportHistoryFilter();
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);

        List<ExpenseReportDto> result = new ArrayList<>();
        ProductImportHistorySpecification spec = new ProductImportHistorySpecification(filter);

        // 1. get filtered import histories
        List<ProductImportHistory> histories = productImportHistoryRepository.findAll(spec);
        if (histories.isEmpty()) return result;

        List<Long> idProductImports = histories.stream()
                .map(h -> h.getProduct().getId())
                .distinct()
                .toList();

        Map<Long, Product> idProductImportsMap = productService.getAllPrroductsByIdMap(idProductImports);

        Map<Product, List<ProductImportHistory>> importDetailGroupByProduct =
                histories.stream().collect(Collectors.groupingBy(ProductImportHistory::getProduct));

        for (var entry : importDetailGroupByProduct.entrySet()) {
            Product productImport = idProductImportsMap.get(entry.getKey().getId());
            if (productImport == null) continue;

            List<ProductImportHistory> dataImport = entry.getValue();

            ExpenseReportDto dto = new ExpenseReportDto();
            dto.setProductId(productImport.getId());
            dto.setProductName(productImport.getName());

            int unit = dataImport.stream()
                    .map(ProductImportHistory::getImportUnit)
                    .filter(java.util.Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();

            BigDecimal totalAmount = dataImport.stream()
                    .map(a -> {
                        BigDecimal price = a.getPricePerUnit() == null ? BigDecimal.ZERO : a.getPricePerUnit();
                        int u = a.getImportUnit() == null ? 0 : a.getImportUnit();
                        return price.multiply(BigDecimal.valueOf(u));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            dto.setUnit(unit);
            dto.setTotalAmount(totalAmount);

            result.add(dto);
        }

        return result;
    }
}
