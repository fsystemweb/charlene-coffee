package services;


import enums.CoffeeSize;
import enums.ProductType;
import models.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.Constants.BEVERAGE_PROMO_DSC;
import static utils.Constants.BONUSSNACK_PROMO_DSC;

public class DiscountServiceTest {
    @InjectMocks
    private DiscountServiceImpl discountService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addDiscounts_withFourteenBeverage(){
        List<Product> saleList = createSaleListFourteenBeverages();
        List<Product> expected = createSaleListFourteenBeverages();
        expected.add(new Product(BEVERAGE_PROMO_DSC, null, 3.20, ProductType.discount, "EUR"));
        expected.add(new Product(BEVERAGE_PROMO_DSC, null, 2.30, ProductType.discount, "EUR"));

        List<Product> result = discountService.addDiscounts(saleList);

        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(13).getName(), result.get(13).getName());
        assertEquals(expected.get(14).getPrice(), result.get(14).getPrice());
    }

    @Test
    public void addDiscounts_withBonusSnack(){
        List<Product> saleList = createSaleListBonusSnack();
        List<Product> expected = createSaleListBonusSnack();
        expected.add(new Product(BONUSSNACK_PROMO_DSC, null, 1.20, ProductType.discount, "EUR"));

        List<Product> result = discountService.addDiscounts(saleList);

        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(6).getName(), result.get(6).getName());
        assertEquals(expected.get(6).getPrice(), result.get(6).getPrice());
    }

    private List<Product> createSaleListFourteenBeverages(){
        List<Product> saleList = new ArrayList<Product>();
        Product orangeJuice = new Product("orange juice", null, 2.30, ProductType.juice, "EUR");
        Product coffee = new Product("coffee", CoffeeSize.large, 3.20, ProductType.coffee, "EUR");
        saleList.add(coffee);
        saleList.add(orangeJuice);
        saleList.add(coffee);
        saleList.add(orangeJuice);
        saleList.add(orangeJuice);
        saleList.add(orangeJuice);
        saleList.add(coffee);
        saleList.add(coffee);
        saleList.add(coffee);
        saleList.add(orangeJuice);
        saleList.add(orangeJuice);
        saleList.add(orangeJuice);
        saleList.add(coffee);
        saleList.add(orangeJuice);


        return saleList;
    }

    private List<Product> createSaleListBonusSnack(){
        List<Product> saleList = new ArrayList<Product>();
        Product orangeJuice = new Product("orange juice", null, 2.30, ProductType.juice, "EUR");
        Product coffee = new Product("coffee", CoffeeSize.large, 3.20, ProductType.coffee, "EUR");
        Product baconRoll = new Product("bacon roll", null, 2.20, ProductType.snack, "EUR");
        Product extraMilk = new Product("extra milk", null, 1.20, ProductType.extra, "EUR");

        saleList.add(orangeJuice);
        saleList.add(coffee);
        saleList.add(coffee);
        saleList.add(extraMilk);
        saleList.add(baconRoll);
        saleList.add(baconRoll);

        return saleList;
    }
}
