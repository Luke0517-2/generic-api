package com.iisigroup.generic.module.oc64.constant;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum EquipmentEnum {
    //TODO 實作匯入功能時，sheet會有"設備"column，下拉選單帶入 ex.家用冷凍、冷藏裝備， 填入對應逸散率並計算


    //家用冷凍、冷藏裝備
    Domestic_Refrigeration(BigDecimal.valueOf(0.3)),
    //獨立商用冷凍、冷藏裝備
    Commercial_Applications(BigDecimal.valueOf(8.0)),
    //中、大型冷凍、冷藏裝備
    Commercial_Refrigeration(BigDecimal.valueOf(22.5)),
    //交通用冷凍、冷藏裝備
    Transport_Refrigeration(BigDecimal.valueOf(32.5)),
    //工業冷凍、冷藏裝備，包括食品加工及冷藏
    Industrial_Refrigeration(BigDecimal.valueOf(16.0)),
    //冰水機
    Chillers(BigDecimal.valueOf(8.5)),
    //住宅及商業建築冷氣機
    Residential_and_Commercial_AC(BigDecimal.valueOf(5.5)),
    //移動式空氣清靜機
    Mobile_AC(BigDecimal.valueOf(15.0)),

    //不在此8項中，由使用者自行輸入
    Others(BigDecimal.valueOf(0.0));
    private BigDecimal leakageRate;

    EquipmentEnum(BigDecimal leakageRate){
        this.leakageRate = leakageRate;
    }
}
