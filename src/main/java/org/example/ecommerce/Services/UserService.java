package org.example.ecommerce.Services;


import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Model.User;
import org.springframework.core.PriorityOrdered;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    ArrayList<User> users = new ArrayList<>();
    private final MerchantService merchantService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean addUser(User user) {
        for (User u : users) {
            if(u.getId().equals(user.getId())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public boolean updateUser(String id,User user) {
        for (User u : users) {
            if(u.getId().equals(id)) {
                users.set(users.indexOf(u), user);
                return true;
            }
        }
        return false;
    }


    public boolean deleteUser(String id) {
        for (User u : users) {
            if(u.getId().equals(id)) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    public User getUser(String id) {
        for (User u : users) {
            if(u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public int buyproduct(String userid,String productid,String merchantid) {
        Product p=productService.productExists(productid);

        if(p==null) {
            return 0;
        }

        Merchant m =merchantService.getMerchant(merchantid);
        if(m==null) {
            return 1;
        }

        User user=getUser(userid);
        if(user==null) {
            return 2;
        }

        MerchantStock merchantStock=merchantStockService.getMerchantStock(productid,merchantid);
        if(merchantStock==null) {
            return 3;
        }
        if(merchantStock.getStock()==0)
            return 4;
        if(user.getBalance()<p.getPrice())
            return 5;
        merchantStock.setStock(merchantStock.getStock()-1);
        p.setSalesCount(p.getSalesCount()+1);//new attrbute
        user.setBalance(user.getBalance()-p.getPrice());

        return 6;
    }

    public boolean addBalance(String userid,double price) {
        User user=getUser(userid);
        if(user==null) {
            return false;
        }
        user.setBalance(user.getBalance()+price);
        return true;
    }

    public ArrayList<User> getAdminUsers() {
        ArrayList<User> adminUsers = new ArrayList<>();
        for(User u : users) {
            if(u.getRole().equals("admin")) {
                adminUsers.add(u);
            }
        }
        return adminUsers;
    }

    public  int addProductToCart(String userid,String productid,String merchantid) {
        Product p=productService.productExists(productid);

        if(p==null) {
            return 0;
        }

        Merchant m =merchantService.getMerchant(merchantid);
        if(m==null) {
            return 1;
        }

        User user=getUser(userid);
        if(user==null) {
            return 2;
        }

        MerchantStock merchantStock=merchantStockService.getMerchantStock(productid,merchantid);
        if(merchantStock==null) {
            return 3;
        }
        if(merchantStock.getStock()==0)
            return 4;

        user.getCart().add(p);
        return 5;
    }

    public boolean deleteProductFromCart(String userid,String productid) {
        for(User u : users) {
           if(u.getId().equals(userid)) {
               for(Product p : u.getCart()) {
                   if(p.getId().equals(productid)) {
                       u.getCart().remove(p);
                       return true;
                   }
               }
           }
        }
        return false;
    }

    public  ArrayList<Product> getUserCart(String userid) {

        User user=getUser(userid);
        if(user==null) {
            return null;
        }
        return user.getCart();
    }
    public int applyDiscount(String userid,int discount){
        User user=getUser(userid);
        if(user==null) {
            return 0;
        }
        if(!user.getRole().equalsIgnoreCase("admin")) {
            return 1;
        }

        if(productService.getProducts().isEmpty())
            return 2;

        for(Product p:productService.getProducts()){
            double newPrice=p.getPrice()*discount/100;
            p.setPrice(p.getPrice()-newPrice);
        }
        return 4;
    }

    public double priceAmountLimitForDiscount(String userid){
        if(calculateTotalPrice(userid)>500){
            return calculateTotalPrice(userid)-(calculateTotalPrice(userid)*20/100);
        }
        return calculateTotalPrice(userid);
    }

    public Double calculateTotalPrice(String userid) {

        User user=getUser(userid);
        if(user==null) {
            return null;
        }
        if(user.getCart().isEmpty())
            return 0.0;
        double total=0.0;
        for(Product p:user.getCart()) {
            total+=p.getPrice();
        }
        return total;
    }

//    public Double calculateTotalPrice(String userid) {
//
//        User user=getUser(userid);
//        if(user==null) {
//            return 0.0;
//        }
//        if(user.getCart().isEmpty())
//            return null;
//        double total=0.0;
//        for(Product p:user.getCart()) {
//            total+=p.getPrice();
//        }
//        if(total>=500){
//            return total-(total*20/100);
//        }
//        return total;
//    }




}
