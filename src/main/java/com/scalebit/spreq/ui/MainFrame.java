package com.scalebit.spreq.ui;

import com.apple.eawt.AppEvent;
import com.apple.eawt.FullScreenAdapter;
import com.apple.eawt.FullScreenUtilities;
import com.scalebit.spreq.monitor.PlayerEvent;
import com.scalebit.spreq.monitor.PlayerEventListener;
import com.scalebit.spreq.slideshow.PhotoProvider;
import com.scalebit.spreq.slideshow.SlideShowPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame implements PlayerEventListener {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private SlideShowPanel slideshowPanel;

    private static Logger LOG = Logger.getLogger(MainFrame.class.getName());

    private Timer timer;

    public MainFrame(String title, PhotoProvider photoProvider) throws HeadlessException {
        super(title);

        JMenuBar menuBar = new JMenuBar();
        JMenu windowMenu = new JMenu("Window");
        menuBar.add(windowMenu);
        JMenuItem undecoratedMenuItem = new JMenuItem("Toggle Decorated");
        windowMenu.add(undecoratedMenuItem);
        undecoratedMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.setUndecorated(!MainFrame.this.isUndecorated());
            }
        });
        //this.setJMenuBar(menuBar);

        enableOSXFullscreen(this);

        Rectangle screenBounds = calculateBounds();
        this.setBounds(screenBounds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        slideshowPanel = new SlideShowPanel(photoProvider);


        this.setContentPane(slideshowPanel);

    }

    /**
     * @param window
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public  void enableOSXFullscreen(final Window window) {
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


    @Override
    public void onEvent(final PlayerEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                String songText = fixStringLength(event.getArtist()) + " - " +
                                    fixStringLength(event.getTitle());

                String requestor = event.getRequester();
                String requestorText = "~";
                if (requestor != null) {
                    requestorText =
                            String.format("%s", fixStringLength(event.getRequester()));
                }

                slideshowPanel.setMainText(String.format("%s (%s)", songText, requestorText));

            }
        });
    }

    private static String fixStringLength(String str) {
        return str.length() > 20 ? str.substring(0, 20) + "..." : str;
    }
}
