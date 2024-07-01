package ChurchManagementSystem.CMS.modules.financial.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_outcome")
public class OutcomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_outcome")
    private long idOutcome;

    @Column(name = "outcome_deposit")
    @Nullable
    private BigDecimal outcomeDeposit;

    @Column(name = "outcome_building")
    @Nullable
    private BigDecimal outcomeBuilding;

    @Column(name = "outcome_diakonia")
    @Nullable
    private BigDecimal outcomeDiakonia;

    @Column(name = "outcome_guest")
    @Nullable
    private BigDecimal outcomeGuest;

    @Column(name = "outcome_operational")
    @Nullable
    private BigDecimal outcomeOperational;

    @Column(name = "outcome_event")
    @Nullable
    private BigDecimal outcomeEvent;

    @Column(name = "outcome_other")
    @Nullable
    private BigDecimal outcomeOther;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at" )
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "financial_entity_id_financial", foreignKey = @ForeignKey(name = "fk_outcome_financial"))
    @JsonBackReference
    private FinancialEntity financialEntity;
}
