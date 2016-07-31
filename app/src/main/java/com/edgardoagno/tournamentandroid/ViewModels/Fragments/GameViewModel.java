package com.edgardoagno.tournamentandroid.ViewModels.Fragments;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;

/**
 * Created by edgardoagno on 31/07/16.
 */
public class GameViewModel extends BaseViewModel {

    private String _textColor = "#333333";
    private String _whiteColor = "#FFFFFF";
    private String _winningColor = "#4FB05D";
    private String _drawnColor = "#3084C8";
    private String _neutralColor = "#D3D3D3";
    private Group _group;
    private Game _game;

    public GameViewModel(Group group) {
        _group = group;
    }

    public void setGame(Game game) {
        _game = game;
    }

    public void setLeftWinner() {
        realm.beginTransaction();
        if (_game.leftTeam != null) {
            _game.winner = _game.leftTeam;
            // promote winner/loser if elimination type schedule
        }
        realm.commitTransaction();
    }

    public void setRightWinner() {
        realm.beginTransaction();
        if (_game.rightTeam != null) {
            _game.winner = _game.rightTeam;
            // promote winner/loser if elimination type schedule
        }
        realm.commitTransaction();
    }

    public void setDrawn() {
    }

    // Text values

    public String getLeftButtonText() {
        String leftText = "";
        if (_game.leftTeam != null) {
            leftText = _game.leftTeam.name;
        } else if (_game.isBye) {
            leftText = "BYE";
        }
        if (_group.getScheduleType() == ScheduleType.American && _game.doublesInfo != null && _game.doublesInfo.leftTeam != null) {
            leftText += "/";
            leftText += _game.doublesInfo.leftTeam.name;
        }
        return leftText;
    }

    public String getRightButtonText() {
        String rightText = "";
        if (_game.rightTeam != null) {
            rightText = _game.rightTeam.name;
        } else if (_game.isBye) {
            rightText = "BYE";
        }
        if (_group.getScheduleType() == ScheduleType.American && _game.doublesInfo != null && _game.doublesInfo.rightTeam != null) {
            rightText += "/";
            rightText += _game.doublesInfo.rightTeam.name;
        }
        return rightText;
    }

    public String getIndex() {
        return String.format("%1$s", _game.index);
    }

    // Colors

    public String getLeftButtonTextColor() {
        if (_game.isDraw) {
            return _whiteColor;
        } else if (_game.winner != null && _game.leftTeam != null && _game.winner.id.equals(_game.leftTeam.id)) {
            return _whiteColor;
        }
        return _textColor;
    }

    public String getRightButtonTextColor() {
        if (_game.isDraw) {
            return _whiteColor;
        } else if (_game.winner != null && _game.rightTeam != null && _game.winner.id.equals(_game.rightTeam.id)) {
            return _whiteColor;
        }
        return _textColor;
    }

    public String getLeftButtonColor() {
        if (_game.isDraw) {
            return _drawnColor;
        } else if (_game.winner != null && _game.leftTeam != null && _game.winner.id.equals(_game.leftTeam.id)) {
            return _winningColor;
        }
        return _neutralColor;
    }

    public String getRightButtonColor() {
        if (_game.isDraw) {
            return _drawnColor;
        } else if (_game.winner != null && _game.rightTeam != null && _game.winner.id.equals(_game.rightTeam.id)) {
            return _winningColor;
        }
        return _neutralColor;
    }

    // Enabled

    public Boolean isLeftButtonEnabled() {
        return !getLeftButtonText().equals("BYE");
    }

    public Boolean isRightButtonEnabled() {
        return !getRightButtonText().equals("BYE");
    }

}
