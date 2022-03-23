package ru.tishin.springweb.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.api.core.ProductDto;
import ru.tishin.springweb.api.exceptions.ResourceNotFoundException;
import ru.tishin.springweb.cart.integrations.ProductServiceIntegration;
import ru.tishin.springweb.cart.models.Cart;
import ru.tishin.springweb.cart.models.ProductsWithDailyStat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartStatisticService {

    private final RedisTemplate<String, Object> redisDailyCartStatisticTemplate;
    private final RedisTemplate<String, Object> redisAllCartStatisticTemplate;
    private final ProductServiceIntegration productServiceIntegration;

    @Value("${utils.cart-statistic-cashe.prefix}")
    private String cartStatisticCashPrefix;

    @Value("${utils.cart-statistic-entire.prefix}")
    private String cartStatisticEntirePrefix;

    /*
    Добавляет товар в дневной кэш редиса. Срабатывает при добавлении товара в корзину
     */
    protected void add(ProductDto productDto) {
        if (!redisDailyCartStatisticTemplate.hasKey(getProductStatKeyFromId(productDto.getId()))) {
            productDto.setCount(1);
            updateProductStat(productDto.getId(), productDto);
            return;
        }
        changeProductsCount(productDto.getId(), 1);
    }

    /*
    Меняет количество добавленных за день продуктов в корзину на delta
     */
    protected void changeProductsCount(Long productId, int delta) {
        ProductDto productDto = getActualProductStat(productId);
        int count = productDto.getCount();
        count += delta;
        if (count < 0) {
            productDto.setCount(0);
            redisDailyCartStatisticTemplate.delete(getProductStatKeyFromId(productId));
        } else {
            productDto.setCount(count);
        }
        updateProductStat(productId, productDto);
    }

    protected void decrease(Long productId) {
        changeProductsCount(productId, -1);
    }

    public void decrease(Long productId, int delta) {
        changeProductsCount(productId, delta);
    }

    /*
    Возвращает статистику по определенному продукту за день
     */
    protected ProductDto getActualProductStat(Long productId) {
        return (ProductDto) redisDailyCartStatisticTemplate.opsForValue().get(getProductStatKeyFromId(productId));
    }

    protected void updateProductStat(Long productId, ProductDto productDto) {
        redisDailyCartStatisticTemplate.opsForValue().set(getProductStatKeyFromId(productId), productDto);
    }

    protected void setProductsCount(Long productId, int count) {
        ProductDto productDto = getActualProductStat(productId);
        productDto.setCount(count);
        updateProductStat(productId, productDto);
    }

    public Integer getProductsCount(Long productId) {
        return getActualProductStat(productId).getCount();
    }

    /*
    Если у корзины был вызван метод remove(), то вычисляем количество этих продуктов в корзине,
    затем уменьшаем на это же количество в статистике
     */
    protected void removeProductFromTargetCart(Cart cart, Long productId) {
        long count = cart.getItems().stream().filter(cartItem -> cartItem.getProductId().equals(productId)).count();
        changeProductsCount(productId, (int) count);
    }

    /*
    Если у корзины был вызван метод clear(), вычисляем количество всех продуктов находящихся в корзине,
    затем уменьшаем показатели поочередно у каждого товара в статистике
     */
    protected void removeProductsFromTargetCart(Cart cart) {
        cart.getItems().forEach(cartItem -> changeProductsCount(cartItem.getProductId(), -cartItem.getQuantity()));
    }

    /*
    Возвращает статистику по определенным продуктам за последние сутки.
    ProductDto.count хранит в себе количество добавлений в корзину за день
     */
    public List<ProductDto> getStatisticsFromProducts(List<Long> listProductId) {
        List<String> listProductStatKeys = listProductId.stream().map(this::getProductStatKeyFromId).collect(Collectors.toList());

        return redisDailyCartStatisticTemplate.opsForValue().multiGet(listProductStatKeys)
                .stream()
                .map(o -> (ProductDto) o)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /*
    Собирает статистику по добавляемым товарам в корзину за день в лист.
     */
    @Scheduled(cron = "@midnight")
    public void saveDailyCartStatistic() {

        // Берем из productService id всех продуктов
        List<Long> allProductDtoIdList = productServiceIntegration.getAllProducts().stream()
                .map(ProductDto::getId)
                .collect(Collectors.toList());

        // Собираем статистику по добавленным товарам в корзину за день, сортируем по убыванию
        List<ProductDto> productDtos = getStatisticsFromProducts(allProductDtoIdList).stream()
                .sorted(Comparator.comparingInt(ProductDto::getCount).reversed())
                .collect(Collectors.toList());

        // Оборачиваем в объект, где хранится LocalDate (дата создания объекта)
        ProductsWithDailyStat dailyStat = new ProductsWithDailyStat(productDtos);

        // Отправляем в редис, где хранится стата за всё время
        String key = cartStatisticEntirePrefix + dailyStat.getLocalDate().toString();
        redisAllCartStatisticTemplate.opsForValue().set(key, dailyStat);

        // Очищаем ежедневный кэш
        List<String> keysForDelete = productDtos.stream()
                .map(productDto -> getProductStatKeyFromId(productDto.getId()))
                .collect(Collectors.toList());

        redisDailyCartStatisticTemplate.delete(keysForDelete);
    }

    /*
    Находит статистику по продуктам за определенным день @localDate
     */
    public ProductsWithDailyStat getStatisticsForDefinedPeriod(LocalDate localDate) {
        String key = getProductStatKeyFromDate(localDate);
        if (!redisAllCartStatisticTemplate.hasKey(key)) {
            throw new ResourceNotFoundException("Объект с ключом = " + key + " не найден");
        }
        return (ProductsWithDailyStat) redisAllCartStatisticTemplate.opsForValue().get(key);
    }

    public ProductsWithDailyStat getStatisticsForDefinedPeriod(Integer year, Integer month, Integer day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return getStatisticsForDefinedPeriod(localDate);
    }

    /*
    Находит статистику за определенным месяц с помощью keyPattern.
    Например 2022-02* выдаст лист дневных статистик за февраль 2022
     */
    public List<ProductsWithDailyStat> getStatisticsForDefinedPeriod(String year, String month) {
        String keyPattern = getProductStatKeyFromDate(year, month) + "*";
        Set<String> keys = redisAllCartStatisticTemplate.keys(keyPattern);
        if (keys == null) {
            throw new ResourceNotFoundException("Ключи с паттерном = " + keyPattern + " не найдены");
        }
        return redisAllCartStatisticTemplate.opsForValue()
                .multiGet(keys)
                .stream()
                .map(o -> (ProductsWithDailyStat) o)
                .collect(Collectors.toList());
    }

    public String getProductStatKeyFromId(Long productId) {
        return cartStatisticCashPrefix + productId;
    }

    public String getProductStatKeyFromDate(LocalDate localDate) {
        return cartStatisticEntirePrefix + localDate.toString();
    }

    public String getProductStatKeyFromDate(String year, String month) {
        return cartStatisticEntirePrefix + year + "-" + month;
    }
}
