package com.github.syr0ws.bingo.plugin.tool;

import com.github.syr0ws.bingo.plugin.util.TextUtil;

public enum Text {

    BINGO_INVENTORY_TITLE("&6Bingo"),
    GAME_STARTING_IN("&eDébut dans &6%d&e."),
    GAME_STARTED("&6Début de la partie !"),
    ITEM_FOUND("&6%s &ea trouvé un item !"),
    ROW_COMPLETE("&6%s &ea complété une ligne !"),
    COLUMN_COMPLETE("&6%s &ea complété une colonne !"),
    DIAGONAL_COMPLETE("&6%s &ea complété une diagonale !"),
    PLAYER_BINGO_COMPLETE("&6%s &ea complété son bingo en entier !"),
    PLAYER_WIN("&6%s &eremporte la partie !"),
    GAME_TIME_LEFT_MINUTE("&eIl reste &6%d minute%s &epour compléter votre bingo."),
    GAME_TIME_LEFT_SECOND("&eIl reste &6%d seconde%s &epour compléter votre bingo."),
    RESTART("&cLe serveur redémarre. Veuillez vous reconnecter."),
    GAME_FINISHED("&cLa partie est terminée !");

    private final String text;

    Text(String text) {
        this.text = text;
    }

    public String get() {
        return TextUtil.parseColors(this.text);
    }
}
