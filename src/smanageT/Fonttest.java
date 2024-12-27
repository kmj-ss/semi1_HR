package smanageT;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Fonttest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Font Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Load custom font from TTF file using relative path
        Font customFont = loadFont("src/Nanum/NanumSquareR.ttf");
        
        if (customFont != null) {
            JLabel label = new JLabel("나눔스퀘어폰트가나다라마바사");
            label.setFont(customFont.deriveFont(Font.PLAIN, 30));
            frame.getContentPane().add(label, BorderLayout.CENTER);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to load custom font.");
        }
        
        frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // Method to load custom font from TTF file
    private static Font loadFont(String path) {
        Font customFont = null;
        try {
            File fontFile = new File(path);
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            Font.createFont(0, fontFile);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return customFont;
    }
}
