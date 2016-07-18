package com.edgardoagno.tournamentandroid;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.edgardoagno.tournamentandroid.ItemTouchHelperAdapter.ItemTouchHelperAdapter;
import com.edgardoagno.tournamentandroid.ItemTouchHelperAdapter.ItemTouchHelperViewHolder;
import com.edgardoagno.tournamentandroid.ItemTouchHelperAdapter.OnStartDragListener;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.ViewModels.GroupSettingsViewModel;
import com.wefika.horizontalpicker.HorizontalPicker;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by edgardoagno on 16/07/16.
 */
public class GroupSettingsTeamRecyclerAdapter extends RecyclerView.Adapter<GroupSettingsTeamRecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private final OnStartDragListener _dragStartListener;
    private GroupSettingsViewModel viewModel;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(FrameLayout container) {
            super(container);
        }
    }

    public class ItemViewHolder extends ViewHolder implements ItemTouchHelperViewHolder {
        @Bind(R.id.seed_text_view) TextView _seedTextView;
        @Bind(R.id.team_name_edit_text) EditText _teamNameEditText;
        @Bind(R.id.team_handle_view) ImageView _teamHandleView;
        public ItemViewHolder(FrameLayout container) {
            super(container);
            ButterKnife.bind(this, container);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public class HeaderViewHolder extends ViewHolder implements  HorizontalPicker.OnItemSelected {

        @Bind(R.id.group_edit_text) EditText _groupNameEditText;
        @Bind(R.id.radio_round_robin) RadioButton _radioRoundRobin;
        @Bind(R.id.radio_american) RadioButton _radioAmerican;
        @Bind(R.id.radio_single) RadioButton _radioSingle;
        @Bind(R.id.radio_double) RadioButton _radioDouble;
        @Bind(R.id.picker_team_count) HorizontalPicker _pickerTeamCount;
        private GroupSettingsViewModel __viewModel;

        // Constructor

        public HeaderViewHolder(FrameLayout container, GroupSettingsViewModel viewModel) {
            super(container);
            this.__viewModel = viewModel;
            ButterKnife.bind(this, container);
            this._pickerTeamCount.setOnItemSelectedListener(this);
            CharSequence[] s = this.__viewModel.getAllowedTeamCounts();
            this._pickerTeamCount.setValues(s);

            CharSequence[] allowedTeamCounts = this.__viewModel._group.getScheduleType().getAllowedTeamCounts();
            String teamCount = Integer.toString(__viewModel._group.teamCount);
            int index = Arrays.asList(allowedTeamCounts).indexOf(teamCount);
            this._pickerTeamCount.setSelectedItem(index);
        }

        @OnTextChanged(R.id.group_edit_text)
        public void onGroupNameChanged() {
            String name = _groupNameEditText.getText().toString();
            this.__viewModel.setGroupName(name);
        }

        @OnClick({R.id.radio_round_robin, R.id.radio_american, R.id.radio_single, R.id.radio_double })
        public void onClickScheduleTypeGroup() {
            if (_radioRoundRobin.isChecked()) {
                setScheduleType(ScheduleType.RoundRobin);
            } else if (_radioAmerican.isChecked()) {
                setScheduleType(ScheduleType.American);
            } else if (_radioSingle.isChecked()) {
                setScheduleType(ScheduleType.SingleElimination);
            } else if (_radioDouble.isChecked()) {
                setScheduleType(ScheduleType.DoubleElimination);
            }
        }

        private void setScheduleType(ScheduleType scheduleType) {
            CharSequence oldTeamCountValueCharSequence = this.__viewModel.getTeamCountValue();
            int oldTeamCountValue = Integer.parseInt(oldTeamCountValueCharSequence.toString());
            CharSequence[] newAllowedTeamCounts = scheduleType.getAllowedTeamCounts();
            int index = Arrays.asList(newAllowedTeamCounts).indexOf(oldTeamCountValueCharSequence);
            if (index < 0) {
                oldTeamCountValue--;
                String newTeamCountValue = Integer.toString(oldTeamCountValue);
                index = Arrays.asList(newAllowedTeamCounts).indexOf(newTeamCountValue);
                if (index < 0) {
                    index = 0;
                }
            }
            this._pickerTeamCount.setValues(scheduleType.getAllowedTeamCounts());
            this._pickerTeamCount.setSelectedItem(index);
            this.__viewModel.setScheduleType(scheduleType);
            this.__viewModel.setTeamCountIndex(index);
        }

        @Override
        public void onItemSelected(int index)    {
            this.__viewModel.setTeamCountIndex(index);
        }
    }

    // Constructor

    public GroupSettingsTeamRecyclerAdapter(Context context, GroupSettingsViewModel viewModel, OnStartDragListener dragStartListener) {
        this.viewModel = viewModel;
        this._dragStartListener = dragStartListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            View v = inflater.inflate(R.layout.group_settings_team_item_view, parent, false);
            return new ItemViewHolder((FrameLayout) v);
        } else if (viewType == TYPE_HEADER) {
            View v = inflater.inflate(R.layout.group_settings_header_view, parent, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder((FrameLayout) v, this.viewModel);
            return headerViewHolder;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder)viewHolder;
            final Team item = getItem(position);
            itemViewHolder._seedTextView.setText(viewModel.seed(item.seed));
            itemViewHolder._teamNameEditText.setText(item.name);
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            // Start a drag whenever the handle view it touched
            itemViewHolder._teamHandleView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        _dragStartListener.onStartDrag(itemViewHolder);
                    }
                    return false;
                }
            });
        } else if (viewHolder instanceof HeaderViewHolder) {
            //cast holder to HeaderViewHolder and set data for header.
            HeaderViewHolder headerViewHolder = (HeaderViewHolder)viewHolder;
            headerViewHolder._groupNameEditText.requestFocus();
        }
    }

    @Override
    public void onItemDismiss(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public boolean onItemMoved(int fromPosition, int toPosition) {
        viewModel.swapTeams(fromPosition - 1, toPosition - 1);
        return  true;
    }

    @Override
    public int getItemCount() {
        return viewModel._teams.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Team getItem(int position) {
        return viewModel._teams.get(position - 1);
    }
}
