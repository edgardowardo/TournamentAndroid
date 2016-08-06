package com.edgardoagno.tournamentandroid.ViewModels.Fragments;

/**
 * Created by edgardoagno on 06/08/16.
 */
public class GameFooterViewModel {

    private String _textColor = "#333333";
    private String _neutralColor = "#D3D3D3";
    private boolean isLosersRound = false;

    public GameFooterViewModel(boolean isLosersRound) {
        this.isLosersRound = isLosersRound;
    }

    public String getFooterTextColor() {
        if (isLosersRound) {
            return _neutralColor;
        } else {
            return _textColor;
        }
    }
}
