package mercadolibre.examen.entities;

import lombok.Getter;
import lombok.Setter;
import mercadolibre.examen.config.Constants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase Entidad Dna, utilizada para persistir en la BD los ADNs procesados por el servicio isMutant.
 */
@Entity
@Getter
@Setter
@Table(name = "dnas")
public class Dna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = Constants.DB_MAX_MATRIX_SIZE)
    private String[] dna;

    private boolean isMutant;

    public Dna(String[] dna, boolean isMutant) {
        this.dna = dna;
        this.isMutant = isMutant;
    }

    public Dna() {
    }

    // Datos de auditoría:
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @CreationTimestamp
//    private LocalDateTime createDate;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @UpdateTimestamp
//    private LocalDateTime updateDate;

    @Transient
    private final Logger log = LoggerFactory.getLogger(this.getClass());

}
