package com.iisigroup.generic.module.oc64.service.impl;


import com.iisigroup.generic.exception.ServiceException;
import com.iisigroup.generic.module.oc64.repository.ChangeHistoriesRepository;
import com.iisigroup.generic.utils.JWTInfoHelper;
import com.iisigroup.generic.utils.StringUtils;
import com.iisigroup.ocapi.entity.ChangeHistories;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AsyncChangeHistoriesService {

    @Autowired
    private ChangeHistoriesRepository changeHistoriesRepository;
    @Autowired
    private JWTInfoHelper jwtInfoHelper;

    private static final List<String> FILTERED_FIELD = Arrays.asList("id", "createdAt", "createdBy", "updatedAt", "updatedBy", "passwordHash");

    @Async
    public void saveDiff(Object oldBean, Object newBean, String beanId, Class<?> clazz) {
        List<ChangeHistories> list = Arrays.stream(BeanUtils.getPropertyDescriptors(clazz))
                .filter(prop -> !FILTERED_FIELD.contains(prop.getName()))
                .map(prop -> createChangeHistory(oldBean, newBean, beanId, prop))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        changeHistoriesRepository.saveAll(list);
    }

    private ChangeHistories createChangeHistory(Object oldBean, Object newBean, String beanId, PropertyDescriptor prop) {
        if (oldBean == null || newBean == null) {
            return null;
        }
        String fieldName = prop.getName();
        try {
            Object oldValue = prop.getReadMethod().invoke(oldBean);
            Object newValue = prop.getReadMethod().invoke(newBean);

            if (Objects.equals(oldValue, newValue)) {
                return null;
            }

            ChangeHistories changeHistories = new ChangeHistories();
            changeHistories.setCompanyId(jwtInfoHelper.getCompanyIdByJwt());
            changeHistories.setUserId(jwtInfoHelper.getUsernameByJwt());
            changeHistories.setTableName(StringUtils.toUnderScoreCase(newBean.getClass().getSimpleName()));
            changeHistories.setFieldName(StringUtils.toUnderScoreCase(fieldName));
            changeHistories.setSourceId(beanId);
            changeHistories.setOldValue(String.valueOf(oldValue));
            changeHistories.setNewValue(String.valueOf(newValue));
            changeHistories.setRequestTime(OffsetDateTime.now());

            return changeHistories;
        } catch (Exception e) {

            throw new ServiceException("changeHistories.create.error");
        }
    }
}
