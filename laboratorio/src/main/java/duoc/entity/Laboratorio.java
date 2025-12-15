package duoc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "laboratorios")
public class Laboratorio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "laboratorios_id_seq")
    @SequenceGenerator(name = "laboratorios_id_seq", sequenceName = "SEQ_LABORATORIOS", allocationSize = 1)
    private Long id;
    private String nombre;
    private String telefono;

    @Column(name = "web_url")
    private String webUrl;

    private String email;

    @ManyToOne(targetEntity = Convenio.class)
    @JoinColumn(name = "convenios_id")
    private Convenio convenio;
}
