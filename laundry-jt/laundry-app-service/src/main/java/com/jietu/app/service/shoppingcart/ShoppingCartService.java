package com.jietu.app.service.shoppingcart;

import com.jietu.app.dao.shoppingcart.ShoppingCartDao;
import com.jietu.app.entity.shoppingcart.ShoppingCart;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.common.entity.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车service
 *
 * @author: lzm
 * @date: 2018/1/10 21:27
 */
@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    /**
     * 新增衣物
     * @param shoppingCart
     * @return
     */
    public List<ShoppingCart> addItemsInShoppingCart(ShoppingCart shoppingCart){
        Customer customer = CustomerUtils.getCurrentCustomer();
        shoppingCart.setCustomerId(customer.getId());
        ShoppingCart checkCart = shoppingCartDao.getItemByGoodsId(shoppingCart);

        if(checkCart == null){
            shoppingCart.setNumber(1);
            shoppingCartDao.insertItem(shoppingCart);
        }else{
            checkCart.setNumber(checkCart.getNumber()+1);
            //前端直接返回数量即可
            shoppingCartDao.updateItemByGoodsId(checkCart);
        }

        return shoppingCartDao.showList(shoppingCart);
    }


    /**
     * 显示某商户ID下的衣物list
     * @param shoppingCart
     * @return
     */
    public List<ShoppingCart> showListByMerchantId(ShoppingCart shoppingCart){
        Customer customer = CustomerUtils.getCurrentCustomer();
        shoppingCart.setCustomerId(customer.getId());
        return shoppingCartDao.showList(shoppingCart);
    }


    /**
     * 清空客户ID在某商户ID下的衣物list
     * @param shoppingCart
     * @return
     */
    public List<ShoppingCart> removeItemsInShoppingCart(ShoppingCart shoppingCart){
        Customer customer = CustomerUtils.getCurrentCustomer();
        shoppingCart.setCustomerId(customer.getId());
        shoppingCartDao.deleteItem(shoppingCart);
        return shoppingCartDao.showList(shoppingCart);
    }

    /**
     * 清空客户ID在某商户ID下的某一项衣物
     * @param shoppingCart
     * @return
     */
    public List<ShoppingCart> deleteSingleItemInShoppingCart(ShoppingCart shoppingCart){
        Customer customer = CustomerUtils.getCurrentCustomer();
        shoppingCart.setCustomerId(customer.getId());
        shoppingCartDao.deleteSingleItem(shoppingCart);
        return shoppingCartDao.showList(shoppingCart);
    }

    /**
     * 更新客户ID在某商户ID下的某一项衣物数量
     * @param shoppingCart
     * @return
     */
    public List<ShoppingCart> updateItemsInShoppingCart(ShoppingCart shoppingCart){
        Customer customer = CustomerUtils.getCurrentCustomer();
        shoppingCart.setCustomerId(customer.getId());

        if(0==shoppingCart.getNumber()){
            shoppingCartDao.deleteSingleItem(shoppingCart);
        }else{
            shoppingCartDao.updateItemByGoodsId(shoppingCart);
        }
        return shoppingCartDao.showList(shoppingCart);
    }


    /**
     * 统计购物车金额
     * @param merchantId 商户id
     * @return
     */
    public BigDecimal countShoppingCartAmount(Long merchantId){
        Customer customer = CustomerUtils.getCurrentCustomer();
        return shoppingCartDao.countShoppingCartAmount(customer.getId(), merchantId);
    }

}
