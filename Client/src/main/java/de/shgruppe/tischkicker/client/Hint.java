package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;

public class Hint {
    public static void enableFor(JTextField textbox, String hint) {
        Font defaultFont = textbox.getFont();
        Color defaultForeground = textbox.getForeground();

        textbox.setText(hint);
        textbox.setFont(new Font(textbox.getFont().getName(), Font.ITALIC, textbox.getFont().getSize()));
    }
}
