   package org.example.javawebv2.com.v2.utils;

   import javax.imageio.ImageIO;
   import java.awt.*;
   import java.awt.image.BufferedImage;
   import java.io.IOException;
   import java.io.OutputStream;
   import java.util.Random;
   public class codepic {

       private static final int WIDTH = 100; // 图片宽度
       private static final int HEIGHT = 50; // 图片高度
       private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 可选字符集

       public String generateCode() {
           StringBuilder code = new StringBuilder();
           Random random = new Random();
           for (int i = 0; i < 4; i++) { // 生成4位验证码
               code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
           }
           return code.toString();
       }

       public void createImage(String code, OutputStream os) throws IOException {
           BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
           Graphics2D g2d = image.createGraphics();

           // 设置背景色
           g2d.setColor(Color.WHITE);
           g2d.fillRect(0, 0, WIDTH, HEIGHT);

           // 设置字体
           Font font = new Font("Arial", Font.BOLD, 24);
           g2d.setFont(font);

           // 设置文字颜色
           g2d.setColor(Color.BLACK);

           // 绘制验证码
           g2d.drawString(code, 20, 35);

           // 添加干扰线
           Random random = new Random(); // 声明并初始化 Random 对象
           g2d.setColor(Color.LIGHT_GRAY);
           for (int i = 0; i < 5; i++) {
               int x1 = random.nextInt(WIDTH);
               int y1 = random.nextInt(HEIGHT);
               int x2 = random.nextInt(WIDTH);
               int y2 = random.nextInt(HEIGHT);
               g2d.drawLine(x1, y1, x2, y2);
           }
           // 清理资源
           g2d.dispose();
           // 输出图片
           ImageIO.write(image, "png", os);
       }
   }
   