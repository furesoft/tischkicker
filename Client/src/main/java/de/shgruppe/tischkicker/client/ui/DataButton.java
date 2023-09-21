package de.shgruppe.tischkicker.client.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class RoundedBorder implements Border {

    private final int radius;


    RoundedBorder(int radius) {
        this.radius = radius;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}

public class DataButton extends JButton implements MouseListener {
    private Object data;
    private boolean isMouseOver;

    public DataButton(String text) {
        setText(text);
        setForeground(Colors.BUTTON_SCHRIFT);
        setBackground(Colors.BUTTON_BACKGROUND);

        setBorder(new RoundedBorder(23));
        setOpaque(true);

        Dimension size = getPreferredSize();
        setPreferredSize(size);

        setContentAreaFilled(false);

        addMouseListener(this);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        g.setFont(getFont());

        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

        paintBorder(g);

        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(getText());
        int textHeight = metrics.getDescent();

        g.setColor(getForeground());
        g.drawString(getText(), getWidth() / 2 - textWidth / 2, getHeight() / 2 - textHeight / 2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseOver = true;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseOver = false;
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}