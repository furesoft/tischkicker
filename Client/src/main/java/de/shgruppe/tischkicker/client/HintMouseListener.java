package de.shgruppe.tischkicker.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintMouseListener implements FocusListener {
    private final Font defaultFont;
    private final Color defaultForeground;
    private final JTextField textbox;
    private final String hint;
    private final Font hintFont;

    public HintMouseListener(Font defaultFont, Color defaultForeground, JTextField textbox, String hint, Font hintFont) {
        this.defaultFont = defaultFont;
        this.defaultForeground = defaultForeground;
        this.textbox = textbox;
        this.hint = hint;
        this.hintFont = hintFont;
    }


    @Override
    public void focusGained(FocusEvent e) {
        textbox.setFont(new Font("Arial", 0, defaultFont.getSize()));
        textbox.setForeground(Color.BLACK);

        if (textbox.getText().equals(hint)) {
            textbox.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (textbox.getText().equals(hint) || textbox.getText().isEmpty()) {
            textbox.setFont(hintFont);
            textbox.setForeground(Color.GRAY);

            textbox.setText(hint);
        }
    }
}
