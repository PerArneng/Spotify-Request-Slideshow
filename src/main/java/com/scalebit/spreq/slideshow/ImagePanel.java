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

    private final Image image;
    private final int imageWidth;
    private final int imageHeight;

    public ImagePanel(File photo) {

        this.setBackground(new Color((int)(Math.random()*200), (int)(Math.random()*200), (int)(Math.random()*200)));
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

        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();


        Rectangle imageBounds = calcCenter(panelWidth, panelHeight, imageWidth, imageHeight, false);

        g2.drawImage(image, imageBounds.x, imageBounds.y, imageBounds.width, imageBounds.height, this);
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
