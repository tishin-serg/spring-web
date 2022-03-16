package ru.tishin.springweb.api.cart;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.tishin.springweb.api.core.ProductDto;

import java.time.LocalDate;
import java.util.List;

/*
    Содержит в себе лист ProductDto, в которых хранится количество добавлений в корзину за день
    и дату за которую собрана статистика
 */
@Schema(description = "Модель продукта с дневной статистикой добавления в корзину")
public class ProductsWithDailyStatDto {
    @Schema(description = "Список моделей продуктов со статистикой")
    private List<ProductDto> productDtoList;

    @Schema(description = "День, за который собиралась статистика", example = "2022-01-01")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate localDate;

    public ProductsWithDailyStatDto(List<ProductDto> productDtoList, LocalDate localDate) {
        this.productDtoList = productDtoList;
        this.localDate = localDate;
    }

    public ProductsWithDailyStatDto() {
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
