package org.example.ecommerce.Services;


import lombok.RequiredArgsConstructor;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Model.User;
import org.springframework.stereotype.Service;

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



}
