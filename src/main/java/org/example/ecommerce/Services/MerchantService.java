package org.example.ecommerce.Services;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {

    ArrayList<Merchant> merchants=new ArrayList<>();


    public ArrayList<Merchant>  getMerchants(){
        return merchants;
    }

    public boolean addMerchant(Merchant merchant){
        for(Merchant m:merchants){
            if(m.getId().equals(merchant.getId())){
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public boolean updateMerchant(String id,Merchant merchant){
        for(Merchant m:merchants){
            if(m.getId().equals(id)){
                merchants.set(merchants.indexOf(m),merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id){
        for(Merchant m:merchants){
            if(m.getId().equals(id)){
                merchants.remove(m);
                return true;
            }
        }
        return false;
    }

    public Merchant getMerchant(String id){
        for(Merchant m:merchants){
            if(m.getId().equals(id)){
                return m;
            }
        }
        return null;
    }



}
