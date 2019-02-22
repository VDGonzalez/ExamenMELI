package mercadolibre.examen.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase final que contiene las costantes de configuración del programa.
 */
public final class Constants {

    /**
     * Tamaño máximo para la matriz de ADN ingresada.
     */
    public static final int MAX_DNA_MATRIX_SIZE = 100;

    /**
     * Tamaño máximo de caracteres para la matriz de ADN almacenada en la base de datos.
     *
     * Se configura como el tamaño de un elemento blob en la tabla, si se cambia el MAX_DNA_MATRIX_SIZE se debe
     * tener en cuenta cambiar este tamaño de manera proporcional.
     *
     * Se calculó teniendo en cuenta una matriz de 100x100 elementos = 10.000 caracteres.
     */
    public static final int DB_MAX_MATRIX_SIZE = 10000;

    /**
     * Largo de la cadena de caracteres iguales necesaria para considerarse una secuencia mutante.
     * A su vez es el tamaño mínimo para la matriz de ADN ingresada.
     */
    public static final int SEQUENCE_LENGTH = 4;

    /**
     * Número de secuencias mutantes necesario para considerar el ADN completo como mutante.
     */
    public static final int MUTANT_SEQUENCE_COUNT = 2;

    /**
     * Set de caracteres genéticos de entrada válidos para el programa.
     */
    public static final Set<Character> VALID_DNA_INPUT_CHARACTERS = new HashSet<>(Arrays.asList('A','T','C','G'));

    public Constants(){}

}
