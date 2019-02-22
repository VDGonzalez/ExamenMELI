package mercadolibre.examen.repositories;

import mercadolibre.examen.entities.Dna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de ADN.
 */
@Repository
public interface DnaRepository extends JpaRepository<Dna, Long> {

    Long countByIsMutant(boolean value);

    /**
     * Busca si existe un registro con el mismo ADN ingresado.
     * @param dna Matriz de ADN a buscar.
     * @return Optional<Dna> Registro de ADN encontrado.
     */
    @Query(value = "SELECT * FROM dnas d " +
            "WHERE d.dna = :dna " +
            "LIMIT 1", nativeQuery = true)
    Optional<Dna> findMutantByDna(@Param("dna") String[] dna);

}
