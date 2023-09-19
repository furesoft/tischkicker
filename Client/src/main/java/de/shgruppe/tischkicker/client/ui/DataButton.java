package de.shgruppe.tischkicker.client.ui;

import javax.swing.*;

public class DataButton extends JButton {
    private Object data;

    public DataButton(String text) {

        setText(text);
        setForeground(Colors.BUTTON_SCHRIFT);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}