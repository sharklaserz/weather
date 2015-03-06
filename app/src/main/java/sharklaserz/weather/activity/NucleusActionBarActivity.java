package sharklaserz.weather.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import nucleus.presenter.Presenter;
import nucleus.presenter.PresenterCreator;
import nucleus.view.PresenterFinder;
import nucleus.view.PresenterProvider;


public class NucleusActionBarActivity<PresenterType extends Presenter> extends ActionBarActivity implements PresenterProvider<PresenterType> {

    private static final String PRESENTER_STATE_KEY = "presenter_state";

    private PresenterType presenter;

    @Override
    public PresenterType getPresenter() {
        return presenter;
    }

    protected PresenterCreator<PresenterType> getPresenterCreator() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle savedPresenterState = savedInstanceState == null ? null : savedInstanceState.getBundle(PRESENTER_STATE_KEY);
        PresenterCreator<PresenterType> creator = getPresenterCreator();
        if (creator != null)
            presenter = (PresenterType) PresenterFinder.getInstance().findParentPresenter(this).provide(creator, savedPresenterState);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (presenter != null)
            presenter.takeView(this);
    }

    /**
     * Presenter destruction is here because we want Activity's presenter to live longer than view's presenter
     */
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (presenter != null) {
            presenter.dropView(this);

            if (isFinishing()) {
                presenter.destroy();
                presenter = null;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (presenter != null)
            outState.putBundle(PRESENTER_STATE_KEY, presenter.save());
    }
}

