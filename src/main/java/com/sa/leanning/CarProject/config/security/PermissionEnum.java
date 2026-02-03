package com.sa.leanning.CarProject.config.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionEnum {
      BRAND_READ("brand:read"),
      BRAND_WRITE("brand:write"),
      MODEL_READ("model:read"),
      MODEL_WRITE("model:write");
      private final String decription;
      
}
