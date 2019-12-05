/**   
* @Title: ImagePanel.java 
* @Package com.zyc.ui 
* @Description: TODO
* @author zyc  
* @date 2019年3月15日 下午3:02:24 
* @version V1.0   
*/
package com.zyc.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/** 
 * @ClassName: ImagePanel 
 * @Description: TODO
 * @author zyc
 * @date 2019年3月15日 下午3:02:24 
 *  
 */
public class ImagePanel extends JPanel {
	Dimension d;
    Image image;

    public ImagePanel(Dimension d, Image image) {
        super();
        this.d = d;
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, d.width, d.height, this);
//        MainFrame.instance().repaint();
    }

}
