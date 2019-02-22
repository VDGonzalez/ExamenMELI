package mercadolibre.examen;

import mercadolibre.examen.dtos.MutantStatsDTO;
import mercadolibre.examen.repositories.DnaRepository;
import mercadolibre.examen.servicesImpl.MutantServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test parametrizado.
 *
 * Para estos tests se hace un mock del repositorio de ADN, por consiguiente el resultado no se persiste en la base de
 * datos.
 *
 * Y además controlamos la respuesta de las consultas al mismo.
 */
@RunWith(value = Parameterized.class)
public class MutantService_getStatsTest {

    static DnaRepository dnaRepository;
    static MutantServiceImpl mutantService;

    @Parameterized.Parameters
    public static Iterable<Object[]> getData(){
        return Arrays.asList(new Object[][]{
                {40L, 100L, 0.4F},
                {100L, 40L, 2.5F},
                {0L, 40L, 0.0F},
                {40L, 0L, 40.0F},
                {0L, 0L, 0.0F},
                {40L, 40L, 1.0F}
        });
    }

    private long count_mutant_dna;
    private long count_human_dna;
    private float ratio;

    public MutantService_getStatsTest(long count_mutant_dna, long count_human_dna, float ratio){
        this.count_mutant_dna = count_mutant_dna;
        this.count_human_dna = count_human_dna;
        this.ratio = ratio;
    }

    @Before
    public void setUp(){
        dnaRepository = Mockito.mock(DnaRepository.class);
        mutantService  = new MutantServiceImpl(dnaRepository);
    }

    /**
     * Test parametrizado.
     */
    @Test
    public void getStatsTest(){

        Mockito.when(dnaRepository.countByIsMutant(true)).thenReturn(count_mutant_dna);
        Mockito.when(dnaRepository.countByIsMutant(false)).thenReturn(count_human_dna);

        MutantStatsDTO dto = mutantService.getStats();

        assertEquals(count_mutant_dna, dto.getCount_mutant_dna());
        assertEquals(count_human_dna, dto.getCount_human_dna());
        assertEquals(ratio, dto.getRatio(), 0.0);

        // Verificar si se están utilizando los mocks de los métodos.
        Mockito.verify(dnaRepository).countByIsMutant(true);
        Mockito.verify(dnaRepository).countByIsMutant(false);

    }

}
