package mercadolibre.examen.services;

import mercadolibre.examen.dtos.MutantStatsDTO;

/**
 * Interfaz del MutantService.
 */
public interface MutantService {

    boolean isMutant(String[] dna);

    MutantStatsDTO getStats();

    boolean deleteAll();

}
