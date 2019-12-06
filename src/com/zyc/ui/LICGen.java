package com.zyc.ui;

import com.jeeplus.common.security.lisence.licenseCreateTest;
import com.zyc.ui.utils.FocusListenerUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class LICGen {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static JPanel p = new JPanel(); // 实例化一个面板
    static JFrame jFrame = new JFrame();
    static JFileChooser jfChooser = new JFileChooser();
    static JPanel bottomP=new JPanel();  //
    static JLabel tooltipLabels = new JLabel();

    static SpinnerDateModel startDateModel = new SpinnerDateModel();
    //获得JSPinner对象
    static JSpinner startDate = new JSpinner(startDateModel);
    //设置时间格式
    static JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDate,
            "yyyy-MM-dd");

    //设置时间格式
    static SpinnerDateModel endDateModel = new SpinnerDateModel();
    static JSpinner endDate = new JSpinner(endDateModel);
    //设置时间格式
    static JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDate,
            "yyyy-MM-dd");

    static SpinnerDateModel issuedDateModel = new SpinnerDateModel();
    static JSpinner issuedDate = new JSpinner(issuedDateModel);
    //设置时间格式
    static JSpinner.DateEditor issuedDateEditor = new JSpinner.DateEditor(issuedDate,
            "yyyy-MM-dd");
//            "yyyy-MM-dd HH:mm:ss");

    //	static JXDatePicker datepick = new JXDatePicker();
    public static void main(String[] args) throws Exception {
        issuedDateEditor.getTextField().setEditable(false);
        issuedDateModel.getCalendarField();
        issuedDate.setEnabled(false);
        JMenuBar mb;
        JMenu menuFile;
        JMenuItem mItemExport, mItemExit,mItemChange;
        JFrame f = new JFrame("授权证书生成器");
        Toolkit tk=Toolkit.getDefaultToolkit();
        Image image= tk.getImage(LICGens.class.getResource("images/glLogoSm.png"));//
//		Image image= tk.createImage("images/glLogoSm.png");
        f.setIconImage(image);
        JLabel ipLabel = new JLabel("IP     地址");
        JLabel macLabel = new JLabel("MAC地址");
        JTextField ipTextField = new JTextField();// TextField 目录的路径
        JTextField macTextField = new JTextField();// 文件的路径
        JButton genBtn = new JButton("生成证书");
        generateBtnListener(genBtn,macTextField,ipTextField);

        f.setSize(750, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setLayout(null); // 设置窗体布局为空布局

        Font font = new Font("仿宋", 1, 20);
        Font mItemFont = new Font("仿宋", 1, 16);

        // 设置面板背景色为蓝色，如果不引入AWT包，程序将出错，可以试试看
        p.setBackground(Color.GRAY);
        p.setSize(750, 500); // 设置面板对象大小
        f.getContentPane().add(p); // 将面板添加到窗体中
        // 如果使用下面添加面板的方法，面板将布满整个窗口，可以试试看
        //f.setContentPane(p);

        ipLabel.setBounds(30, 10, 70, 30);
        ipTextField.setBounds(95, 10, 200, 30);
        macLabel.setBounds(30, 55, 70, 30);
        macTextField.setBounds(95, 55, 200, 30);
        JLabel t = new JLabel("*");
        t.setBounds(300, 55, 200, 30);
        t.setForeground(Color.RED);
        genBtn.setBounds(600, 10, 100, 80);

        //设置默认提示
        macTextField.setText("3C-A0-67-EF-84-5D,此为提示");
        macTextField.addFocusListener(new FocusListenerUtils("3C-A0-67-EF-84-5D,此为提示", macTextField));

        p.setLayout(null);
        p.add(ipLabel);
        p.add(ipTextField);
        p.add(macLabel);
        p.add(macTextField);
        p.add(t);
        p.add(genBtn);
//        p.add(datepick);

        JLabel effectiveDateLabel = new JLabel("有效日期");
        JLabel issuedDateLabel = new JLabel("签发日期");
        JLabel gapLabel = new JLabel("至");
        issuedDateLabel.setBounds(330, 10, 70, 30);
        effectiveDateLabel.setBounds(330, 55, 70, 30);
        gapLabel.setBounds(480, 55, 20, 30);
        p.add(issuedDateLabel);
        p.add(effectiveDateLabel);
        p.add(gapLabel);

        issuedDate.setValue(new Date());
        issuedDate.setEditor(issuedDateEditor);
        issuedDate.setBounds(390, 10, 195, 30);
        p.add(issuedDate);
        startDate.setValue(new Date());
        startDate.setEditor(startDateEditor);
        startDate.setBounds(390, 55, 85, 30);
        p.add(startDate);

        endDate.setValue(computeEndDate(new Date()));
        endDate.setEditor(endDateEditor);
        endDate.setBounds(500, 55, 85, 30);
        p.add(endDate);




        bottomP.setPreferredSize(new Dimension(250,100));//添加面板1
        bottomP.setBackground(Color.WHITE);
        bottomP.setBounds(25, 155, 700, 220);
        bottomP.setLayout(null);

        JPanel contentLogoPane=new JPanel(){
            @Override
            public void paint(Graphics g) {
//        		ImageIcon icon=new ImageIcon("glLogoBig.png");
                URL imgURL = LICGens.class.getResource("images/glLogoBig.png");//
                ImageIcon icon=new ImageIcon(imgURL);
                Image image=icon.getImage();
                g.drawImage(image, 0,0,null);
            }
            // TODO Auto-generated method stub
        };
        contentLogoPane.setBounds(235, 30, 250, 100);
        bottomP.add(contentLogoPane);

        JLabel alert = new JLabel("注：本工具仅供内部使用");
        alert.setBounds(275, 135, 170, 30);
        alert.setForeground(Color.red);
        JLabel tooltipLabel = new JLabel("默认生成路径C:\\license_g");
        tooltipLabel.setBounds(300, 150, 170, 30);
        JLabel rc = new JLabel("design by zyc,made by zyc");
        rc.setBounds(275, 180, 170, 30);
        rc.setForeground(Color.orange);
        bottomP.add(tooltipLabel);
        bottomP.add(rc);
        bottomP.add(alert);

	/*	JLabel tooltipLabels = new JLabel("license文件生成成功");
		tooltipLabels.setBounds(10, 10, 270, 30);
		tooltipLabels.setFont(font);
		bottomP.add(tooltipLabels);*/
        p.add(bottomP);

        mb = new JMenuBar(); // 创建菜单栏MenuBar

        menuFile = new JMenu("文件");
        menuFile.setFont(font);

        mItemChange = new JMenuItem("替换私钥库");
        mItemChange.setFont(mItemFont);
        menuFile.add(mItemChange);
        // 加入分割线
        menuFile.addSeparator();

        mItemExit= new JMenuItem("退出");
        mItemExit.setFont(mItemFont);
        menuFile.add(mItemExit);
        // 菜单栏中加入“文件”菜单
        mb.add(menuFile);

        JMenu settingMenu = new JMenu("设置");
        settingMenu.setFont(font);

        mItemExport = new JMenuItem("导出路径");
        mItemExport.setFont(mItemFont);
        settingMenu.add(mItemExport);

        mb.add(settingMenu);

        // setMenuBar:将此窗体的菜单栏设置为指定的菜单栏。
        f.setJMenuBar(mb);
        f.setVisible(true);

        changeListener(mItemChange);//change privateKeys file
        exitListener(mItemExit);//exit app
        settingListener(mItemExport);
    }

    public static void changeListener(JMenuItem mItemChange){
        //鼠标监听器
        mItemChange.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JOptionPane.showMessageDialog(null, "待后续升级...");
                System.out.println(""+mItemChange.getLabel());
            }
        });
    }
    /**
     * 退出应用
     * @param mItemExit
     * @Description:
     */
    public static void exitListener(JMenuItem mItemExit){
        //鼠标监听器
        mItemExit.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });
    }
    public static void settingListener(JMenuItem mItemExport){
        //鼠标监听器
        mItemExport.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                JOptionPane.showMessageDialog(null, "待后续升级...");

		       /* double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

		        double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
				System.out.println("tests");

//				   }
				choosePath(jfChooser);
	    	    jFrame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
	    	    jFrame.setSize(280, 200);// 设定窗口大小
	    	    jFrame.setContentPane(p);// 设置布局
				jFrame.add(jfChooser);
				jFrame.setVisible(true);// 窗口可见
*///				jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
            }
        });
    }
    protected static void choosePath(JFileChooser jfChooser) {
        FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
        jfChooser.setFileSelectionMode(1);// 设定只能选择到文件夹
        int state = jfChooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
        if (state == 1) {
            return;
        } else {
            File f = jfChooser.getSelectedFile();// f为选择到的目录
//             text1.setText(f.getAbsolutePath());
            System.out.println("========="+f.getAbsolutePath());
        }

    }
    /**
     * 时间监听的方法
     */
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
//        if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个
        jfChooser.setFileSelectionMode(1);// 设定只能选择到文件夹
        int state = jfChooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
        if (state == 1) {
            return;
        } else {
            File f = jfChooser.getSelectedFile();// f为选择到的目录
//                text1.setText(f.getAbsolutePath());
            System.out.println("========="+f.getAbsolutePath());
        }
//        }
//        // 绑定到选择文件，先择文件事件
//        if (e.getSource().equals(button2)) {
//            jfChooser.setFileSelectionMode(0);// 设定只能选择到文件
//            int state = jfChooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
//            if (state == 1) {
//                return;// 撤销则返回
//            } else {
//                File f = jfChooser.getSelectedFile();// f为选择到的文件
//                text2.setText(f.getAbsolutePath());
//            }
//        }
//        if (e.getSource().equals(button3)) {
//            // 弹出对话框可以改变里面的参数具体得靠大家自己去看，时间很短
//            JOptionPane.showMessageDialog(null, "弹出对话框的实例，欢迎您-漆艾琳！", "提示", 2);
//        }
    }
    /**
     * 按钮事件
     * @param genBtn
     * @Description:
     */
    public static void generateBtnListener(JButton genBtn,JTextField macTextField,JTextField ipTextField){
        genBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                super.mousePressed(e);
                tooltipLabels.setText("");//置空提示
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                super.mouseReleased(e);
                String end = endDateEditor.getTextField().getText();
                String start = startDateEditor.getTextField().getText();
                String issued = issuedDateEditor.getTextField().getText();
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    issued = sdf.format(sdfDate.parse(issued)) ;
                } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.out.println(issued);
                int token = compareDate(start, end);
                licenseCreateTest lt = new licenseCreateTest();
                String mac = macTextField.getText();
                String ip = ipTextField.getText();
                if(null==ip||"".equals(ip)){
//    				JOptionPane.showMessageDialog(null, "请输入IP地址");
//    				return;
                }else{
                    if(!checkIp(ip)){//格式不正确
                        ipTextField.setText("");
                        ipTextField.setFocusable(true);
                        JOptionPane.showMessageDialog(null, "输入的IP地址格式不正确");
                        return;
                    }
                }
                if(null==mac||"".equals(mac)){
                    JOptionPane.showMessageDialog(null, "请输入MAC地址");
                }else{
                    mac = mac.toUpperCase();
                    if(mac.contains(":")){
                        mac = mac.replace(":", "-");
                    }
                    if(!checkMac(mac)){//格式不正确
                        System.out.println("testst==zhongwen===");
                        JOptionPane.showMessageDialog(null, "输入MAC地址格式不正确,请安输入框提示格式输入");
                        macTextField.setText("");
                        macTextField.setFocusable(true);
                    }else{//格式正确
                        if(token==1){
                            JOptionPane.showMessageDialog(null, "起始时间不能为空！");
                        }else if(token==2){
                            JOptionPane.showMessageDialog(null, "结束时间必须大于开始时间");
                        }else{//token==0
                            boolean bool = lt.generateLic(mac, ip,issued,start,end);
                            Font font = new Font("仿宋", 1, 16);
                            tooltipLabels.setBounds(10, 10, 270, 30);
                            tooltipLabels.setFont(font);
                            bottomP.add(tooltipLabels);
                            bottomP.validate();
                            bottomP.repaint();
                            if(bool){//
                                tooltipLabels.setText("license文件生成成功~！");
                            }else{
//        						JLabel tooltipLabelf = new JLabel("license文件生成失败");
//        						tooltipLabelf.setBounds(10, 10, 270, 30);
//        						tooltipLabelf.setFont(font);
//        						bottomP.add(tooltipLabelf);
                                tooltipLabels.setText("license文件生成失败~！！！");
                                // 重绘面板（重要重要重要）
//        						bottomP.revalidate();
//        						p.revalidate();
                            }
                        }
                    }
                }

            }
        });
    }
    /**
     * 判断输入内容是否含有中文
     * @param str
     * @return
     * @Description:
     */
    public static boolean isContainChinese(String str) {
        byte[] bytes = str.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] >= (byte) 0x81 && bytes[i] <= (byte) 0xfe) {
                return false;
            }
        }
        return true;
    }
    /**
     * @Title: checkMac
     * @Description: 检查mac合法性
     * @param @param mac
     * @param @return
     * @return boolean
     * @throws
     */
    public static boolean checkMac(String mac){
        if(isContainChinese(mac)){
            String patternMac="^[A-F0-9]{2}(-[A-F0-9]{2}){5}$";
            if(!Pattern.compile(patternMac).matcher(mac).find()){
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    public static boolean checkIp(String ip) {
        if (ip != null && !ip.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (ip.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }
    public static boolean checkDate(String mac){
        if(isContainChinese(mac)){
            String patternMac="\\^[A-F0-9]{2}(-[A-F0-9]{2}){5}\\$";
            if(!Pattern.compile(patternMac).matcher(mac).find()){
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    public static Date computeEndDate(Date endDate){
        try {
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(endDate);
            rightNow.add(Calendar.DAY_OF_YEAR,1);//日期加1天
            Date dt1=rightNow.getTime();
            endDate =dt1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endDate;
    }
    public static int compareDate(String start,String end){
        int token=0;//成功
        try {
            if("".equals(start)||"".equals(end)){
                token= 1;//失败时间为空
            }else{
                SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd");
                if(sdfStart.parse(start).after(sdfEnd.parse(end))||sdfStart.parse(start).equals(sdfEnd.parse(end))){
                    token= 2;//开始时间大于结束时间
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
