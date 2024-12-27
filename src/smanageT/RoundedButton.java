package smanageT;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class RoundedButton extends BasicButtonUI {
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        decorate((AbstractButton) c);
    }

    protected void decorate(AbstractButton button) {
        button.setBorderPainted(false);
        button.setOpaque(false);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        int width = button.getWidth();
        int height = button.getHeight();

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (button.getModel().isArmed()) {
            graphics.setColor(button.getBackground().darker());
        } else if (button.getModel().isRollover()) {
            graphics.setColor(button.getBackground().brighter());
        } else {
            graphics.setColor(button.getBackground());
        }

        graphics.fillRoundRect(0, 0, width, height, 10, 10);

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(button.getText(), graphics).getBounds();

        int textX = (width - stringBounds.width) / 2;
        int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();

        graphics.setColor(button.getForeground());
        graphics.setFont(button.getFont());
        graphics.drawString(button.getText(), textX, textY);
        graphics.dispose();
    }

    public static ComponentUI createUI(JComponent c) {
        return new RoundedButton();
    }
}