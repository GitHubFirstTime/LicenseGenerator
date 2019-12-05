/**   
* @Title: CreateLicense.java 
* @Package com.jeeplus.common.security.lisence 
* @Description: TODO
* @author zyc  
* @date 2019年3月11日 上午10:56:03 
* @version V1.0   
*/
package com.jeeplus.common.security.lisence;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.security.auth.x500.X500Principal;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultKeyStoreParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/** 
 * @ClassName: CreateLicense 
 * @Description: TODO
 * @author zyc
 * @date 2019年3月11日 上午10:56:03 
 *  
 */
public class CreateLicense {
	//common param
	private static String PRIVATEALIAS = "";
	private static String KEYPWD = "";
	private static String STOREPWD = "";
	private static String SUBJECT = "";
	private static String licPath = "";
	private static String priPath = "";
	//license content
	private static String issuedTime = "";
	private static String notBefore = "";
	private static String notAfter = "";
	private static String ipAddress = "";
	private static String macAddress = "";
	private static String consumerType = "";
	private static int consumerAmount = 0;
	private static String info = "";
	
	// 为了方便直接用的API里的例子
	// X500Princal是一个证书文件的固有格式，详见API
	private final static X500Principal DEFAULTHOLDERANDISSUER = new X500Principal(
			"CN=Duke、OU=JavaSoft、O=Sun Microsystems、C=US");
	
	public void setParam(String propertiesPath,String mac,String ip,String issuedDate,String notBeforeT,String notAfterT) {
		// 获取参数
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream(propertiesPath);
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PRIVATEALIAS = prop.getProperty("PRIVATEALIAS");
		KEYPWD = prop.getProperty("KEYPWD");
		STOREPWD = prop.getProperty("STOREPWD");
		SUBJECT = prop.getProperty("SUBJECT");
		KEYPWD = prop.getProperty("KEYPWD");
		licPath = "C://license_g//rlc_edrp.lic";//prop.getProperty("licPath");
		priPath = prop.getProperty("priPath");
		//license content
		issuedTime = issuedDate;//prop.getProperty("issuedTime");
		notBefore = notBeforeT;//prop.getProperty("notBefore");
		notAfter = notAfterT;//prop.getProperty("notAfter");
		macAddress = mac.toUpperCase();//prop.getProperty("macAddress");
		ipAddress =ip;// prop.getProperty("ipAddress");
		consumerType = prop.getProperty("consumerType");
		consumerAmount = Integer.valueOf(prop.getProperty("consumerAmount"));
		info = prop.getProperty("info");
		File file = new File("C://license_g");
		judeDirExists(file);
	}
    // 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdirs();
        }

    }
	public boolean create() {		
		try {
			/************** 证书发布者端执行 ******************/
			LicenseManager licenseManager = LicenseManagerHolder
					.getLicenseManager(initLicenseParams0());
			licenseManager.store((createLicenseContent()), new File(licPath));	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("客户端证书生成失败!_generation lic fail!");
			return false;
		}
		System.out.println("服务器端生成证书成功!_generation lic success!");
		return true;
	}

	// 返回生成证书时需要的参数
	private static LicenseParam initLicenseParams0() {
		Preferences preference = Preferences
				.userNodeForPackage(CreateLicense.class);
		// 设置对证书内容加密的对称密码
		CipherParam cipherParam = new DefaultCipherParam(STOREPWD);
		// 参数1,2从哪个Class.getResource()获得密钥库;参数3密钥库的别名;参数4密钥库存储密码;参数5密钥库密码
		KeyStoreParam privateStoreParam = new DefaultKeyStoreParam(
				CreateLicense.class, priPath, PRIVATEALIAS, STOREPWD, KEYPWD);
		LicenseParam licenseParams = new DefaultLicenseParam(SUBJECT,
				preference, privateStoreParam, cipherParam);
		return licenseParams;
	}

	// 从外部表单拿到证书的内容
		public final static LicenseContent createLicenseContent() {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			LicenseContent content = null;
			content = new LicenseContent();
			content.setSubject(SUBJECT);
			content.setHolder(DEFAULTHOLDERANDISSUER);
			content.setIssuer(DEFAULTHOLDERANDISSUER);
			try {
				content.setIssued(format.parse(issuedTime));
				content.setNotBefore(format.parse(notBefore));
				content.setNotAfter(format.parse(notAfter));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			content.setConsumerType(consumerType);
			content.setConsumerAmount(consumerAmount);
			content.setInfo(info);
			// 扩展
//			content.setExtra(new Object());
			content.setExtra(macAddress);
			return content;
		}
}