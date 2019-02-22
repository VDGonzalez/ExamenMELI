package mercadolibre.examen;

import mercadolibre.examen.domain.DnaIter;
import mercadolibre.examen.enums.Direction;
import mercadolibre.examen.enums.Start;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DnaIterTest {

    private static final String[] dnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

    private static List<DnaIter> dnaIterList;

    @Before
    public void setUp(){
        dnaIterList = new ArrayList<>();

        dnaIterList.add(new DnaIter(dnaMutant, Start.LEFT, Direction.RIGHT));
        dnaIterList.add(new DnaIter(dnaMutant, Start.TOP, Direction.DOWN));
        dnaIterList.add(new DnaIter(dnaMutant, Start.LEFT, Direction.DIAG_DOWN));
        dnaIterList.add(new DnaIter(dnaMutant, Start.LEFT, Direction.DIAG_UP));

        dnaIterList.add(new DnaIter(dnaMutant, Start.TOP, Direction.DIAG_DOWN));    // Sin diagonal principal
        dnaIterList.add(new DnaIter(dnaMutant, Start.BOTTOM, Direction.DIAG_UP));    // Sin diagonal principal
    }

    @Test
    public void getValueTest(){
        assertEquals(new Character(dnaMutant[0].charAt(0)), dnaIterList.get(0).getValue());
        assertEquals(new Character(dnaMutant[0].charAt(0)), dnaIterList.get(1).getValue());
        assertEquals(new Character(dnaMutant[0].charAt(0)), dnaIterList.get(2).getValue());
        assertEquals(new Character(dnaMutant[0].charAt(0)), dnaIterList.get(3).getValue());
        assertEquals(new Character(dnaMutant[0].charAt(1)), dnaIterList.get(4).getValue());
        assertEquals(new Character(dnaMutant[0].charAt(1)), dnaIterList.get(5).getValue());
    }

    @Test
    public void getNextTest(){
        assertEquals(new Character(dnaMutant[0].charAt(1)), dnaIterList.get(0).getNextValue(Direction.RIGHT));
        assertEquals(new Character(dnaMutant[1].charAt(0)), dnaIterList.get(1).getNextValue(Direction.DOWN));
        assertEquals(new Character(dnaMutant[1].charAt(1)), dnaIterList.get(2).getNextValue(Direction.DIAG_DOWN));
        assertEquals(null, dnaIterList.get(3).getNextValue(Direction.DIAG_UP));
        assertEquals(new Character(dnaMutant[1].charAt(2)), dnaIterList.get(4).getNextValue(Direction.DIAG_DOWN));
        assertEquals(null, dnaIterList.get(5).getNextValue(Direction.DIAG_UP));
    }

    @Test
    public void nextStartLineTest(){

        // True: -------------------------------------------------------------------------------------------------------
        for (int i = 0; i < 5; i++){
            assertTrue(dnaIterList.get(0).nextStartLine(Start.LEFT));
            assertTrue(dnaIterList.get(1).nextStartLine(Start.TOP));
            assertTrue(dnaIterList.get(2).nextStartLine(Start.LEFT));
            assertTrue(dnaIterList.get(3).nextStartLine(Start.LEFT));

            if (i < 4) {
                assertTrue(dnaIterList.get(4).nextStartLine(Start.TOP));
                assertTrue(dnaIterList.get(5).nextStartLine(Start.BOTTOM));
            }
        }

        // False: ------------------------------------------------------------------------------------------------------
        assertFalse(dnaIterList.get(0).nextStartLine(Start.LEFT));
        assertFalse(dnaIterList.get(1).nextStartLine(Start.TOP));
        assertFalse(dnaIterList.get(2).nextStartLine(Start.LEFT));
        assertFalse(dnaIterList.get(3).nextStartLine(Start.LEFT));
        assertFalse(dnaIterList.get(4).nextStartLine(Start.TOP));
        assertFalse(dnaIterList.get(5).nextStartLine(Start.BOTTOM));

    }

}
