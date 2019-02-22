package mercadolibre.examen.controllers;

import mercadolibre.examen.dtos.InputDnaDTO;
import mercadolibre.examen.dtos.MutantStatsDTO;
import mercadolibre.examen.services.MutantService;
import mercadolibre.examen.utils.MutantValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller de los servicios de mutante.
 */
@RestController
@CrossOrigin
public class MutantController {

    private final Logger log = LoggerFactory.getLogger(MutantController.class);

    private MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    /**
     * Servicio que determina si el ADN ingresado es mutante o humano.
     *
     * @param inputDnaDTO JSON enviado en el body del request, con el formato:
     *        {
     *          "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
     *        }
     * @return Http Status Code 200-OK para ADN mutante, y 403-FORBIDDEN en caso contrario
     */
    @PostMapping("/mutant/")
    public ResponseEntity isMutant(@RequestBody InputDnaDTO inputDnaDTO){
        try {

            // Si la entrada NO es válida, loggeamos y devolvemos false.
            if (!MutantValidationUtils.isDnaValid(inputDnaDTO.getDna())){
                log.debug("Invalid DNA input in checking for mutant DNA: {}", inputDnaDTO);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            // Verificamos si es o no mutante:
            if (this.mutantService.isMutant(inputDnaDTO.getDna())) {
                log.debug("Mutant DNA Found");
                return ResponseEntity.status(HttpStatus.OK).body(null);
            } else {
                log.debug("Human DNA Found");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

        } catch (Exception e){
            // Si se produce una exception, logeamos y devolvemos false.
            log.error("Error in checking for mutant DNA: " + e.getMessage() + " {}", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    /**
     * Servicio que devuelve estadísticas de ADNs consultados al servicio "/mutant/".
     *
     * @return Http Status Code 200-OK y un JSON con el formato:
     *        {
     *           "count_mutant_dna": 40,
     *           "count_human_dna": 100,
     *           "ratio": 0.4
     *        }
     *        En caso de producirse una excepción se devuelve unicamente un Http Status Code 500-INTERNAL_SERVER_ERROR
     */
    @GetMapping("/stats")
    public ResponseEntity<MutantStatsDTO> getStats(){
        try {

            log.debug("Stats requested");
            return new ResponseEntity<>(this.mutantService.getStats(), HttpStatus.OK);

        } catch (Exception e){
            // Si se produce una exception, logeamos y devolvemos un INTERNAL_SERVER_ERROR.
            log.error("Error in getting DNA stats: " + e.getMessage() + " {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Servicios Auxiliares Agregados: ---------------------------------------------------------------------------------

    /**
     * Servicio auxiliar que elimina todos los elementos en la base de datos, pensado para facilitar las pruebas en el
     * servidor de producción.
     *
     * @return Http Status Code 200-OK si fue exitoso el proceso, y 403-FORBIDDEN en caso contrario
     */
    @DeleteMapping("/deleteall")
    public ResponseEntity deleteAll(){
        boolean response = this.mutantService.deleteAll();
        if (response){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    /**
     * Servicio auxiliar que devuelve un token necesario para que loader.io pueda verificar nuestro host como target
     * válido.
     *
     * @return String TOKEN = loaderio-1a0711ce40549cb02c4e2e071b5c1a90
     */
    @GetMapping("/loaderio-1a0711ce40549cb02c4e2e071b5c1a90/")
    public String getLoaderioVerifyToken(){
        return "loaderio-1a0711ce40549cb02c4e2e071b5c1a90";
    }

}
