package com.liferay.ide.intellij.swingspy;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

/**
 * @author Andy Wu
 */
public class SwingSpyInfoPopup extends JPanel{

    private JLabel label;
    private JTextArea textArea;

    public SwingSpyInfoPopup()
    {
        super(new BorderLayout());

        label = new JLabel();

        label.setText("Swing Spy Info");

        textArea = new JTextArea();

        add(label,BorderLayout.NORTH);
        add(textArea,BorderLayout.SOUTH);
    }

    public void setContent(String content)
    {
        textArea.setText(content);
    }
}
