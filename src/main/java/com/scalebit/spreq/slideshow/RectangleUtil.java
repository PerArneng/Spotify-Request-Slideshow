package com.scalebit.spreq.slideshow;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: pure
 * Date: 8/8/13
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RectangleUtil {


    public static Rectangle fit(Rectangle source, Rectangle target) {

        double heightScaleFactor = target.getHeight() / source.getHeight();
        double widthScaleFactor = target.getWidth() / source.getWidth();

        Rectangle scaledByWidth = scale(source, widthScaleFactor);
        Rectangle scaledByHeight = scale(source, heightScaleFactor);

        scaledByWidth = centerInRect(scaledByWidth, target);
        scaledByHeight = centerInRect(scaledByHeight, target);

        if (target.contains(new Rectangle(scaledByWidth.x+1, scaledByWidth.y+1,
                                          scaledByWidth.width-2, scaledByWidth.height-2))) {
            return scaledByWidth;
        } else {
            return scaledByHeight;
        }
    }


    public static Rectangle scale(Rectangle rect, double scaleFactor) {
        return new Rectangle(
            rect.x, rect.y,
            (int)(rect.getWidth() * scaleFactor),
            (int)(rect.getHeight() * scaleFactor)
        );
    }

    public static Rectangle centerInRect(Rectangle source, Rectangle target) {
        int centerX = target.x + (int)(target.getWidth()/2);
        int centerY = target.y + (int)(target.getHeight()/2);
        return new Rectangle(
            centerX - (int)(source.getWidth()/2),
            centerY - (int)(source.getHeight()/2),
            source.width,
            source.height
        );
    }


}
