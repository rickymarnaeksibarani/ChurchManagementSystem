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
import java.util.Date;

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

    @Column(name = "outcome_date") //yyyy-MM-dd
    private Date outcomeDate;

    @Column(name = "outcome_deposit")
    @Nullable
    private BigDecimal outcomeDeposit = BigDecimal.ZERO;

    @Column(name = "outcome_building")
    @Nullable
    private BigDecimal outcomeBuilding = BigDecimal.ZERO;

    @Column(name = "outcome_diakonia")
    @Nullable
    private BigDecimal outcomeDiakonia = BigDecimal.ZERO;

    @Column(name = "outcome_guest")
    @Nullable
    private BigDecimal outcomeGuest = BigDecimal.ZERO;

    @Column(name = "outcome_operational")
    @Nullable
    private BigDecimal outcomeOperational = BigDecimal.ZERO;

    @Column(name = "outcome_event")
    @Nullable
    private BigDecimal outcomeEvent = BigDecimal.ZERO;

    @Column(name = "outcome_other")
    @Nullable
    private BigDecimal outcomeOther = BigDecimal.ZERO;

    @Column(name = "description", columnDefinition = "text")
    @Nullable
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at" )
    private LocalDateTime updateAt;

}
