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
        if (!categoryExists(product.getCategorylD()))
            return 0;//no category with this id

        if(productExists(product.getId())!=null)
            return 1;
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
            if(p.getName().toLowerCase().contains(name.toLowerCase()))
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
        List<Product> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, Comparator.comparingDouble(Product::getPrice));
        return sortedProducts;
    }

    public List<Product> sortProductsFromExpensiveToCheap(){
        if(products.isEmpty())
            return null;
        List<Product> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, Comparator.comparingDouble(Product::getPrice).reversed());
        return sortedProducts;
    }


    public List<Product> mostThreeSales(){

        List<Product> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, Comparator.comparingInt(Product::getSalesCount).reversed());
        return sortedProducts.subList(0, Math.min(3, sortedProducts.size()));
    }

    public List<Product> mostThreeSalesInCategory(String category){
        List<Product> sortedProducts = getProductsByCategory(category);
        if(sortedProducts==null)
            return null;
        Collections.sort(sortedProducts, Comparator.comparingInt(Product::getSalesCount).reversed());
        return sortedProducts.subList(0, Math.min(3, sortedProducts.size()));

    }






}
