package smanageT;

import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class Combotest extends BasicComboBoxUI {
    
    @Override
    protected void installDefaults() {
        super.installDefaults();
        comboBox.setBorder(new LineBorder(Color.black, 1));
    }

    @Override
    public void configureArrowButton() {
        super.configureArrowButton();
        arrowButton.setBackground(new Color(51, 77, 117));
        arrowButton.setForeground(Color.black);
    }
}