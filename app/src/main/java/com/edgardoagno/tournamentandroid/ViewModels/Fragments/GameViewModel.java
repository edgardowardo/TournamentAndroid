package com.edgardoagno.tournamentandroid.ViewModels.Fragments;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Scheduler;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;

import io.realm.RealmResults;

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

    private void parseTree(Game game) {
        RealmResults<Game> nexts = _group.games.where()
                .equalTo("elimination.leftGameIndex", game.index)
                .or().equalTo("elimination.rightGameIndex", game.index)
                .findAll();
        for (Game next: nexts) {
            Scheduler.parseTree(next, _group);
            // recursively apply this, since setting a winner may reset succeeding games.
            parseTree(next);
        }
    }

    public void setLeftWinner() {
        realm.beginTransaction();
        if (_game.leftTeam != null && _game.rightTeam != null) { // both must exist to set a winner
            _game.winner = _game.leftTeam;
            parseTree(_game);
        }
        realm.commitTransaction();

    }

    public void setRightWinner() {
        realm.beginTransaction();
        if (_game.rightTeam != null && _game.leftTeam != null) {
            _game.winner = _game.rightTeam;
            parseTree(_game);
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
        return !_game.getIsBye();
    }

    public Boolean isRightButtonEnabled() {
        return !_game.getIsBye();
    }

}
