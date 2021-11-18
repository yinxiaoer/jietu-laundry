package com.jietu.admin.service.merchant.impl;

import com.jietu.admin.dao.merchant.MerchantImageDao;
import com.jietu.admin.service.system.IUserService;
import com.jietu.admin.dao.merchant.MerchantDao;
import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.service.merchant.IMerchantService;
import com.jietu.admin.utils.Assert;
import com.jietu.admin.utils.Constants;
import com.jietu.admin.utils.PasswordUtil;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.admin.service.system.impl.BaseServiceImpl;
import com.jietu.common.entity.merchant.MerchantImage;
import com.jietu.common.enums.MerchantImageTypes;
import com.jietu.common.enums.MerchantStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商户服务实现类
 * @author: 印修河
 * @date: 2018/12/20 22:45
 */
@Service
public class MerchantServiceImpl extends BaseServiceImpl<Merchant, Long> implements IMerchantService {

    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private MerchantImageDao merchantImageDao;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(Merchant merchant) {
        //为商户创建对应的用户
        UserEntity user = new UserEntity();
        user.setUsername(merchant.getContactMobile());
        user.setPassword(PasswordUtil.encrypting(merchant.getContactMobile(), merchant.getContactMobile()));
        user.setEmail(merchant.getContactMail());
        user.setRealname(merchant.getName());
        user.setAvailable(true);
        user.setPhone(merchant.getContactMobile());
        user.setRoleId(Constants.ROLE_ID_MERCHANT);
        userService.insert(user);
        //设置用户id
        merchant.setUserId(user.getId());
        merchant.setCreateName(SessionUtils.getCurrentUser().getRealname());
        merchant.setStatus(MerchantStatus.CLOSED.getCode());
        merchantDao.insert(merchant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        Merchant merchant = merchantDao.getById(id);
        Assert.notNull(merchant, "上送商户id错误");
        merchantDao.delete(id);
        //删除商户用户
        userService.delete(merchant.getUserId());
    }

    @Override
    public List<MerchantImage> getMerchantImageList(Long merchantId) {
        return merchantImageDao.getMerchantImageList(MerchantImage.builder()
                .merchantId(merchantId)
                .types(MerchantImageTypes.MERCHANT_INFO.getCode())
                .build());
    }

    @Override
    public void saveMerchantImage(MerchantImage merchantImage) {
        merchantImage.setTypes(MerchantImageTypes.MERCHANT_INFO.getCode());
        //默认排序在100
        merchantImage.setSortOrder(100);
        merchantImageDao.insert(merchantImage);
    }

    @Override
    public void deleteMerchantImage(Long id) {
        merchantImageDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMerchantImageSeq(Long srcId, Long tarId, Integer oldIndex, Integer newIndex) {
        merchantImageDao.updateMerchantImageSeq(srcId, newIndex);
        merchantImageDao.updateMerchantImageSeq(tarId, oldIndex);
    }
}
