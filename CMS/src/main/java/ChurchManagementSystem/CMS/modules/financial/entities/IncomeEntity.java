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
@Table(name = "tb_income")
public class IncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_income")
    private long idIncome;

    @Column(name = "income_date") //yyyy-MM-dd
    private Date incomeDate;

    @Column(name = "income_give")
    @Nullable
    private BigDecimal incomeGive = BigDecimal.ZERO;

    @Column(name = "income_tenth")
    @Nullable
    private BigDecimal incomeTenth = BigDecimal.ZERO;

    @Column(name = "income_building")
    @Nullable
    private BigDecimal incomeBuilding =  BigDecimal.ZERO;

    @Column(name = "income_service")
    @Nullable
    private BigDecimal incomeService = BigDecimal.ZERO;

    @Column(name = "income_donate")
    @Nullable
    private BigDecimal incomeDonate = BigDecimal.ZERO;

    @Column(name = "income_other")
    @Nullable
    private BigDecimal incomeOther = BigDecimal.ZERO;

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
