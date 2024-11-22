package com.iisigroup.generic.module.oc64.constant;

/**
 * @Description: MS Interface - com.iisigroup.ocarbon.interfaces.services.dto
 * 輸入的型態
 *
 */
public enum ActivityBaseEnum {
	//C1
	/**
	 * 移動燃燒
	 */
	MOBILE_COMBUSTION,
	/**
	 * 固定燃燒
	 */
	STATIONARY_COMBUSTION,
	/**
	 * 工業製程
	 */
	 PROCESS_EMISSIONS,
	/**
	 * 逸散排放
	 */
	FUGITIVE_EMISSIONS,

	//C2
	/**
	 * 外購電力
	 */
	EXTERNAL_PURCHASED_ELECTRICITY,
	/**
	 * 外購蒸汽
	 */
	EXTERNAL_PURCHASED_STEAM,

	//C3
	/**
	 * 上游運輸及配送
	 */
	UPSTREAM_TRANSPORTATION,
	/**
	 * 商務旅行
	 */
	BUSINESS_TRAVEL,
	/**
	 * 員工通勤
	 */
	EMPLOYEE_COMMUTING,
	/**
	 * 下游運輸及配送
	 */
	DOWNSTREAM_TRANSPORTATION,

	//C4
	/**
	 * 購買的產品與服務 (通用excel)
	 */
	PURCHASED_GOODS_AND_SERVICES,
	/**
	 * 資本產品 (通用excel)
	 */
	CAPITAL_GOODS,
	/**
	 * 燃料與能源相關活動 (通用excel)
	 */
	EXTERNAL_PURCHASED_FUEL_AND_RESOURCES,
	/**
	 * 營運過程產生的廢棄物
	 */
	WASTE_TREATMENT,
	/**
	 * 上游資產租賃 (通用excel)
	 */
	UPSTREAM_LEASING,

	//C5
	/**
	 * 售出產品的加工 (通用excel)
	 */
	PRODUCT_PROCESSING,
	/**
	 * 售出產品的使用 (通用excel)
	 */
	PRODUCT_USE,
	/**
	 * 售出產品的最終處理 (通用excel)
	 */
	END_OF_LIFE_TREATMENT_OF_PRODUCTS,
	/**
	 * 下游資產租賃 (通用excel)
	 */
	DOWNSTREAM_LEASING,
	/**
	 * 投資 (通用excel)
	 */
	INVESTMENT_EMISSIONS,
	//C6
	/**
	 * 其他來源 (通用excel)
	 */
	OTHER_INDIRECT_EMISSIONS,
	/**
	 * 特許經營(加盟) (通用excel)
	 */
	FRANCHISES,

	// 原燃物料
	/**
	 * 購買的產品與服務-自來水
	 */
	PURCHASED_WATER,
	/**
	 * 逸散排放-化糞池(未接管)
	 */
	FUGITIVE_EMISSIONS_SEPTIC_TANK,



	CommonDto
}
