package com.scalebit.spreq.ui;

import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;
import com.scalebit.spreq.slideshow.PhotoProvider;
import com.scalebit.spreq.slideshow.SlideShowPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame implements PlayerEventListener {

    private final JLabel songLabel = new JLabel("", JLabel.CENTER);
    private final JLabel requesterLabel = new JLabel("", JLabel.CENTER);
    private final Font songFont = new Font(getFontFamily(), Font.PLAIN, 30);
    private final Font requestFont = new Font(getFontFamily(), Font.PLAIN, 25);


    private final Color backgroundColor = Color.BLACK;
    private final Color foregroundColor = Color.WHITE;

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private SlideShowPanel slideshowPanel;

    private static Logger LOG = Logger.getLogger(MainFrame.class.getName());


    public MainFrame(String title, PhotoProvider photoProvider) throws HeadlessException {
        super(title);

        enableOSXFullscreen(this);

        Rectangle screenBounds = calculateBounds();
        this.setBounds(screenBounds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        songLabel.setForeground(foregroundColor);
        songLabel.setFont(songFont);

        requesterLabel.setForeground(foregroundColor);
        requesterLabel.setFont(requestFont);

        slideshowPanel = new SlideShowPanel(photoProvider);

        JPanel textPanel = new JPanel(new MigLayout("", "[grow]","[][][]"));
        textPanel.setOpaque(true);
        textPanel.setBackground(backgroundColor);
        textPanel.add(songLabel, "wrap, grow");
        textPanel.add(requesterLabel, "grow");

        JPanel xpanel = new JPanel(new BorderLayout());
        xpanel.add(slideshowPanel, BorderLayout.CENTER);
        xpanel.add(textPanel, BorderLayout.SOUTH);

        this.setContentPane(xpanel);

    }

    /**
     * @param window
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void enableOSXFullscreen(Window window) {
        try {
            Class util = Class.forName("com.apple.eawt.FullScreenUtilities");
            Class params[] = new Class[]{Window.class, Boolean.TYPE};
            Method method = util.getMethod("setWindowCanFullScreen", params);
            method.invoke(util, window, true);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "OS X Fullscreen FAIL", e);
        }
    }

    private Rectangle calculateBounds() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenBounds = new Rectangle(0,0, screenSize.width, screenSize.height);

        int shrinkX = (screenSize.width - DEFAULT_WIDTH)/2;
        int shrinkY = (screenSize.height - DEFAULT_HEIGHT)/2;

        screenBounds.grow(-shrinkX, -shrinkY);

        return screenBounds;
    }

    private static String getFontFamily() {
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
                songLabel.setText("");
                requesterLabel.setText("");

                songLabel.setText(fixStringLength(event.getArtist()) + " - " +
                                    fixStringLength(event.getTitle()));

                String requestor = event.getRequester();
                if (requestor != null) {
                    requesterLabel.setText(
                            String.format("Önskad av: %s", fixStringLength(event.getRequester()))
                    );
                } else {
                    requesterLabel.setText(" ~ ");
                }

            }
        });
    }

    private static String fixStringLength(String str) {
        return str.length() > 30 ? str.substring(0, 30) + "..." : str;
    }
}
