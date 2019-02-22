package mercadolibre.examen.servicesImpl;

import mercadolibre.examen.config.Constants;
import mercadolibre.examen.domain.DnaIter;
import mercadolibre.examen.dtos.MutantStatsDTO;
import mercadolibre.examen.entities.Dna;
import mercadolibre.examen.enums.Direction;
import mercadolibre.examen.enums.Start;
import mercadolibre.examen.repositories.DnaRepository;
import mercadolibre.examen.services.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del MutantService.
 */
@Service
public class MutantServiceImpl implements MutantService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private DnaRepository dnaRepository;

    @Autowired
    public MutantServiceImpl(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    @Override
    public boolean isMutant(String[] dna) {

        // Buscar en la base de datos si ya existe un ADN igual. En matrices grandes esto ayudará en la performance.
        Dna cacheDna = this.dnaRepository.findMutantByDna(dna).orElse(null);
        if (cacheDna != null){

            log.info("DNA found in the DB");

            // Persistimos:
            Dna dnaEntity = new Dna(dna, cacheDna.isMutant());
            this.dnaRepository.save(dnaEntity);

            log.info("DNA Persisted");

            return cacheDna.isMutant();
        }

        // Si NO se encuentra un ADN igual en la base de datos, entonces realizamos las iteraciones:

        // Buscamos si es mutante o no:
        boolean value = searchInDNA_AllDireccitons(dna);

        // Persistimos:
        Dna dnaEntity = new Dna(dna, value);
        this.dnaRepository.save(dnaEntity);

        log.info("DNA Persisted");

        return value;

    }

    @Override
    public MutantStatsDTO getStats() {
        long mutantCount = this.dnaRepository.countByIsMutant(true);
        long humanCount = this.dnaRepository.countByIsMutant(false);
        return new MutantStatsDTO(mutantCount, humanCount);
    }

    @Override
    public boolean deleteAll() {
        try {
            this.dnaRepository.deleteAll();
            log.info("All DNAs in the DB were Deleted");
            return true;
        } catch (Exception e){
            log.error("Error deleting all DNAs from the DB: " + e.getMessage() + " {}", e);
            return false;
        }
    }

    private boolean searchInDNA_AllDireccitons(String[] dna){

        int count = 0;

        count += searchInDNA_EspecificDirection(dna, count, Start.LEFT, Direction.RIGHT);
        if (count >= Constants.MUTANT_SEQUENCE_COUNT) { return true; }

        count += searchInDNA_EspecificDirection(dna, count, Start.TOP, Direction.DOWN);
        if (count >= Constants.MUTANT_SEQUENCE_COUNT) { return true; }

        count += searchInDNA_EspecificDirection(dna, count, Start.TOP, Direction.DIAG_DOWN);
        if (count >= Constants.MUTANT_SEQUENCE_COUNT) { return true; }

        count += searchInDNA_EspecificDirection(dna, count, Start.LEFT, Direction.DIAG_DOWN);
        if (count >= Constants.MUTANT_SEQUENCE_COUNT) { return true; }

        count += searchInDNA_EspecificDirection(dna, count, Start.BOTTOM, Direction.DIAG_UP);
        if (count >= Constants.MUTANT_SEQUENCE_COUNT) { return true; }

        count += searchInDNA_EspecificDirection(dna, count, Start.LEFT, Direction.DIAG_UP);

        if (count >= Constants.MUTANT_SEQUENCE_COUNT) {
            return true;
        } else {
            return false;
        }

    }

    private int searchInDNA_EspecificDirection(String[] dna, int actualCount, Start start, Direction direction) {

        DnaIter dnaIter = new DnaIter(dna, start, direction);

        int sequenceCount = 0;

        do {

            int counter = 1;
            Character prevCharacter = dnaIter.getValue();
            Character actualCharacter;

            while ((actualCharacter = dnaIter.getNextValue(direction)) != null){

                if (actualCharacter.equals(prevCharacter)){
                    counter++;
                } else {
                    prevCharacter = actualCharacter;
                    counter = 1;
                }

                if (counter == Constants.SEQUENCE_LENGTH) {
                    sequenceCount++;
                    // Verificamos si es mutante y no seguimos buscando innecesariamente:
                    if (actualCount + sequenceCount >= 2){
                        return sequenceCount;
                    }
                    // Continuamos con la búsqueda pero reiniciamos el contador:
                    counter = 1;
                }

            }

        } while (dnaIter.nextStartLine(start));

        return sequenceCount;
    }

}
