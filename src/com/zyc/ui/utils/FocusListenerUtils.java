/**   
* @Title: FocusListenerUtils.java 
* @Package com.zyc.ui.utils 
* @Description: TODO
* @author zyc  
* @date 2019年3月15日 上午10:02:31 
* @version V1.0   
*/
package com.zyc.ui.utils;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/** 
 * @ClassName: FocusListenerUtils 
 * @Description: 输入框默认提示
 * @author zyc
 * @date 2019年3月15日 上午10:02:31 
 *  
 */
public class FocusListenerUtils implements FocusListener {
	/**
	 * 提示信息
	 */
	 String info;
	 /**
	  * 文本框
	  */
	 JTextField jtf;
	/** 
	*  
	*  
	* @param info
	* @param jtf 
	*/
	public FocusListenerUtils(String info, JTextField jtf) {
		super();
		this.info = info;
		this.jtf = jtf;
	}

	/* 
	 *  获得焦点的时候,清空提示文字
	 *  
	 * @param e 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent) 
	 */
	@Override
	public void focusGained(FocusEvent e) {
		String temp = jtf.getText();
        if(temp.equals(info)){
            jtf.setText("");
        }
	}

	/* 
	 *  失去焦点的时候,判断如果为空,就显示提示文字
	 *  
	 * @param e 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent) 
	 */
	@Override
	public void focusLost(FocusEvent e) {
		String temp = jtf.getText();
        if(temp.equals("")){
            jtf.setText(info);
        }
	}

}
