package services;


import enums.ProductType;
import models.Product;

import java.util.List;
import java.util.stream.Collectors;

import static utils.Constants.*;

public class DiscountServiceImpl implements DiscountService{

    public DiscountServiceImpl(){};

    public List<Product> addDiscounts(List<Product> saleList){
        addBeverageDiscount(saleList);

        addBonusSnack(saleList);

        return saleList;
    }

    private void addBeverageDiscount(List<Product> saleList){
        List<Product> beverageList = saleList.stream()
                .filter(product -> isBeverage(product.getType()))
                .collect(Collectors.toList());

        int discountQuantity = beverageList.size() / BEVERAGE_PROMO;

        if(discountQuantity > 0)
            addItemDiscount(BEVERAGE_PROMO_DSC, beverageList, saleList, discountQuantity);
    }

    private Boolean isBeverage(ProductType type){
        return type == ProductType.juice || type == ProductType.coffee;
    }

    private void addBonusSnack(List<Product> saleList){
        List<Product> extrasList = saleList.stream()
                .filter(product -> product.getType() == ProductType.extra)
                .collect(Collectors.toList());

        if(extrasList.size() == 0)
            return;

        List<Product> beverageList = saleList.stream()
                .filter(product -> isBeverage(product.getType()))
                .collect(Collectors.toList());

        if(beverageList.size() == 0)
            return;

        List<Product> snackList = saleList.stream()
                .filter(product -> product.getType() == ProductType.snack)
                .collect(Collectors.toList());

        if(snackList.size() == 0)
            return;

        int discountQuantity = getDiscountQuantityForBonusSnack(snackList.size(), beverageList.size());

        addItemDiscount(BONUSSNACK_PROMO_DSC, extrasList, saleList, discountQuantity);

    }

    private int getDiscountQuantityForBonusSnack(int snacks, int beverages){
        if(snacks >= beverages)
            return beverages;
        else
            return snacks;
    }

    private void addItemDiscount(String promoDsc, List<Product> productList, List<Product> saleList
            , int discountQuantity){
        int index = 0;
        do {
            if(productList.size() < (index+1))
                discountQuantity = 0;
            else{
                Product newDiscount = new Product(promoDsc, null, productList.get(index).getPrice()
                        , ProductType.discount, productList.get(index).getCurrency());

                saleList.add(newDiscount);

                discountQuantity--;
                index++;
            }

        } while (discountQuantity > 0);
    }
}
