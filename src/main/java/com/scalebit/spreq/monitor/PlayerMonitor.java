package com.scalebit.spreq.monitor;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: pure
 * Date: 7/11/13
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PlayerMonitor {



    void start(Map<String, String> params, PlayerEventListener listener);

}
