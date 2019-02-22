package mercadolibre.examen.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO utilizado para devolver la información estadística.
 */
@Data
@NoArgsConstructor
@ToString
public class MutantStatsDTO {

    private long count_mutant_dna;
    private long count_human_dna;
    private float ratio;

    public MutantStatsDTO(long count_mutant_dna, long count_human_dna) {
        this.count_mutant_dna = count_mutant_dna;
        this.count_human_dna = count_human_dna;
        // Calcular el ratio:
        if (count_mutant_dna + count_human_dna == 0){
            this.ratio = 0.0F;
        } else {
            if (count_human_dna == 0){
                this.ratio = 1.0F * count_mutant_dna;
            } else {
                this.ratio = 1.0F * count_mutant_dna / count_human_dna;
            }
        }
    }
}
