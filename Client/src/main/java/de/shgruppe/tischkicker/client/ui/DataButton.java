package de.shgruppe.tischkicker.client.ui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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