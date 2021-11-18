package ru.tishin.springweb.repository;

import org.springframework.stereotype.Component;
import ru.tishin.springweb.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepository {
    private List<Product> catalog;

    public List<Product> getCatalog() {
        return Collections.unmodifiableList(catalog);
    }

    // Пока плаваю в теме стрим апи, не понял, как выбросить исключение в этом случае.
    // OrElseThrow в параметры не принимает Exception
    public Product findByID(Long id) {
        return catalog.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().get();
    }

    /* Вот здесь столкнулся с небольшой проблемкой. сначала писал
     catalog = List.of(
                new Product(1L, "Tomato", 50), и тд

     Всё работало, каталог отрисовывался во фронте и менялась стоимость, но продукты не удалялись по кнопке Удалить
     выкидывало исключение (UnsupportedOperationException), которое ругалось, что я не могу менять иммутабельный лист

    Но если писать catalog = new ArrayList<>(List.of(
    То всё норм и продукты удаляются. Объясните пожалуйста, почему тогда, при такой записи мы можем менять лист, хотя сами
    возвращаем иммутабельный.
    Наверное, вопрос из джавы 2, но всё же буду благодарен за ответ!
     */
    @PostConstruct
    public void init() {
        catalog = new ArrayList<>(List.of(
                new Product(1L, "Tomato", 50),
                new Product(2L, "Cucumber", 30),
                new Product(3L, "Apple", 20),
                new Product(4L, "Banana", 20),
                new Product(5L, "Carrot", 10)
        ));
    }

    public void deleteById(Long id) {
        catalog.removeIf(product -> product.getId().equals(id));
    }
}
