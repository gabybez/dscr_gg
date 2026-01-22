// Position.java
package es.unavarra.tlm.dscr_25_06;

/** Position { row: 'A'..'J', column: 1..10 } */
public class Position {
    public String row;   // 'A'..'J'
    public int column;   // 1..10

    public Position() {}
    public Position(String row, int column) {
        this.row = row;
        this.column = column;
    }
}
