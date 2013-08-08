package com.scalebit.spreq.slideshow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;


public class SlideShowPanel extends JPanel {

    private static Logger LOG = Logger.getLogger(SlideShowPanel.class.getName());
    private Thread flipThread;
    private CardLayout cardLayout;
    private final java.util.List<String> cardNames = new ArrayList<String>();
    private final java.util.List<ImagePanel> cards = new ArrayList<ImagePanel>();
    private int selectedCard = 0;
    private static final int SLEEP_TIME = 7000; //milliseconds

    public SlideShowPanel(PhotoProvider provider) {
        this.setOpaque(true);
        this.setBackground(Color.GREEN);

        cardLayout = new CardLayout();

        this.setLayout(cardLayout);

        for (File photo : provider.getPhotoFileList()) {
            LOG.info("loading image: " + photo);
            ImagePanel imagePanel = new ImagePanel(photo);
            String cardName = photo.getAbsolutePath();
            this.add(imagePanel, cardName);
            this.cardNames.add(cardName);
            this.cards.add(imagePanel);
        }

        flipThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {

                    LOG.info(String.format("sheduling image %s %s", selectedCard, cardNames.get(selectedCard)));

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {


                            SlideShowPanel.this.cardLayout.show(
                                    SlideShowPanel.this,
                                    cardNames.get(selectedCard));

                            selectedCard++;

                            if (selectedCard == cardNames.size()) selectedCard = 0;
                        }
                    });


                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        flipThread.start();

    }

    public void setMainText(String text) {
        for (ImagePanel imagePanel : this.cards) {
            imagePanel.setMainText(text);
        }
    }
}
