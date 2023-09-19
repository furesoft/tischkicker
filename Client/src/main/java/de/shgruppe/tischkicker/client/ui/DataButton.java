package de.shgruppe.tischkicker.client.ui;

import javax.swing.*;

public class DataButton extends JButton {
    private Object data;

    public DataButton(String text) {

        setText(text);
        setForeground(Colors.BUTTON_SCHRIFT);
        setBackground(Colors.BUTTON_BACKGROUND);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}