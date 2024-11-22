package com.iisigroup.generic.module.oc64.constant;

/**
 * ClassName:ActivityDataEnum
 * Package:com.iisigroup.ocapi.component.myEnum
 * Description:
 *
 * @Date:2024/2/22 上午 11:34
 * @Author:2208021
 */
public enum ActivityTypeEnum {
    //移動燃燒
    MOBILE_COMBUSTION,
    //固定燃燒、工業製程
    BIO_ENERGY,
    //逸散排放
    LEAKAGE,
    //外購電力
    ENERGY,
    //購買的產品及服務-自來水
    PURCHASED_WATER,
    //外購蒸汽
    OUTSOURCING_STEAM,
    //上游運輸及配送、下游運輸及配送
    WEIGHT_TRANSPORT,
    //員工通勤、商務旅行
    PERSON_TRANSPORT,
    //資本財、燃料與能源相關活動、上游資產租賃、售出產品的加工、售出產品的使用、售出產品的最終處理、下游資產租賃、加盟或投資、其他來源、特許經營  //todo 不確定是不是都是在basic
    BASIC,
    //營運過程產生的廢棄物
    OPERATIONAL_WASTE_GENERATED,

    //逸散排放-化糞池
    SEPTI_TANK
}
