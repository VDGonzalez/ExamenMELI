package mercadolibre.examen;

import mercadolibre.examen.controllers.MutantController;
import mercadolibre.examen.dtos.InputDnaDTO;
import mercadolibre.examen.dtos.MutantStatsDTO;
import mercadolibre.examen.repositories.DnaRepository;
import mercadolibre.examen.servicesImpl.MutantServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;

public class MutantControllerTest {

    static MutantController mutantController;
    static DnaRepository dnaRepository;
    static MutantServiceImpl mutantService;

    private static final String[] validDnaMutant = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
    private static final String[] validDnaHuman = {"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
    private static final String[] invalidDna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACT"};

    private static final InputDnaDTO mutantDnaDTO = new InputDnaDTO(validDnaMutant);
    private static final InputDnaDTO humanDnaDTO = new InputDnaDTO(validDnaHuman);
    private static final InputDnaDTO invalidDnaDTO = new InputDnaDTO(invalidDna);

    @Before
    public void setUp(){
        dnaRepository = Mockito.mock(DnaRepository.class);
        mutantService  = new MutantServiceImpl(dnaRepository);
        mutantController = new MutantController(mutantService);
    }

    @Test
    public void isMutantTest(){

        Mockito.when(mutantService.isMutant(validDnaMutant)).thenReturn(true);
        Mockito.when(mutantService.isMutant(validDnaHuman)).thenReturn(false);

        assertEquals(HttpStatus.OK, mutantController.isMutant(mutantDnaDTO).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, mutantController.isMutant(humanDnaDTO).getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, mutantController.isMutant(invalidDnaDTO).getStatusCode());

        assertNull(mutantController.isMutant(mutantDnaDTO).getBody());
        assertNull(mutantController.isMutant(humanDnaDTO).getBody());
        assertNull(mutantController.isMutant(invalidDnaDTO).getBody());

    }

        @Test
    public void getStatsTest(){

        MutantStatsDTO dto = new MutantStatsDTO(40L, 100L);

        Mockito.when(dnaRepository.countByIsMutant(true)).thenReturn(40L);
        Mockito.when(dnaRepository.countByIsMutant(false)).thenReturn(100L);

        assertEquals(HttpStatus.OK, mutantController.getStats().getStatusCode());
        assertEquals(dto, mutantController.getStats().getBody());

    }


}
