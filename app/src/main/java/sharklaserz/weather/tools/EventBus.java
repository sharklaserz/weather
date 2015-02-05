package sharklaserz.weather.tools;

import com.squareup.otto.Bus;

/**
 * Created by toddish on 2/4/2015.
 */
public final class EventBus {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {

        return BUS;
    }

    private EventBus() {
    }
}
