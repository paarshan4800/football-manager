package com.models;

import com.models.Player;
import com.models.Team;

import java.math.BigInteger;

public class PlayerExchangeTransfer extends PermanentTransfer {
    private Player exchangePlayer;
    private Team exchangePlayerTeam;

    public PlayerExchangeTransfer(Player player, Team fromTeam, Team toTeam, int status, double transferFee, Player exchangePlayer, Team exchangePlayerTeam) {
        super(player, fromTeam, toTeam, status, transferFee);
        this.exchangePlayer = exchangePlayer;
        this.exchangePlayerTeam = exchangePlayerTeam;
    }

    public PlayerExchangeTransfer(Player player, Team fromTeam, Team toTeam, BigInteger transferID, int status) {
        super(player, fromTeam, toTeam, transferID, status);
    }

    public PlayerExchangeTransfer(Player player, Team fromTeam, Team toTeam, BigInteger transferID, int status, double transferFee, Player exchangePlayer, Team exchangePlayerTeam) {
        super(player, fromTeam, toTeam, transferID, status, transferFee);
        this.exchangePlayer = exchangePlayer;
        this.exchangePlayerTeam = exchangePlayerTeam;
    }

    public Player getExchangePlayer() {
        return exchangePlayer;
    }

    public void setExchangePlayer(Player exchangePlayer) {
        this.exchangePlayer = exchangePlayer;
    }

    public Team getExchangePlayerTeam() {
        return exchangePlayerTeam;
    }

    public void setExchangePlayerTeam(Team exchangePlayerTeam) {
        this.exchangePlayerTeam = exchangePlayerTeam;
    }
}

