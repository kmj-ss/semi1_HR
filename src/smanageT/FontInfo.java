package smanageT;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontInfo {

    // ��Ʈ �ε� �޼���
    public static Font loadFont(String fontFilePath, int style, float size) {
        Font font = null;
        InputStream fontStream = null;
        try {
            // ���ҽ��κ��� ��Ʈ ���� �б�
            fontStream = FontInfo.class.getResourceAsStream(fontFilePath);
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = font.deriveFont(style, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        } finally {
            if (fontStream != null) {
                try {
                    fontStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return font;
    }

    // ��ü UI�� ��Ʈ �����ϴ� �޼���
    public static void setUIFont(Font font) {
        FontUIResource fontResource = new FontUIResource(font);
        UIManager.put("Button.font", fontResource);
        UIManager.put("ToggleButton.font", fontResource);
        UIManager.put("RadioButton.font", fontResource);
        UIManager.put("CheckBox.font", fontResource);
        UIManager.put("ColorChooser.font", fontResource);
        UIManager.put("ComboBox.font", fontResource);
        UIManager.put("Label.font", fontResource);
        UIManager.put("List.font", fontResource);
        UIManager.put("MenuBar.font", fontResource);
        UIManager.put("MenuItem.font", fontResource);
        UIManager.put("RadioButtonMenuItem.font", fontResource);
        UIManager.put("CheckBoxMenuItem.font", fontResource);
        UIManager.put("Menu.font", fontResource);
        UIManager.put("PopupMenu.font", fontResource);
        UIManager.put("OptionPane.font", fontResource);
        UIManager.put("Panel.font", fontResource);
        UIManager.put("ProgressBar.font", fontResource);
        UIManager.put("ScrollPane.font", fontResource);
        UIManager.put("Viewport.font", fontResource);
        UIManager.put("TabbedPane.font", fontResource);
        UIManager.put("Table.font", fontResource);
        UIManager.put("TableHeader.font", fontResource);
        UIManager.put("TextField.font", fontResource);
        UIManager.put("PasswordField.font", fontResource);
        UIManager.put("TextArea.font", fontResource);
        UIManager.put("TextPane.font", fontResource);
        UIManager.put("EditorPane.font", fontResource);
        UIManager.put("TitledBorder.font", fontResource);
        UIManager.put("ToolBar.font", fontResource);
        UIManager.put("ToolTip.font", fontResource);
        UIManager.put("Tree.font", fontResource);
        
        
       
//        UIManager.put("Panel.background",Color.white);
        
        
        
        UIManager.put("ComboBox.arrowButton.background", new Color(239, 246, 255));
        UIManager.put("ComboBox.border", new LineBorder(Color.black, 1)); // �޺��ڽ� �׵θ�
//        UIManager.put("ButtonUI","smanageT.RoundedButton");
        UIManager.put("Button.foreground", new Color(239, 246, 255));
        UIManager.put("Button.background", new Color(51, 77, 117));
        UIManager.put("Button.focusPainted", false);
        
        UIManager.put("Table.background", new Color(243,247,252));
        UIManager.put("Scorllpane.backgrond",new Color(243,247,252));
        
        
        
    }
}
