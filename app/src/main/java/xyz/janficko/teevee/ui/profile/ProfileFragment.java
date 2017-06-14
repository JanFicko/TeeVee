package xyz.janficko.teevee.ui.profile;

import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.FocusHighlight;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.widget.Toast;

import xyz.janficko.teevee.models.Card;
import xyz.janficko.teevee.presenters.CardPresenterSelector;
import xyz.janficko.teevee.ui.base.GridFragment;

public class ProfileFragment extends GridFragment {
    private static final int COLUMNS = 4;
    private final int ZOOM_FACTOR = FocusHighlight.ZOOM_FACTOR_SMALL;
    private ArrayObjectAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAdapter();
        //loadData();
        getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
    }


    private void setupAdapter() {
        VerticalGridPresenter presenter = new VerticalGridPresenter(ZOOM_FACTOR);
        presenter.setNumberOfColumns(COLUMNS);
        setGridPresenter(presenter);

        CardPresenterSelector cardPresenter = new CardPresenterSelector(getActivity());
        mAdapter = new ArrayObjectAdapter(cardPresenter);
        setAdapter(mAdapter);

        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(
                    Presenter.ViewHolder itemViewHolder,
                    Object item,
                    RowPresenter.ViewHolder rowViewHolder,
                    Row row) {
                Card card = (Card)item;
                Toast.makeText(getActivity(),
                        "Clicked on "+card.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

		/*private void loadData() {
			String json = Utils.inputStreamToString(getResources().openRawResource(
					R.raw.grid_example));
			CardRow cardRow = new Gson().fromJson(json, CardRow.class);
			mAdapter.addAll(0, cardRow.getCards());
		}*/
}