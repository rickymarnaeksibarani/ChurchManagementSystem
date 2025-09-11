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
@Table(name = "tb_income")
public class IncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_income")
    private long idIncome;

    @Column(name = "income_date") //yyyy-MM-dd
    private Date incomeDate;


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

    @Column(name = "persembahan")
    private BigDecimal persembahan = BigDecimal.ZERO;

    @Column(name = "perpuluhan")
    private BigDecimal perpuluhan = BigDecimal.ZERO;

    @Column(name = "pembangunan")
    private BigDecimal pembangunan = BigDecimal.ZERO;

    @Column(name = "service")
    private BigDecimal service = BigDecimal.ZERO;

    @Column(name = "donasi")
    private BigDecimal donasi = BigDecimal.ZERO;

    @Column(name = "lainnya")
    private BigDecimal lainnya = BigDecimal.ZERO;

    @Column(name = "total_income")
    @Nullable
    private BigDecimal totalIncome = BigDecimal.ZERO;

    @PrePersist
    @PreUpdate
    public void calculateTotalIncome() {
        this.totalIncome = BigDecimal.ZERO;
        if (persembahan != null) totalIncome = totalIncome.add(persembahan);
        if (perpuluhan != null) totalIncome = totalIncome.add(perpuluhan);
        if (pembangunan != null) totalIncome = totalIncome.add(pembangunan);
        if (service != null) totalIncome = totalIncome.add(service);
        if (donasi != null) totalIncome = totalIncome.add(donasi);
        if (lainnya != null) totalIncome = totalIncome.add(lainnya);
    }
}
