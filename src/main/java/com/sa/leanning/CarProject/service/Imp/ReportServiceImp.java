package com.sa.leanning.CarProject.service.Imp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.sa.leanning.CarProject.DTO.ExpenseReportDto;
import com.sa.leanning.CarProject.DTO.ProductReportDto;
import com.sa.leanning.CarProject.Entities.Product;
import com.sa.leanning.CarProject.Entities.ProductImportHistory;
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
        ReportSpecification reportSpecification = new ReportSpecification(reportFilter);

        // 2. Get all SaleDetails within the date range
        List<SaleDetail> saleDetails = saleDetailRepository.findAll(reportSpecification);

        if (saleDetails.isEmpty()) {
            return listDto; // return empty if no sales
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
            if (product == null) continue; // skip if product not found

            List<SaleDetail> details = entry.getValue();

            // Sum units
            int totalUnit = details.stream().mapToInt(SaleDetail::getUnit).sum();

            // Sum total amount
            BigDecimal totalAmount = details.stream()
                    .map(sd -> sd.getAmount().multiply(BigDecimal.valueOf(sd.getUnit())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Build DTO
            ProductReportDto dto = new ProductReportDto();
            dto.setProductId(product.getId());
            dto.setProductName(product.getName());
            dto.setTotalUnit(totalUnit);
            dto.setTotalAmount(totalAmount);

            listDto.add(dto);
        }

        return listDto;
    }
    @Override
    public List<ExpenseReportDto> getExpenseReport(
            LocalDate startDate,
            LocalDate endDate) {
        System.out.println("===============");
        ProductImportHistoryFilter filter = new ProductImportHistoryFilter();
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);
        List<ExpenseReportDto> result = new ArrayList<>();
        ProductImportHistorySpecification spec =
                new ProductImportHistorySpecification(filter);

        // 1️⃣ get filtered import histories
        List<ProductImportHistory> histories =
                productImportHistoryRepository.findAll(spec);
         
        List<Long> idProductImports = histories.stream().map(s->s.getProduct().getId()).distinct().toList();
        System.out.println(idProductImports);
        Map<Long, Product> idProductImportsMap = productService.getAllPrroductsByIdMap(idProductImports);
       
        
        
        System.out.println(idProductImportsMap);
        Map<Product, List<ProductImportHistory>> importDetailGroupByProduct = histories.stream().collect(Collectors.groupingBy(s->s.getProduct()));
        
        
        
        for(var entry : importDetailGroupByProduct.entrySet()) {
        	Product productImport = idProductImportsMap.get(entry.getKey().getId());
        	List<ProductImportHistory> dataImport = entry.getValue();
        	ExpenseReportDto dto = new ExpenseReportDto();
        	
        	dto.setProductId(productImport.getId());
        	dto.setProductName(productImport.getName());
            int unit = dataImport.stream().mapToInt(s->s.getImportUnit()).sum();
        	BigDecimal totalAmoun = dataImport.stream().map(a -> a.getPricePerUnit().multiply(BigDecimal.valueOf(a.getImportUnit()
        			))).reduce(BigDecimal.ZERO, BigDecimal::add);
        	
        	dto.setUnit(unit);
        	dto.setTotalAmount(totalAmoun);
        	result.add(dto);
        	
        }
        
        
        return result;
    }

       
 

}
