package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Hint {
    private static final Map<JTextField, String> hints = new HashMap<>();

    public static void enableFor(JTextField textbox, String hint) {
        Font defaultFont = textbox.getFont();
        Color defaultForeground = textbox.getForeground();
        Font hintFont = new Font(textbox.getFont().getName(), Font.ITALIC, textbox.getFont().getSize());

        textbox.setForeground(Color.GRAY);
        textbox.setText(hint);
        textbox.setFont(hintFont);

        HintListener listener = new HintListener(defaultFont, defaultForeground, textbox, hint, hintFont);
        textbox.addFocusListener(listener);

        hints.put(textbox, hint);
    }

    public static void reset(JTextField field) {
        String hint = hints.get(field);
        Font hintFont = new Font(field.getFont().getName(), Font.ITALIC, field.getFont().getSize());

        field.setForeground(Color.GRAY);
        field.setText(hint);
        field.setFont(hintFont);
    }
}
