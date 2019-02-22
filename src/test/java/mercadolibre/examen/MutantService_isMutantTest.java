package mercadolibre.examen;

import mercadolibre.examen.entities.Dna;
import mercadolibre.examen.repositories.DnaRepository;
import mercadolibre.examen.servicesImpl.MutantServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Para estos tests se hace un mock del repositorio de ADN, por consiguiente el resultado no se persiste en la base de
 * datos.
 *
 * Y además controlamos la respuesta de las consultas al mismo.
 */
public class MutantService_isMutantTest {

    private static final String[] validDnaHuman = {"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
    private static final String[] validDnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

    private static final String[] invalidDnaSecuence = null;

    static DnaRepository dnaRepository;
    static MutantServiceImpl mutantService;

    @Before
    public void setUp(){
        dnaRepository = Mockito.mock(DnaRepository.class);
        mutantService  = new MutantServiceImpl(dnaRepository);
    }

    @Test
    public void isMutantTest(){

        // Primero pruebo la funcionalidad anulando la consulta al repositorio de ADN.
        // Esto equivale a que cacheDna siempre sea null.

        assertFalse(mutantService.isMutant(validDnaHuman));
        assertTrue(mutantService.isMutant(validDnaMutant));

    }

    @Test
    public void isMutantTest_WithQueryResultMocks(){

        // Ahora creo mocks para las consultas al repositorio y pruebo incluyendo esa parte de la funcionalidad.

        // El siguiente caso no debería existir en el sistema, pero nos sirve para comprobar que se está ejecutanto la
        // porción de código antes anulada (Caso: un ADN mutante que haya dado un falso resultado y se haya persistido).

        Optional<Dna> mockDna = Optional.ofNullable(new Dna(validDnaMutant, false));
        Mockito.when(dnaRepository.findMutantByDna(validDnaMutant)).thenReturn(mockDna);

        assertFalse(mutantService.isMutant(validDnaMutant));

        // Verificar si se esta utilizando el mock del método.
        Mockito.verify(dnaRepository).findMutantByDna(validDnaMutant);

        // -------------------------------------------------------------------------------------------------------------

        // El siguiente caso si es posible, cuando no existe ningún elemento en la base de datos con ese ADN.

        Optional<Dna> mockDnaNull = Optional.ofNullable(null);
        Mockito.when(dnaRepository.findMutantByDna(validDnaHuman)).thenReturn(mockDnaNull);

        assertFalse(mutantService.isMutant(validDnaHuman));

        // Verificar si se esta utilizando el mock del método.
        Mockito.verify(dnaRepository).findMutantByDna(validDnaHuman);

    }

    @Test(expected = NullPointerException.class)
    public void isMutantTest_InvalidDnaSecuence(){

        mutantService.isMutant(invalidDnaSecuence);

    }

}
