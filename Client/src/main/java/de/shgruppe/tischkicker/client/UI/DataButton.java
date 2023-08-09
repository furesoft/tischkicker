package de.shgruppe.tischkicker.client.UI;

import javax.swing.*;

public class DataButton extends JButton {
    private Object data;

    public DataButton(String text) {
        setText(text);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}