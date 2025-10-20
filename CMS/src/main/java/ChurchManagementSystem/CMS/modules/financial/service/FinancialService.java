package ChurchManagementSystem.CMS.modules.financial.service;

import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.dto.income.*;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.*;
import ChurchManagementSystem.CMS.modules.financial.dto.SummaryDto;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.repository.IncomeRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.OutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
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

    public Pair<IncomeFinancialDetailDto, PaginationUtil<IncomeFinancialDetailItemDto, IncomeFinancialDetailItemDto>>
    getFinancialDetailByIncome(IncomeRequestDto requestDto) {

        int zeroBasedPage = Math.max(0, requestDto.getPage() - 1);
        Pageable pageable = PageRequest.of(zeroBasedPage, requestDto.getSize());

        Specification<IncomeEntity> spec = Specification.where(IncomePredicate.category(requestDto.getCategory()))
                .and(IncomePredicate.betweenDates(requestDto.getPeriodStartTime(), requestDto.getPeriodEndTime()))
                .and(IncomePredicate.specificDate(requestDto.getSpecificDate()));
        List<IncomeEntity> incomes = incomeRepository.findAll(spec);

        IncomeFinancialDetailDto result = new IncomeFinancialDetailDto();
        List<IncomeFinancialDetailItemDto> allDetails = new ArrayList<>();

        for (IncomeEntity income : incomes) {
            // Hitung total kategori
            result.setTotalPersembahan(result.getTotalPersembahan().add(
                    income.getPersembahan() != null ? income.getPersembahan() : BigDecimal.ZERO));
            result.setTotalPerpuluhan(result.getTotalPerpuluhan().add(
                    income.getPerpuluhan() != null ? income.getPerpuluhan() : BigDecimal.ZERO));
            result.setTotalPembangunan(result.getTotalPembangunan().add(
                    income.getPembangunan() != null ? income.getPembangunan() : BigDecimal.ZERO));
            result.setTotalService(result.getTotalService().add(
                    income.getService() != null ? income.getService() : BigDecimal.ZERO));
            result.setTotalDonasi(result.getTotalDonasi().add(
                    income.getDonasi() != null ? income.getDonasi() : BigDecimal.ZERO));
            result.setTotalLainnya(result.getTotalLainnya().add(
                    income.getLainnya() != null ? income.getLainnya() : BigDecimal.ZERO));

            // Tambahkan detail berdasarkan category
            if (requestDto.getCategory() != null && !requestDto.getCategory().isEmpty()) {
                BigDecimal nominal = switch (requestDto.getCategory().toLowerCase()) {
                    case "persembahan" -> income.getPersembahan();
                    case "perpuluhan" -> income.getPerpuluhan();
                    case "pembangunan" -> income.getPembangunan();
                    case "service" -> income.getService();
                    case "donasi" -> income.getDonasi();
                    case "lainnya" -> income.getLainnya();
                    default -> BigDecimal.ZERO;
                };

                if (nominal != null && nominal.compareTo(BigDecimal.ZERO) > 0) {
                    String keterangan = income.getDeskripsi() != null ? income.getDeskripsi() : "";
                    allDetails.add(new IncomeFinancialDetailItemDto(income.getIncomeDate(), capitalize(requestDto.getCategory()), nominal, keterangan, income.getNama()));
                }
            }
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allDetails.size());

        List<IncomeFinancialDetailItemDto> pagedList = allDetails.subList(start, end);
        Page<IncomeFinancialDetailItemDto> pageResult = new PageImpl<>(pagedList, pageable, allDetails.size());

        PaginationUtil<IncomeFinancialDetailItemDto, IncomeFinancialDetailItemDto> pagination =
                new PaginationUtil<>(pageResult, IncomeFinancialDetailItemDto.class);

        return Pair.of(result, pagination);
    }

    public Pair<OutcomeFinancialDetailDto, PaginationUtil<OutcomeFinancialDetailItemDto, OutcomeFinancialDetailItemDto>>
    getFinancialDetailByOutcome(OutcomeRequestDto requestDto) {

        int zeroBasedPage = Math.max(0, requestDto.getPage() - 1);
        Pageable pageable = PageRequest.of(zeroBasedPage, requestDto.getSize());

        Specification<OutcomeEntity> spec = OutcomePredicate.category(requestDto.getCategory())
                .and(OutcomePredicate.betweenDates(requestDto.getPeriodStartTime(), requestDto.getPeriodEndTime()))
                .and(OutcomePredicate.specificDate(requestDto.getSpecificDate()));
        List<OutcomeEntity> outcomes = outcomeRepository.findAll(spec);

        OutcomeFinancialDetailDto result = new OutcomeFinancialDetailDto();
        List<OutcomeFinancialDetailItemDto> allDetails = new ArrayList<>();

        for (OutcomeEntity outcome : outcomes) {
            // Hitung total kategori
            result.setTotalDeposit(result.getTotalDeposit().add(
                    outcome.getDeposit() != null ? outcome.getDeposit() : BigDecimal.ZERO));
            result.setTotalPembangunan(result.getTotalPembangunan().add(
                    outcome.getPembangunan() != null ? outcome.getPembangunan() : BigDecimal.ZERO));
            result.setTotalOperasional(result.getTotalOperasional().add(
                    outcome.getOperasional() != null ? outcome.getOperasional() : BigDecimal.ZERO));
            result.setTotalDiakonia(result.getTotalDiakonia().add(
                    outcome.getDiakonia() != null ? outcome.getDiakonia() : BigDecimal.ZERO));
            result.setTotalPelayanan(result.getTotalPelayanan().add(
                    outcome.getPelayanan() != null ? outcome.getPelayanan() : BigDecimal.ZERO));
            result.setTotalAcara(result.getTotalAcara().add(
                    outcome.getAcara() != null ? outcome.getAcara() : BigDecimal.ZERO));
            result.setTotalLainnya(result.getTotalLainnya().add(
                    outcome.getLainnya() != null ? outcome.getLainnya() : BigDecimal.ZERO));

            // Tambahkan detail berdasarkan category
            if (requestDto.getCategory() != null && !requestDto.getCategory().isEmpty()) {
                BigDecimal nominal = switch (requestDto.getCategory().toLowerCase()) {
                    case "deposit" -> outcome.getDeposit();
                    case "pembangunan" -> outcome.getPembangunan();
                    case "diakonia" -> outcome.getDiakonia();
                    case "pelayanan" -> outcome.getPelayanan();
                    case "operasional" -> outcome.getOperasional();
                    case "acara" -> outcome.getAcara();
                    case "lainnya" -> outcome.getLainnya();
                    default -> BigDecimal.ZERO;
                };

                if (nominal != null && nominal.compareTo(BigDecimal.ZERO) > 0) {
                    String keterangan = outcome.getDeskripsi() != null ? outcome.getDeskripsi() : "";
                    allDetails.add(new OutcomeFinancialDetailItemDto(outcome.getOutcomeDate(), capitalize(requestDto.getCategory()), nominal, keterangan, outcome.getNama()));
                }
            }
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allDetails.size());

        List<OutcomeFinancialDetailItemDto> pagedList = allDetails.subList(start, end);
        Page<OutcomeFinancialDetailItemDto> pageResult = new PageImpl<>(pagedList, pageable, allDetails.size());

        PaginationUtil<OutcomeFinancialDetailItemDto, OutcomeFinancialDetailItemDto> pagination =
                new PaginationUtil<>(pageResult, OutcomeFinancialDetailItemDto.class);

        return Pair.of(result, pagination);
    }


    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }


    //Total income by query paramater year, month and all month
    public BigDecimal getTotalIncomeByYearAndMonth(int year, String month) {
        if (month == null || month.isBlank()) {
            return incomeRepository.sumTotalIncomeByYear(year);
        }

        try {
            int monthNumber = Month.valueOf(month.toUpperCase()).getValue();
            return incomeRepository.sumTotalIncomeByYearAndMonth(year, monthNumber);
        } catch (IllegalArgumentException e) {
            throw new CustomRequestException("Invalid month name: " + month, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomRequestException("Failed to calculate total income: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //Total outcome by query paramater year, month and all month
    public BigDecimal getTotalOutcomeByYearAndMonth(int year, String month) {
        if (month == null || month.isBlank()) {
            return outcomeRepository.sumTotalOutcomeByYear(year);
        }

        try {
            int monthNumber = Month.valueOf(month.toUpperCase()).getValue();
            return outcomeRepository.sumTotalOutcomeByYearAndMonth(year, monthNumber);
        } catch (IllegalArgumentException e) {
            throw new CustomRequestException("Invalid month name: " + month, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new CustomRequestException("Failed to calculate total income: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}


