package game4;
/*
    添加按钮事件和移动业务实现(重绘)
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;

//定义类PictureFrame,继承JFrame
public class PictureFrame extends JFrame {
    // 存储图片编号
    private int[][] imgIdx = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0} //16要换成0(空白图)
    };

    // 定义两个int类型的数据用来定位空白图片(0号图片位置)
    private int x0;
    private int y0;

    // 将上左下右,求助,重置等按钮,提升到成员位置
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton helpButton;
    private JButton resetButton;

    private JPanel imagePanel;

    //在PictureFrame类中编写空参构造,构造中定义两个方法
    public PictureFrame() {
        initFrame(); // 窗体基本设置
        randomImg(); // 图片打乱
        paintView(); // 绘制基本组件
        addButtonEvent(); //按钮点击事件
        this.setVisible(true); // 窗体可见
    }

    //窗体基本设置
    public void initFrame() {
        this.setSize(960, 565);
        this.setTitle("动漫拼图");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
    }

    //窗口组件
    public void paintView() {
        // 1.标题图片
        JLabel titleLable = new JLabel(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/title.png"));
        titleLable.setBounds(254, 27, 232, 57);
        this.add(titleLable);

        // 2.主界面

        // 创建面板(用JPanel)
        imagePanel = new JPanel();
        imagePanel.setBounds(150, 114, 360, 360);
        imagePanel.setLayout(null); //也是容器组件
        // 遍历图片编号,展示图片
        for (int i = 0; i < imgIdx.length; i++) {
            for (int j = 0; j < imgIdx[i].length; j++) {
                JLabel imageLable = new JLabel(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/" + imgIdx[i][j] + ".png"));
                imageLable.setBounds(j * 90, i * 90, 90, 90);
                imagePanel.add(imageLable);
            }
        }
        this.add(imagePanel);

        // 参照图
        JLabel canzhaoLabel = new JLabel(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/canzhaotu.png"));
        canzhaoLabel.setBounds(574, 114, 122, 121);
        this.add(canzhaoLabel);

        // 上下左右, 求助, 重置按钮
        upButton = new JButton(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/shang.png"));
        downButton = new JButton(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/xia.png"));
        leftButton = new JButton(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/zuo.png"));
        rightButton = new JButton(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/you.png"));
        helpButton = new JButton(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/qiuzhu.png"));
        resetButton = new JButton(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/chongzhi.png"));

        upButton.setBounds(732, 265, 57, 57);
        downButton.setBounds(732, 347, 57, 57);
        leftButton.setBounds(650, 347, 57, 57);
        rightButton.setBounds(813, 347, 57, 57);
        helpButton.setBounds(626, 444, 108, 45);
        resetButton.setBounds(786, 444, 108, 45);

        this.add(upButton);
        this.add(downButton);
        this.add(leftButton);
        this.add(rightButton);
        this.add(helpButton);
        this.add(resetButton);

        // 背景图
        JLabel backgroungLabel = new JLabel(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/background.png"));
        backgroungLabel.setBounds(0, 0, 960, 530);
        this.add(backgroungLabel);
    }

    //打乱数组
    public void randomImg() {
        Random rdm = new Random();
        for (int i = 0; i < imgIdx.length; i++) {
            for (int j = 0; j < imgIdx[i].length; j++) {
                int rdm1Idx = rdm.nextInt(imgIdx.length);
                int rdm2Idx = rdm.nextInt(imgIdx[rdm1Idx].length);
                int temp = imgIdx[i][j];
                imgIdx[i][j] = imgIdx[rdm1Idx][rdm2Idx];
                imgIdx[rdm1Idx][rdm2Idx] = temp;
            }
        }

        // 记录0号图片位置
        out:
        for (int i = 0; i < imgIdx.length; i++) {
            for (int j = 0; j < imgIdx[i].length; j++) {
                if (imgIdx[i][j] == 0) {
                    x0 = i;
                    y0 = j;
                    break out;
                }
            }
        }
        System.out.println(x0 + ", " + y0);
    }

    // 按钮事件和移动
    public void addButtonEvent() {
        upButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 边界退出
                if (x0 == 3) {
                    return;
                }
                // 交换
                imgIdx[x0][y0] = imgIdx[x0 + 1][y0];
                imgIdx[x0 + 1][y0] = 0;
                x0++;
                // 重绘方法调用
                rePaintView();
            }

        });
        downButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x0 == 0) {
                    return;
                }
                imgIdx[x0][y0] = imgIdx[x0 - 1][y0];
                imgIdx[x0 - 1][y0] = 0;
                x0--;
                rePaintView();
            }
        });
        leftButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (y0 == 3) {
                    return;
                }
                imgIdx[x0][y0] = imgIdx[x0][y0 + 1];
                imgIdx[x0][y0 + 1] = 0;
                y0++;
                rePaintView();
            }
        });
        rightButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (y0 == 0) {
                    return;
                }
                imgIdx[x0][y0] = imgIdx[x0][y0 - 1];
                imgIdx[x0][y0 - 1] = 0;
                y0--;
                rePaintView();
            }
        });
        helpButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        resetButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


    }

    //移动的图片重新绘制
    public void rePaintView() {
        imagePanel.removeAll();
        for (int i = 0; i < imgIdx.length; i++) {
            for (int j = 0; j < imgIdx[i].length; j++) {
                JLabel imageLable = new JLabel(new ImageIcon("/Users/ethan.liu/Desktop/06_java_workspace/JavaCode_SE/Day09/imgs/" + imgIdx[i][j] + ".png"));
                imageLable.setBounds(j * 90, i * 90, 90, 90);
                imagePanel.add(imageLable);
            }
        }
        this.add(imagePanel);
        imagePanel.repaint();
    }
}
