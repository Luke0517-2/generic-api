package com.iisigroup.generic.module.oc64.service.uncertainty;

import com.iisigroup.ocapi.entity.Emissions;

import java.util.List;

public interface UncertaintyService {


    String updateUncertaintyItemsWhenEmissionsUpdateByActivityItemBases(String activityItemBasesId, List<Emissions> emissionsList);

    String updateSingleUncertaintyItemsWhenSingleEmissionsUpdate(Emissions emissions);
}
