package mercadolibre.examen.utils;

import mercadolibre.examen.config.Constants;

/**
 * Clase con métodos estáticos para validación.
 */
public class MutantValidationUtils {

    /**
     * Método que verifica si la matriz de ADN ingresada es válida.
     * @param dna matriz de ADN.
     * @return boolean. TRUE si es válida.
     */
    public static boolean isDnaValid(String[] dna) {

        // Validar contra Null.
        if (dna == null) {
            return false;
        }

        int numberOfRows = dna.length;

        // Validar que el tamaño de la matriz de ADN ingresada se encuentre entre el tamaño mínimo y máximo admitido.
        if (numberOfRows < Constants.SEQUENCE_LENGTH || numberOfRows > Constants.MAX_DNA_MATRIX_SIZE) {
            return false;
        }

        // Validar que todas las filas tengan la misma longitud y que sea una matriz cuadrada.
        // A su vez validar que todos los caracteres sean los admitidos por el programa.
        for (String row : dna) {

            // Validar longitud:
            if (row == null || row.length() != numberOfRows){
                return false;
            }

            // Validar caracteres:
            for (int i = 0; i < row.length(); i++){
                if (!Constants.VALID_DNA_INPUT_CHARACTERS.contains(row.charAt(i))){
                    return false;
                }
            }

        }

        return true;
    }

}
