package com.sa.leanning.CarProject.projection;

import java.math.BigDecimal;

public interface ProductSold {
   Long getProductId();
   String getProductName();
   Integer getUnit();
   BigDecimal getTotalAmount();
}
