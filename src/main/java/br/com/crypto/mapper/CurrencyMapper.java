package br.com.crypto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {

    @Autowired
    private ModelMapper mapper;
}
