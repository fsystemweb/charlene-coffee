package services;


import enums.CoffeeSize;
import enums.ProductType;
import models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.Constants.ERROR_INVALID_PRODUCT;
import static utils.Constants.EXTRA_PRODUCT_CONNECTOR;

public class SaleServiceImpl implements SaleService {

    private ProductService productService;
    public SaleServiceImpl(ProductService productService){
        this.productService = productService;
    }

    public List<Product> readProducts(String[] products) throws Exception{
        List<Product> saleList = new ArrayList<Product>();
        List<Product> availableProducts =  productService.getProducts();

        Stream<String> streamProducts = Arrays.stream(products);

        streamProducts.forEach(product -> {
            String[] productStructure = product.toLowerCase().split(" ");

            checkInvalidProduct(productStructure[0]);


            if(isSize(productStructure[0])){
                addCoffee(productStructure, availableProducts, saleList);
            }else{
                Product newItem = getProductInfo(availableProducts, product, null);
                addSaleItem(newItem, product, saleList);
            };
        });

        return saleList;
    }

    private void checkInvalidProduct(String productName){
        if(productName.matches(ProductType.coffee.name()))
            throw new RuntimeException(ERROR_INVALID_PRODUCT + productName);
    }

    private Boolean isSize(String size){
        if(size.matches(CoffeeSize.large.name())
                || size.matches(CoffeeSize.medium.name())
                || size.matches(CoffeeSize.small.name()) )
            return true;
        return false;
    }

    private void addCoffee(String[] coffeeStructure, List<Product> availableProducts, List<Product> saleList){
        checkInvalidCoffee(coffeeStructure);

        Product newItem = getProductInfo(availableProducts, coffeeStructure[1], CoffeeSize.valueOf(coffeeStructure[0]));
        addSaleItem(newItem, coffeeStructure[1], saleList);

        if(coffeeStructure.length > 2)
            addExtraProduct(coffeeStructure, availableProducts, saleList);

    }


    private Product getProductInfo(List<Product> availableProducts, String productName, CoffeeSize size){
        List<Product> productVerify = availableProducts.stream()
                .filter(product -> product.getName().matches(productName))
                .filter(product -> product.getSize() == size)
                .collect(Collectors.toList());

        if(productVerify.isEmpty())
            return new Product();

        return productVerify.get(0);

    }


    private void addSaleItem(Product newItem, String productName, List<Product> saleList){
        if(newItem.getName() == null) {
            throw new RuntimeException(ERROR_INVALID_PRODUCT + productName);
        }else{
            saleList.add(newItem);
        }
    }

    private Boolean invalidCoffee(String[] coffeeStructure){
        return coffeeStructure.length == 3 || !coffeeStructure[1].matches(ProductType.coffee.name());
    }

    private Boolean invalidExtra(String[] coffeeStructure){
        return !coffeeStructure[2].matches(EXTRA_PRODUCT_CONNECTOR);
    }

    private void addExtraProduct(String[] coffeeStructure, List<Product> availableProducts, List<Product> saleList){
        String extraProduct = "";
        for(int i = 3; i < coffeeStructure.length; ++i) {
            if(extraProduct.matches(""))
                extraProduct = coffeeStructure[i];
            else
                extraProduct = extraProduct+" "+coffeeStructure[i];
        }
        Product newItem = getProductInfo(availableProducts, extraProduct, null);
        addSaleItem(newItem, coffeeStructure[1], saleList);
    }

    private void checkInvalidCoffee(String[] coffeeStructure){
        if(coffeeStructure.length == 1)
            throw new RuntimeException(ERROR_INVALID_PRODUCT + coffeeStructure);

        if(invalidCoffee(coffeeStructure))
            throw new RuntimeException(ERROR_INVALID_PRODUCT + coffeeStructure);

        if(coffeeStructure.length > 5)
            throw new RuntimeException(ERROR_INVALID_PRODUCT + coffeeStructure);

        if(coffeeStructure.length > 2)
            if(invalidExtra(coffeeStructure))
                throw new RuntimeException(ERROR_INVALID_PRODUCT + coffeeStructure);

    }
}
