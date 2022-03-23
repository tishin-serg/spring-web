package ru.tishin.springweb.cart.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import ru.tishin.springweb.api.core.ProductDto;

import java.time.LocalDate;
import java.util.List;

/*
    Содержит в себе лист ProductDto, в которых хранится количество добавлений в корзину за день
    и дату за которую собрана статистика
 */
public class ProductsWithDailyStat {
    private List<ProductDto> productDtoList;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate localDate;

    public ProductsWithDailyStat(List<ProductDto> productDtoList) {
        this.productDtoList = productDtoList;
        this.localDate = LocalDate.now();
    }

    public ProductsWithDailyStat() {
    }

    public List<ProductDto> getProductDtoList() {
        return productDtoList;
    }

    public void setProductDtoList(List<ProductDto> productDtoList) {
        this.productDtoList = productDtoList;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
