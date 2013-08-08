package com.scalebit.spreq.slideshow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: pure
 * Date: 8/7/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImagePanel extends JPanel {

    public static final Color TEXT_BACKGROUND_COLOR = new Color(0, 0, 0, 150);
    public static final Color TEXT_FOREGROUND = Color.WHITE;
    private final Image image;
    private final int imageWidth;
    private final int imageHeight;

    private double aspectRatio = 0.0d;

    private final Font TEXT_FONT = new Font("Zapfino", Font.PLAIN, 25);

    private String mainText = "Some Artist - Some Song (Önskad Av: John)";

    public ImagePanel(File photo) {

        this.setBackground(Color.BLACK);
        this.setOpaque(true);

        ImageIcon imageIcon = new ImageIcon(photo.getAbsolutePath());
        this.image = imageIcon.getImage();

        imageWidth = this.image.getWidth(this);
        imageHeight = this.image.getHeight(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Rectangle imageBounds = RectangleUtil.fit(new Rectangle(0,0, imageWidth, imageHeight), this.getBounds());

        double newAspectRatio = imageBounds.getWidth()/imageBounds.getHeight();

        Image imageToDraw = this.image;
        if (newAspectRatio != this.aspectRatio) {
            imageToDraw = this.image.getScaledInstance(imageBounds.width, imageBounds.height, Image.SCALE_SMOOTH);
            this.aspectRatio = newAspectRatio;
        }
        g2.drawImage(imageToDraw, imageBounds.x, imageBounds.y, imageBounds.width, imageBounds.height, this);


        FontMetrics fontMetrics = g2.getFontMetrics(TEXT_FONT);
        int mainTextWidth = fontMetrics.stringWidth(mainText);
        int mainTextHeight = fontMetrics.getHeight();
        Rectangle mainTextBounds = RectangleUtil.centerInRect(
                                                    new Rectangle(0,0,mainTextWidth,mainTextHeight),
                                                    imageBounds
                                   );



        mainTextBounds.y = this.getHeight() - mainTextHeight - 40;


        Rectangle textBackground = new Rectangle(0, mainTextBounds.y, this.getWidth(), mainTextBounds.height);
        textBackground.grow(10, 10);

        g2.setColor(TEXT_BACKGROUND_COLOR);
        g2.fill(textBackground);

        g2.setColor(TEXT_FOREGROUND);
        g2.setFont(TEXT_FONT);
        g2.drawString(mainText, mainTextBounds.x, mainTextBounds.y + (mainTextHeight/2));

    }

    public void setMainText(String text) {
        this.mainText = text;
        this.repaint();
    }

    /**
     * Calculate the bounds of an image to fit inside a view after scaling and keeping the aspect ratio.
     * @param vw container view width
     * @param vh container view height
     * @param iw image width
     * @param ih image height
     * @param neverScaleUp if <code>true</code> then it will scale images down but never up when fiting
     * @return Same rect object that was provided to the method or a new one if <code>out</code> was <code>null</code>
     */
    private static Rectangle calcCenter (int vw, int vh, int iw, int ih, boolean neverScaleUp) {

        double scale = Math.min( (double)vw/(double)iw, (double)vh/(double)ih );

        int h = (int)(!neverScaleUp || scale<1.0 ? scale * ih : ih);
        int w = (int)(!neverScaleUp || scale<1.0 ? scale * iw : iw);
        int x = ((vw - w)>>1);
        int y = ((vh - h)>>1);


        return new Rectangle( x, y, x + w, y + h );
    }
}
