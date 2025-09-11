package ChurchManagementSystem.CMS.modules.financial.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@Table(name = "tb_outcome")
public class OutcomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_outcome")
    private long idOutcome;

    @Column(name = "outcome_date") //yyyy-MM-dd
    private Date outcomeDate;

    @Column(name = "deskripsi", columnDefinition = "text")
    @Nullable
    private String deskripsi;

    @Column(name = "nama")
    private String nama;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at" )
    private LocalDateTime updateAt;

    @Column(name = "deposit")
    private BigDecimal deposit = BigDecimal.ZERO;

    @Column(name = "pembangunan")
    private BigDecimal pembangunan = BigDecimal.ZERO;

    @Column(name = "diakonia")
    private BigDecimal diakonia = BigDecimal.ZERO;

    @Column(name = "pelayanan")
    private BigDecimal pelayanan = BigDecimal.ZERO;

    @Column(name = "operasional")
    private BigDecimal operasional = BigDecimal.ZERO;

    @Column(name = "acara")
    private BigDecimal acara = BigDecimal.ZERO;

    @Column(name = "lainnya")
    private BigDecimal lainnya = BigDecimal.ZERO;

    @Column(name = "total_outcome")
    private BigDecimal totalOutcome =BigDecimal.ZERO;

    @PrePersist
    @PreUpdate
    public void calculateTotalIncome() {
        this.totalOutcome = BigDecimal.ZERO;
        if (deposit != null) totalOutcome = totalOutcome.add(deposit);
        if (pembangunan != null) totalOutcome = totalOutcome.add(pembangunan);
        if (diakonia != null) totalOutcome = totalOutcome.add(diakonia);
        if (pelayanan != null) totalOutcome = totalOutcome.add(pelayanan);
        if (operasional != null) totalOutcome = totalOutcome.add(operasional);
        if (acara != null) totalOutcome = totalOutcome.add(operasional);
        if (lainnya != null)totalOutcome = totalOutcome.add(lainnya);
    }
}
