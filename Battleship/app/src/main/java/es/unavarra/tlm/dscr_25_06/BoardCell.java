package es.unavarra.tlm.dscr_25_06;

/**
 * Representa una celda del tablero 10x10.
 * Guarda información de selección temporal, disparos y si hay un barco tuyo colocado.
 */
public class BoardCell {
    public final int rowIndex; // 0..9  -> A..J
    public final int colIndex; // 0..9  -> 1..10

    /** Selección temporal (para colocar barco) */
    public boolean selected;

    /** Disparo que TÚ hiciste a esta celda del enemigo */
    public boolean shotDone;
    /** Si ese disparo fue impactado (hit/sunk) */
    public boolean shotHit;

    /** Disparo que RECIBISTE del enemigo en esta celda (tu tablero) */
    public boolean shotReceived;
    /** Si ese disparo recibido fue impacto */
    public boolean receivedHit;

    /** Indica que en esta celda tienes un barco colocado (tu flota) */
    public boolean myShip;

    public BoardCell(int r, int c) {
        this.rowIndex = r;
        this.colIndex = c;
    }

    /** "A".."J" para el renglón */
    public String rowLetter() {
        return String.valueOf((char) ('A' + rowIndex));
    }

    /** 1..10 para la columna en términos humanos */
    public int columnHuman() {
        return colIndex + 1;
    }
}
