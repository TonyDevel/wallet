package com.wallet;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

public class ConversionTestUtil {

    public static ConversionService initConverters(Converter... converters) {
        GenericConversionService conversionService = new GenericConversionService();
        for (Converter converter : converters) {
            conversionService.addConverter(converter);
        }
        return conversionService;
    }
}
