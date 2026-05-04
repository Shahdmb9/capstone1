package org.example.ecommerce.Services;


import lombok.RequiredArgsConstructor;
import org.example.ecommerce.Model.Category;
import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products=new ArrayList<>();
    private final CategoryService categoryService;

    public ArrayList<Product> getProducts(){
        return products;
    }

    public int addProduct(Product product) {
        ArrayList<Category> categories = categoryService.getCategories();
        if (!categoryExists(product.getCategorylD()))
            return 0;//no category with this id

        for (Product p : products) {
            if (product.getId().equals(p.getId())) {
                return 1;//product with same id exist
            }
        }
        products.add(product);
        return 2;
    }

    public Boolean categoryExists(String id){
        for(Category category:categoryService.getCategories()){
            if(category.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Product productExists(String id){
        for(Product p:products){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }

    public int updateProduct(String id,Product product){
        if(!categoryExists(product.getCategorylD()))
            return 0;//no category with this id
        Product p=productExists(id);

        if(p==null)
            return 1;//no product with this id

        products.set(products.indexOf(p),product);
        return 2;
    }

    public boolean deleteProduct(String id){
        Product p=productExists(id);
        if(p==null)
            return false;

        products.remove(p);
        return true;
    }

    //extra
    public ArrayList<Product> getProductsByCategory(String category){
        ArrayList<Product> productsByCategory=new ArrayList<>();
        Category category1=categoryService.getCategoryByName(category);
        if(category1==null)
            return null;
        for(Product p:products){
            if(p.getCategorylD().equals(category1.getId()))
                productsByCategory.add(p);
        }
        return productsByCategory;
    }

    public ArrayList<Product> getProductsByName(String name){
        ArrayList<Product> productsByName=new ArrayList<>();
        for(Product p:products){
            if(p.getName().matches(name))
                productsByName.add(p);
        }
        return productsByName;
    }

    public ArrayList<Product> getProductsByPrice(Double minPrice,Double maxPrice){
        ArrayList<Product> productsByPrice=new ArrayList<>();
        for(Product p:products){
            if(p.getPrice()>=minPrice&&p.getPrice()<=maxPrice)
                productsByPrice.add(p);
        }
        return productsByPrice;
    }
    public List<Product> sortProductsFromCheapToExpensive(){
        if(products.isEmpty())
            return null;
        List<Product> sortedProducts = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .collect(Collectors.toList());
        return sortedProducts;
    }

    public List<Product> sortProductsFromExpensiveToCheap(){
        if(products.isEmpty())
            return null;
        List<Product> sortedProducts = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .collect(Collectors.toList());
        return sortedProducts;
    }



//    public Product mostSales(){
//        if(products.isEmpty())
//            return null;
//        Product mostSales=products.get(0);
//        for(Product p:products){
//            if(p.getSalesCount()>mostSales.getSalesCount())
//                mostSales=p;
//        }
//        return mostSales;
//    }
//    public Product mostSalesInCategory(String category){
//        ArrayList<Product> productsByCategory=getProductsByCategory(category);
//        if(productsByCategory == null)
//            return null;
//        if(productsByCategory.isEmpty())
//            return null;
//        Product mostSales=productsByCategory.get(0);
//        for(Product p:productsByCategory){
//            if(p.getSalesCount()>mostSales.getSalesCount())
//                mostSales=p;
//        }
//        return mostSales;
//    }

    public List<Product> mostThreeSales(){
        return products.stream()
                .sorted(Comparator.comparingInt(Product::getSalesCount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Product> mostThreeSalesInCategory(String category){
        ArrayList<Product> productsByCategory=getProductsByCategory(category);
        return productsByCategory.stream()
                .sorted(Comparator.comparingInt(Product::getSalesCount).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }




}
