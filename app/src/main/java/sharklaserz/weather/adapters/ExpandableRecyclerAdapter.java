package sharklaserz.weather.adapters;

/**
 * Created by GLaDOS on 3/23/2015.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import sharklaserz.weather.R;
import sharklaserz.weather.loader.WeatherLoader;
import sharklaserz.weather.model.ResponseBody;

public class ExpandableRecyclerAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableRecyclerAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //final WeatherAdapter = new WeatherAdapter();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_child_recycler, null);
        }

        RecyclerView mChildRecycler = (RecyclerView) convertView
                .findViewById(R.id.child_recycle_view);

        // use a linear layout manager
        mChildRecycler.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(parent.getContext());
        mChildRecycler.setLayoutManager(mLayoutManager);
        //dummy adapter
        mChildRecycler.setAdapter(new DummyAdapter());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_parent_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblParentHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


 class DummyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> dataList = new ArrayList<String>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        String data;

        public ViewHolder(View v) {
            super(v);
            data = "dummy adapter";
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() > 0 ? dataList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.future_weather_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

}