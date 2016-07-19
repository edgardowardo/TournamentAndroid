package com.edgardoagno.tournamentandroid;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.edgardoagno.tournamentandroid.ItemTouchHelperAdapter.ItemTouchHelperAdapter;
import com.edgardoagno.tournamentandroid.ItemTouchHelperAdapter.ItemTouchHelperViewHolder;
import com.edgardoagno.tournamentandroid.ItemTouchHelperAdapter.OnStartDragListener;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.ViewModels.GroupSettingsTeamItemViewModel;
import com.edgardoagno.tournamentandroid.ViewModels.GroupSettingsViewModel;
import com.wefika.horizontalpicker.HorizontalPicker;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import rx.Subscription;
import rx.functions.Action1;

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
        @Bind(R.id.handicap_edit_text) EditText _handicapEditText;
        @Bind(R.id.team_handle_view) ImageView _teamHandleView;
        private GroupSettingsTeamItemViewModel _viewModel;
        private Subscription _isManualSortingSubscription;
        private Subscription _isEditingHandicapSubscription;

        public ItemViewHolder(final FrameLayout container, GroupSettingsTeamItemViewModel teamViewModel) {
            super(container);
            ButterKnife.bind(this, container);
            _viewModel = teamViewModel;

            _isManualSortingSubscription = viewModel
                    ._isManualSortingEmitterSubject
                    .asObservable()
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean value) {
                            int visibility = (value) ? View.VISIBLE : View.GONE;
                            _teamHandleView.setVisibility(visibility);
                        }
                    });

            _isEditingHandicapSubscription = viewModel
                    ._isEditingHandicapEmitterSubject
                    .asObservable()
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean value) {
                            int visibility = (value) ? View.VISIBLE : View.GONE;
                            _handicapEditText.setVisibility(visibility);
                        }
                    });
        }

        @OnTouch(R.id.team_handle_view)
        public boolean onTeamHandleTouch(View v, MotionEvent event) {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                _dragStartListener.onStartDrag(this);
            }
            return false;
        }

        @OnTextChanged(R.id.team_name_edit_text)
        public void onTeamNameChanged() {
            String name = _teamNameEditText.getText().toString();
            _viewModel.setName(name);
        }

        @OnFocusChange(R.id.handicap_edit_text)
        public void onClickHandicap(boolean hasFocus) {
            if (hasFocus) {
                _handicapEditText.setText("");
            }
        }

        @OnTextChanged(R.id.handicap_edit_text)
        public void onHandicapChanged() {
            String handicap = _handicapEditText.getText().toString();
            _viewModel.setHandicap(handicap);
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

        @Bind(R.id.shuffle_button) Button _shuffleButton;
        @Bind(R.id.sort_toggle) ToggleButton _sortToggleButton;
        @Bind(R.id.reset_button) Button _resetButton;
        @Bind(R.id.import_button) Button _importButton;
        @Bind(R.id.handicap_toggle) ToggleButton _handicapToggleButton;

        // Constructor

        public HeaderViewHolder(FrameLayout container) {
            super(container);
            ButterKnife.bind(this, container);
        }

        @OnTextChanged(R.id.group_edit_text)
        public void onGroupNameChanged() {
            String name = _groupNameEditText.getText().toString();
            viewModel.setGroupName(name);
        }

        @OnClick(R.id.shuffle_button)
        public void onClickShuffle() {
            viewModel.shuffleTeams();
        }

        @OnClick(R.id.sort_toggle)
        public void onToggleSort() {
            Boolean isChecked = _sortToggleButton.isChecked();
            viewModel.setIsManualSorting(isChecked);
        }

        @OnClick(R.id.reset_button)
        public void onClickReset() {
            viewModel.resetTeams();
        }

        @OnClick(R.id.import_button)
        public void onClickImport() {
            // TODO: under construction
        }

        @OnClick(R.id.handicap_toggle)
        public void onToggleHandicap() {
            Boolean isChecked = _handicapToggleButton.isChecked();
            viewModel.setIsEditingHandicap(isChecked);
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
            CharSequence oldTeamCountValueCharSequence = viewModel.getTeamCountValue();
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
            viewModel.setScheduleType(scheduleType);
            viewModel.setTeamCountIndex(index);
        }

        @Override
        public void onItemSelected(int index)    {
            viewModel.setTeamCountIndex(index);
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
            GroupSettingsTeamItemViewModel teamItemViewModel = new GroupSettingsTeamItemViewModel();
            ItemViewHolder holder = new ItemViewHolder((FrameLayout) v, teamItemViewModel);
            return holder;
        } else if (viewType == TYPE_HEADER) {
            View v = inflater.inflate(R.layout.group_settings_header_view, parent, false);
            HeaderViewHolder holder = new HeaderViewHolder((FrameLayout) v);
            return holder;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder)viewHolder;
            final Team item = getItem(position);
            itemViewHolder._viewModel.setTeam(item);
            itemViewHolder._seedTextView.setText(viewModel.seed(item.seed));
            itemViewHolder._teamNameEditText.setText(item.name);
            itemViewHolder._handicapEditText.setText(itemViewHolder._viewModel.getHandicap());
            itemViewHolder._teamHandleView.setVisibility((viewModel.getIsManualSorting()) ? View.VISIBLE : View.GONE);
            itemViewHolder._handicapEditText.setVisibility((viewModel.getIsEditingHandicap()) ? View.VISIBLE : View.GONE);
        } else if (viewHolder instanceof HeaderViewHolder) {
            //cast holder to HeaderViewHolder and set data for header.
            HeaderViewHolder holder = (HeaderViewHolder)viewHolder;
            holder._groupNameEditText.requestFocus();

            holder._pickerTeamCount.setOnItemSelectedListener(holder);
            CharSequence[] s = viewModel.getAllowedTeamCounts();
            holder._pickerTeamCount.setValues(s);

            CharSequence[] allowedTeamCounts = viewModel._group.getScheduleType().getAllowedTeamCounts();
            String teamCount = Integer.toString(viewModel._group.teamCount);
            int index = Arrays.asList(allowedTeamCounts).indexOf(teamCount);
            holder._pickerTeamCount.setSelectedItem(index);
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
