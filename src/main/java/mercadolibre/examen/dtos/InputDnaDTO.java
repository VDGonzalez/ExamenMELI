package mercadolibre.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO utilizado para mappear el Json recibido en el HTTP POST.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InputDnaDTO {
    private String[] dna;
}
