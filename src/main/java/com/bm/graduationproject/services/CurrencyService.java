package com.bm.graduationproject.services;

import com.bm.graduationproject.dtos.ConversionResponseDto;
import com.bm.graduationproject.dtos.CurrencyResponseDto;
import com.bm.graduationproject.enums.Currency;
import com.bm.graduationproject.models.ConversionOpenApiResponse;
import com.bm.graduationproject.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService implements BaseCurrencyService {

    private final CurrencyRepository repository;

    @Autowired
    public CurrencyService(CurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public ConversionResponseDto convert(String from, String to, double amount) {
        ConversionOpenApiResponse apiResponse = repository.getCurrencyPair(from, to);

        ConversionResponseDto dataDto = new ConversionResponseDto();
        dataDto.setSource(from.toUpperCase());
        dataDto.setDestination(to.toUpperCase());
        dataDto.setAmount(apiResponse.getConversion_rate() * amount);
        return dataDto;
    }

    @Override
    public List<CurrencyResponseDto> getAllCurrencies() {
        return Arrays.stream(Currency.values())
                .map(c -> new CurrencyResponseDto(c.name(), c.getCountry(), c.getFlagImageUrl()))
                .collect(Collectors.toList());
    }
}
