package org.example.ecommerce.Services;


import lombok.RequiredArgsConstructor;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    ArrayList<MerchantStock> merchantStocks=new ArrayList<>();
    private final ProductService productService;
    private final MerchantService merchantService;

    public ArrayList<MerchantStock> getMerchantStock(){
        return merchantStocks;
    }

    public int addMerchantStock(MerchantStock merchantStock){
        Product p = productService.productExists(merchantStock.getProductid());
        Merchant m=merchantService.getMerchant(merchantStock.getMerchantid());
        MerchantStock merchantStock1=getMerchantStock(merchantStock.getId());

        if(p==null){
            return 0;//no product with this id
        }
        if(m==null){
            return 1;//no merchant with this id
        }
        if(merchantStock1!=null){
            return 2;
        }
        merchantStocks.add(merchantStock);
        return 3;
    }

    public int updateMerchantStock(String id,MerchantStock merchantStock){
        Product p = productService.productExists(merchantStock.getProductid());
        Merchant m=merchantService.getMerchant(merchantStock.getMerchantid());
        MerchantStock merchantStock1=getMerchantStock(id);

        if(p==null){
            return 0;//no product with this id
        }
        if(m==null){
            return 1;//no merchant with this id
        }
        if(merchantStock1==null){
            return 2;
        }
        merchantStocks.set(merchantStocks.indexOf(merchantStock1),merchantStock);
        return 3;
    }

    public boolean deleteMerchantStock(String id){
        if(getMerchantStock(id)==null){
            return false;
        }
        merchantStocks.remove(getMerchantStock(id));
        return true;
    }

    public MerchantStock getMerchantStock(String id){
        for(MerchantStock merchantStock:merchantStocks){
            if(merchantStock.getId().equals(id)){
                return merchantStock;
            }
        }
        return null;
    }

    public MerchantStock getMerchantStock(String productid,String merchantid){
        for(MerchantStock merchantStock:merchantStocks){
            if(merchantStock.getProductid().equals(productid) && merchantStock.getMerchantid().equals(merchantid)){
                return merchantStock;
            }
        }
        return null;
    }

    public int addStock(String productid,String merchanrid,Integer stock){
        Product p=productService.productExists(productid);
        if(p==null){
            return 0;
        }
        Merchant m = merchantService.getMerchant(merchanrid);
        if(m==null){
            return 1;
        }
        MerchantStock merchantStock= getMerchantStock(productid,merchanrid);
        if(merchantStock==null){
            merchantStocks.add(new MerchantStock(Math.random()+"",productid,merchanrid,stock));
        }
        else
            merchantStock.setStock(merchantStock.getStock()+stock);
        return 2;
    }

    //extra

    //get all product that are available
    public ArrayList<Product> getAvailabeProducts(){
        ArrayList<Product> products=new ArrayList<>();

        for(MerchantStock merchantStock:merchantStocks){
            if(merchantStock.getStock()>0) {
                Product p=productService.productExists(merchantStock.getProductid());
                products.add(p);
            }
        }
        return products;
    }

    public ArrayList<Product> getProductBymetchant(String metchantid){

//        Merchant m = merchantService.getMerchant(metchantid);
//        if(!merchanthasStock(metchantid)){
//            return null;
//        }
        ArrayList<Product> products=new ArrayList<>();
        for(MerchantStock merchantStock:merchantStocks){
            if(merchantStock.getMerchantid().equals(metchantid)){
                Product p=productService.productExists(merchantStock.getProductid());
                products.add(p);
            }
        }

        return products;

    }

    //helper
    public boolean merchanthasStock(String metchantid){
        for(MerchantStock merchantStock:merchantStocks){
            if(merchantStock.getMerchantid().equals(metchantid)){
                return true;
            }
        }
        return false;
    }


}
