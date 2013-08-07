package com.scalebit.spreq.slideshow;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: pure
 * Date: 8/7/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PhotoProvider {

    List<File> getPhotoFileList();

}
