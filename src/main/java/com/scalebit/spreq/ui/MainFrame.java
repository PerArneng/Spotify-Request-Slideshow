package com.scalebit.spreq.ui;

import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements PlayerEventListener {

    JLabel authorLabel = new JLabel("", JLabel.CENTER);
    JLabel titleLabel = new JLabel("", JLabel.CENTER);

    public MainFrame(String title) throws HeadlessException {
        super(title);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenBounds = new Rectangle(0,0, screenSize.width, screenSize.height);
        screenBounds.grow(-400, -400);
        this.setBounds(screenBounds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        authorLabel.setBackground(Color.BLACK);
        authorLabel.setForeground(Color.WHITE);
        authorLabel.setFont(new Font(getFontFamily(), Font.PLAIN, 50));
        authorLabel.setOpaque(true);

        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(getFontFamily(), Font.PLAIN, 50));
        titleLabel.setOpaque(true);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(authorLabel);
        panel.add(titleLabel);

        this.setContentPane(panel);

    }

    private String getFontFamily() {
        String def = "Zapfino";
        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for ( int i = 0; i < fonts.length; i++ )
        {
            if (fonts[i].equals(def)) {
                return def;
            }
        }
        return "Serif";
    }

    @Override
    public void onEvent(final PlayerEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                authorLabel.setText(event.getArtist());
                titleLabel.setText(event.getTitle());

            }
        });
    }
}
