package com.scalebit.spreq.ui;

import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements PlayerEventListener {

    private final JLabel authorLabel = new JLabel("", JLabel.CENTER);
    private final JLabel titleLabel = new JLabel("", JLabel.CENTER);
    private final JLabel requesterLabel = new JLabel("", JLabel.CENTER);
    private final Font textFont = new Font(getFontFamily(), Font.PLAIN, 50);

    private final Color backgroundColor = Color.GRAY;
    private final Color foregroundColor = Color.WHITE;


    public MainFrame(String title) throws HeadlessException {
        super(title);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenBounds = new Rectangle(0,0, screenSize.width, screenSize.height);
        screenBounds.grow(-400, -400);
        this.setBounds(screenBounds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        authorLabel.setForeground(foregroundColor);
        authorLabel.setFont(textFont);

        titleLabel.setForeground(foregroundColor);
        titleLabel.setFont(textFont);

        requesterLabel.setForeground(foregroundColor);
        requesterLabel.setFont(textFont);

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("debug, flowy"));
        panel.setOpaque(true);
        panel.setBackground(backgroundColor);
        panel.setLayout(new FlowLayout());
        panel.add(authorLabel);
        panel.add(titleLabel);
        panel.add(requesterLabel);
        //panel.setBorder(new LineBorder(Color.BLACK));

        this.setContentPane(panel);

    }

    private static String getFontFamily() {
        String def = "Serif"; //"Zapfino";
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
                authorLabel.setText("");
                titleLabel.setText("");
                requesterLabel.setText("");

                authorLabel.setText(fixStringLength(event.getArtist()));
                titleLabel.setText(fixStringLength(event.getTitle()));
                String requestor = event.getRequester();
                if (requestor != null) {
                    requesterLabel.setText(
                            String.format("Önskad av: %s", fixStringLength(event.getRequester()))
                    );
                }

            }
        });
    }

    private static String fixStringLength(String str) {
        return str.length() > 30 ? str.substring(0, 30) + "..." : str;
    }
}
