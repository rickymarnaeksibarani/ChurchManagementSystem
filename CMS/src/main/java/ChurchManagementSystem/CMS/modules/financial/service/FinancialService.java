package ChurchManagementSystem.CMS.modules.financial.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.dto.income.*;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeFinancialDetailDto;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeFinancialDetailItemDto;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomePredicate;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.SummaryDto;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.repository.IncomeRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.OutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialService {
    private final IncomeRepository incomeRepository;
    private final OutcomeRepository outcomeRepository;

    public IncomeEntity saveIncome (IncomeEntity incomeEntity){
        return incomeRepository.save(incomeEntity);
    }

    public PaginationUtil<IncomeEntity, IncomeResponeDto> getAllIncomeByPagination(IncomeRequestDto request) {
        Pageable paging = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("incomeDate").descending());
        Page<IncomeEntity> pagedResult = incomeRepository.findAll(paging);
        return new PaginationUtil<>(pagedResult, IncomeResponeDto.class);
    }

    public OutcomeEntity saveOutcome (OutcomeEntity outcomeEntity){
        return outcomeRepository.save(outcomeEntity);
    }

    public PaginationUtil<OutcomeEntity, OutcomeResponeDto> getAllOutcomeByPagination(IncomeRequestDto request) {
        Pageable paging = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("outcomeDate").descending());
        Page<OutcomeEntity> pagedResult = outcomeRepository.findAll(paging);

        return new PaginationUtil<>(pagedResult, OutcomeResponeDto.class);
    }

    public SummaryDto getSummary(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        BigDecimal monthlyIncome = incomeRepository.findByIncomeDateBetween(start, end)
                .stream().map(IncomeEntity::getTotalIncome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthlyOutcome = outcomeRepository.findByOutcomeDateBetween(start, end)
                .stream().map(OutcomeEntity::getTotalOutcome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalIncome = incomeRepository.findAll().stream()
                .map(IncomeEntity::getTotalIncome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOutcome = outcomeRepository.findAll().stream()
                .map(OutcomeEntity::getTotalOutcome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSummary = totalIncome.subtract(totalOutcome);
        BigDecimal balance = totalIncome.subtract(totalOutcome);

        return new SummaryDto(monthlyIncome, monthlyOutcome, totalSummary, balance, totalIncome, totalOutcome);
    }

    //Get Income Financial Detail
    public IncomeFinancialDetailDto getFinancialDetailByIncome() {
        Object[] totals = incomeRepository.findTotalFinancialDetail();

        IncomeFinancialDetailDto dto = new IncomeFinancialDetailDto();

        dto.setTotalPersembahan((BigDecimal) totals[0]);
        dto.setTotalPerpuluhan((BigDecimal) totals[1]);
        dto.setTotalPembangunan((BigDecimal) totals[2]);
        dto.setTotalService((BigDecimal) totals[3]);
        dto.setTotalDonasi((BigDecimal) totals[4]);
        dto.setTotalLainnya((BigDecimal) totals[5]);

        return dto;
    }

    public IncomeFinancialDetailDto getFinancialDetailByIncome(String category) {
        Specification<IncomeEntity> spec = IncomePredicate.category(category);

        // Ambil semua data income yang sesuai category filter
        List<IncomeEntity> incomes = incomeRepository.findAll(spec != null ? spec : Specification.where(null));

        IncomeFinancialDetailDto result = new IncomeFinancialDetailDto();

        for (IncomeEntity income : incomes) {
            // Hitung total masing-masing kategori
            result.setTotalPersembahan(result.getTotalPersembahan().add(income.getPersembahan() != null ? income.getPersembahan() : BigDecimal.ZERO));
            result.setTotalPerpuluhan(result.getTotalPerpuluhan().add(income.getPerpuluhan() != null ? income.getPerpuluhan() : BigDecimal.ZERO));
            result.setTotalPembangunan(result.getTotalPembangunan().add(income.getPembangunan() != null ? income.getPembangunan() : BigDecimal.ZERO));
            result.setTotalService(result.getTotalService().add(income.getService() != null ? income.getService() : BigDecimal.ZERO));
            result.setTotalDonasi(result.getTotalDonasi().add(income.getDonasi() != null ? income.getDonasi() : BigDecimal.ZERO));
            result.setTotalLainnya(result.getTotalLainnya().add(income.getLainnya() != null ? income.getLainnya() : BigDecimal.ZERO));

            // Tambahkan detail item berdasarkan kategori yang dicari
            if (category == null || category.isEmpty()) {
                // Jika category null, tampilkan semua kategori yang > 0 sebagai detail (bisa dipilih atau di skip)
                // Untuk simplifikasi, skip detail list kalau kategori kosong (atau implementasi bisa disesuaikan)
                continue;
            }

            BigDecimal nominal = BigDecimal.ZERO;
            switch (category.toLowerCase()) {
                case "persembahan":
                    nominal = income.getPersembahan();
                    break;
                case "perpuluhan":
                    nominal = income.getPerpuluhan();
                    break;
                case "pembangunan":
                    nominal = income.getPembangunan();
                    break;
                case "service":
                    nominal = income.getService();
                    break;
                case "donasi":
                    nominal = income.getDonasi();
                    break;
                case "lainnya":
                    nominal = income.getLainnya();
                    break;
            }

            if (nominal != null && nominal.compareTo(BigDecimal.ZERO) > 0) {
                String keterangan = income.getNama() != null ? income.getNama() : (income.getDeskripsi() != null ? income.getDeskripsi() : "");
                result.getDetails().add(new IncomeFinancialDetailItemDto(income.getIncomeDate(), capitalize(category), nominal, keterangan));
            }
        }

        return result;
    }

    public OutcomeFinancialDetailDto getFinancialDetailByOutcome(String category) {
        Specification<OutcomeEntity> spec = OutcomePredicate.category(category);

        // Ambil semua data income yang sesuai category filter
        List<OutcomeEntity> outcomes = outcomeRepository.findAll(spec != null ? spec : Specification.where(null));


        OutcomeFinancialDetailDto result = new OutcomeFinancialDetailDto();

        for (OutcomeEntity outcome : outcomes) {
            // Hitung total masing-masing kategori
            result.setTotalDeposit(result.getTotalDeposit().add(outcome.getDeposit() != null ? outcome.getDeposit() : BigDecimal.ZERO));
            result.setTotalPembangunan(result.getTotalPembangunan().add(outcome.getPembangunan() != null ? outcome.getPembangunan() : BigDecimal.ZERO));
            result.setTotalDiakonia(result.getTotalDiakonia().add(outcome.getDiakonia() != null ? outcome.getDiakonia() : BigDecimal.ZERO));
            result.setTotalPelayanan(result.getTotalPelayanan().add(outcome.getPelayanan() != null ? outcome.getPelayanan() : BigDecimal.ZERO));
            result.setTotalOperasional(result.getTotalOperasional().add(outcome.getOperasional() != null ? outcome.getOperasional() : BigDecimal.ZERO));
            result.setTotalAcara(result.getTotalAcara().add(outcome.getAcara() != null ? outcome.getAcara() : BigDecimal.ZERO));

            // Tambahkan detail item berdasarkan kategori yang dicari
            if (category == null || category.isEmpty()) {
                // Jika category null, tampilkan semua kategori yang > 0 sebagai detail (bisa dipilih atau di skip)
                // Untuk simplifikasi, skip detail list kalau kategori kosong (atau implementasi bisa disesuaikan)
                continue;
            }

            BigDecimal nominal = BigDecimal.ZERO;
            switch (category.toLowerCase()) {
                case "deposit":
                    nominal = outcome.getDeposit();
                    break;
                case "pembangunan":
                    nominal = outcome.getPembangunan();
                    break;
                case "diakonia":
                    nominal = outcome.getDiakonia();
                    break;
                case "pelayanan":
                    nominal = outcome.getPelayanan();
                    break;
                case "operasional":
                    nominal = outcome.getOperasional();
                    break;
                case "acara":
                    nominal = outcome.getAcara();
                    break;
                case "lainnya":
                    nominal = outcome.getLainnya();
                    break;
            }

            if (nominal != null && nominal.compareTo(BigDecimal.ZERO) > 0) {
                String keterangan = outcome.getNama() != null ? outcome.getNama() : (outcome.getDeskripsi() != null ? outcome.getDeskripsi() : "");
                result.getDetails().add(new OutcomeFinancialDetailItemDto(outcome.getOutcomeDate(), capitalize(category), nominal, keterangan));
            }
        }

        return result;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }

}


