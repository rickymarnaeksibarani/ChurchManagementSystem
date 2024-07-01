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
@Table(name = "tb_income")
public class IncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_income")
    private long idIncome;

    @Column(name = "income_give")
    @Nullable
    private BigDecimal incomeGive;

    @Column(name = "income_tenth")
    @Nullable
    private BigDecimal incomeTenth;

    @Column(name = "income_building")
    @Nullable
    private BigDecimal incomeBuilding;

    @Column(name = "income_service")
    @Nullable
    private BigDecimal incomeService;

    @Column(name = "income_donate")
    @Nullable
    private BigDecimal incomeDonate;

    @Column(name = "income_other")
    private BigDecimal incomeOther;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at" )
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "financial_entity_id_financial", foreignKey = @ForeignKey(name = "fk_income_financial"))
    @JsonBackReference
    private FinancialEntity financialEntity;

}
