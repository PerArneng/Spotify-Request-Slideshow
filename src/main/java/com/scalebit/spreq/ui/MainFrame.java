package com.scalebit.spreq.ui;

import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;

public class MainFrame extends JFrame implements PlayerEventListener {

    JLabel textPanel = new JLabel();

    public MainFrame(String title) throws HeadlessException {
        super(title);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenBounds = new Rectangle(0,0, screenSize.width, screenSize.height);
        screenBounds.grow(-100, -100);
        this.setBounds(screenBounds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        textPanel.setHorizontalTextPosition(JLabel.CENTER);
        this.setContentPane(this.textPanel);

    }

    @Override
    public void onEvent(final PlayerEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textPanel.setText(event.getArtist() + " - " + event.getTitle());
            }
        });
    }
}
