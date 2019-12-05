package com.jeeplus.common.security.lisence;

public class licenseCreateTest {
	/*
	 * public static void main(String[] args){ CreateLicense cLicense = new
	 * CreateLicense(); //获取参数 cLicense.setParam("./param.properties"); //生成证书
	 * cLicense.create(); }
	 */
	public boolean generateLic(String mac, String ip, String issuedDate,
			String startDate, String endDate) {
		if (null != mac && !"".equals(mac)) {
			mac = mac.trim().toUpperCase();
			if (mac.contains(":")) {
				mac = mac.replaceAll(":", "-");
			}
		}
		CreateLicense cLicense = new CreateLicense();
		// 初始化参数
		cLicense.setParam("param.properties", mac, ip, issuedDate, startDate,
				endDate);
		// 生成证书
		boolean bool = cLicense.create();

		return bool;
	}
}
