package mercadolibre.examen.domain;

import mercadolibre.examen.enums.Direction;
import mercadolibre.examen.enums.Start;

/**
 * Clase iterador de ADN.
 * Se utiliza para iterar sobre los valores de la matriz de ADN en las distintas direcciones de búsqueda.
 */
public class DnaIter {

    private String[] dna;
    Start start;
    Direction direction;
    private int n;
    private int row;
    private int column;
    private int rowStart;
    private int columnStart;

    public DnaIter(String[] dna, Start start, Direction direction){

        // Inicializar valores:

        this.dna = dna;
        this.n = dna.length;
        this.row = 0;
        this.column = 0;
        this.start = start;
        this.direction = direction;
        this.rowStart = 0;
        this.columnStart = 0;

        /*

            Para los casos de búsquedas en diagonal:

        Se optó por dividir en dos la matriz a través de la diagonal descendente o ascendente respectivamente al caso.
        Y realizar búsquedas en diagonal en cada una de esas divisiones de la matriz.

        Al hacer esto, hay que evitar que la línea diagonal se recorra dos veces en las dos búsquedas de la misma
        dirección diagonal. O esto puede llevar a resultados erroneos.

        Para evitar esa doble iteración sobre la diagonal se realiza la siguiente inicialización.

        */

        if (start == Start.BOTTOM && direction == Direction.DIAG_UP){
            this.column = 1;
            this.columnStart = 1;
        }

        if (start == Start.TOP && direction == Direction.DIAG_DOWN){
            this.column = 1;
            this.columnStart = 1;
        }

    }

    /**
     * Método que verifica si existe en la matriz iterada una próxima línea de búsqueda,
     * y se posiciona en ella.
     */
    public boolean nextStartLine(Start start){
        if (start == Start.TOP || start == Start.BOTTOM) {

            if (start == Start.TOP){
                row = 0;
            } else {
                row = n - 1;
            }

            columnStart++;
            column = columnStart;

            if (columnStart<n){
                return true;
            } else {
                return false;
            }

        }

        column = 0;
        rowStart++;
        row = rowStart;

        if (rowStart < n) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Método que devuelve el caracter correspondiente a la posición actual del iterador.
     * Devuelve null en caso de que se encuentre fuera de los límites de la matriz.
     */
    public Character getValue() {
        if (row < 0 || row > n || column < 0 || column > n) {
            return null;
        }
        return dna[row].charAt(column);
    }

    /**
     * Método que devuelve el siguiente caracter según la dirección, si no existe devuelve null.
     */
    public Character getNextValue(Direction direction) {
        switch (direction){

            case RIGHT:
                if (column < n - 1){
                    column++;
                    return getValue();
                }
                break;

            case DOWN:
                if (row < n -1){
                    row++;
                    return getValue();
                }
                break;

            case DIAG_DOWN:
                if (row < n -1 && column < n -1){
                    row++;
                    column++;
                    return getValue();
                }
                break;

            case DIAG_UP:
                if (row > 0 && column < n - 1) {
                    row--;
                    column++;
                    return getValue();
                }
                break;
        }

        return null;
    }

}
