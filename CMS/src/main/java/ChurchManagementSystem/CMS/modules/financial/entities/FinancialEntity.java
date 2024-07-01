package ChurchManagementSystem.CMS.modules.financial.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "master_financial")
public class FinancialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_financial")
    private Long idFinancial;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "total_income")
    private BigDecimal totalIncome;

    @Column(name = "total_outcome")
    private BigDecimal totalOutcome;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at" )
    private LocalDateTime updateAt;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "financialEntity", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<IncomeEntity> incomeList;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToMany(mappedBy = "financialEntity", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OutcomeEntity> outcomeList;

    public FinancialEntity(BigDecimal balance) {
    }
}


