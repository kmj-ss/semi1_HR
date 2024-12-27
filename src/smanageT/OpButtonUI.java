package smanageT;

import javax.swing.AbstractButton;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class OpButtonUI extends BasicButtonUI {

    @Override
    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        // Override to do nothing, effectively disabling focus painting
    }
}
