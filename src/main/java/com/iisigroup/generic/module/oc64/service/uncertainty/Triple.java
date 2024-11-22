package com.iisigroup.generic.module.oc64.service.uncertainty;

import java.math.BigDecimal;

/**
 * ClassName:Triple
 * Package:com.iisigroup.ocapi.domain.uncertainty
 * Description:
 *
 * @Date:2024/4/1 上午 10:22
 * @Author:2208021
 */
public record Triple(BigDecimal upperBound, BigDecimal lowerBound, BigDecimal sumEmission) {
}
