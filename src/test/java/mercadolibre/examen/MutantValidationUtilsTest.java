package mercadolibre.examen;

import mercadolibre.examen.config.Constants;
import mercadolibre.examen.utils.MutantValidationUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class MutantValidationUtilsTest {

    private static final String[] validDnaHuman = {"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
    private static final String[] validDnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
    private static final String[] validDnaSecuente_MinLength = generateValidMatrix(Constants.SEQUENCE_LENGTH);
    private static final String[] validDnaSecuente_MaxLength = generateValidMatrix(Constants.MAX_DNA_MATRIX_SIZE);

    private static final String[] invalidDnaSecuence_MxN_6x5 = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA"};
    private static final String[] invalidDnaSecuence_MxN_5x6 = {"ATGCG","CAGTG","TTATG","AGAAG","CCCCT","TCACT"};
    private static final String[] invalidDnaSecuence_null = null;
    private static final String[] invalidDnaSecuence_RowNull = {"ATGC","CAGT",null,"AGAA"};
    private static final String[] invalidDnaSecuence_RowsNotEqualLength = {"ATGC","CAGT","TTA","AGAA"};
    private static final String[] invalidDnaSecuente_MinLength = generateValidMatrix(Constants.SEQUENCE_LENGTH - 1);
    private static final String[] invalidDnaSecuente_Empty = {};
    private static final String[] invalidDnaSecuente_MaxLength = generateValidMatrix(Constants.MAX_DNA_MATRIX_SIZE + 1);
    private static final String[] invalidDnaSecuente_Characters = {"ATGD","CAGT","TTAT","AGAA"};

    // Método que genera una matris válida simple para validaciones de Length.
    public static String[] generateValidMatrix(int n){
        String[] matrix = new String[n];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (j == 0){
                    matrix[i] = "A";
                } else {
                    matrix[i] = matrix[i] + "A";
                }
            }
        }

        return matrix;
    }

    @Test
    public void isDnaValidTest(){

        // True: -------------------------------------------------------------------------------------------------------

        assertTrue(MutantValidationUtils.isDnaValid(validDnaHuman));
        assertTrue(MutantValidationUtils.isDnaValid(validDnaMutant));
        assertTrue(MutantValidationUtils.isDnaValid(validDnaSecuente_MinLength));
        assertTrue(MutantValidationUtils.isDnaValid(validDnaSecuente_MaxLength));

        // False: ------------------------------------------------------------------------------------------------------

        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuence_MxN_6x5));
        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuence_MxN_5x6));
        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuence_null));
        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuence_RowNull));
        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuence_RowsNotEqualLength));
        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuente_MinLength));
        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuente_Empty));
        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuente_MaxLength));
        assertFalse(MutantValidationUtils.isDnaValid(invalidDnaSecuente_Characters));

    }

}
