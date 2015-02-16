package sharklaserz.weather.loader;

/**
 * Created by toddish on 2/14/2015.
 */

import nucleus.model.Loader;

public abstract class TestLoader<ResponseT> extends Loader<ResponseT> {

    public void TestLoader() {}

    public void doTestForMe(ResponseT responseT) {
        notifyReceivers(responseT);
    }


}
