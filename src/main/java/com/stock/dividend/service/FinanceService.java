package com.stock.dividend.service;

import com.stock.dividend.constants.CacheKey;
import com.stock.dividend.model.Company;
import com.stock.dividend.model.Dividend;
import com.stock.dividend.model.ScrapedResult;
import com.stock.dividend.persist.CompanyRepository;
import com.stock.dividend.persist.DividendRepository;
import com.stock.dividend.persist.entity.CompanyEntity;
import com.stock.dividend.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE)
    public ScrapedResult getDividenByCompanyName(String companyName) {
        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다"));

        // 2. 조회된 회사 ID로 배당금 정보 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> new Dividend(e.getDate(), e.getDividend()))
                        .collect(Collectors.toList());

        return new ScrapedResult(new Company(company.getTicker(),company.getName()),
                dividends);
    }
}
