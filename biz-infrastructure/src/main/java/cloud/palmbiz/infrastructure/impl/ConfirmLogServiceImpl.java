package cloud.palmbiz.infrastructure.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cloud.palmbiz.common.enums.StatusEnum;
import cloud.palmbiz.common.param.ConfirmLogPage;
import cloud.palmbiz.common.service.ConfirmLogService;
import cloud.palmbiz.common.service.CouponService;
import cloud.palmbiz.common.service.MemberService;
import cloud.palmbiz.common.service.StoreService;
import cloud.palmbiz.framework.pagination.PaginationResponse;
import cloud.palmbiz.infrastructure.mapper.WriteOffRecordMapper;
import cloud.palmbiz.infrastructure.model.WriteOffRecord;
import cloud.palmbiz.infrastructure.model.MtCoupon;
import cloud.palmbiz.infrastructure.model.MtStore;
import cloud.palmbiz.infrastructure.model.MtUser;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 核销卡券服务
 */
@Service
@AllArgsConstructor(onConstructor_= {@Lazy})
public class ConfirmLogServiceImpl extends ServiceImpl<WriteOffRecordMapper, WriteOffRecord> implements ConfirmLogService {

    private WriteOffRecordMapper writeOffRecordMapper;

    /**
     * 卡券服务接口
     */
    private CouponService couponService;

    /**
     * 会员服务接口
     */
    private MemberService memberService;

    /**
     * 店铺服务接口
     */
    private StoreService storeService;

    /**
     * 分页查询卡券核销列表
     *
     * @param confirmLogPage
     * @return
     */
    @Override
    public PaginationResponse<ConfirmLogDto> queryConfirmLogListByPagination(ConfirmLogPage confirmLogPage) {
        Page<WriteOffRecord> pageHelper = PageHelper.startPage(confirmLogPage.getPage(), confirmLogPage.getPageSize());
        LambdaQueryWrapper<WriteOffRecord> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.ne(WriteOffRecord::getStatus, StatusEnum.DISABLE.getKey());

        String status = confirmLogPage.getStatus();
        if (StringUtils.isNotBlank(status)) {
            lambdaQueryWrapper.eq(WriteOffRecord::getStatus, status);
        }
        Integer userId = confirmLogPage.getUserId();
        if (userId != null) {
            lambdaQueryWrapper.eq(WriteOffRecord::getUserId, userId);
        }
        Integer couponId = confirmLogPage.getCouponId();
        if (couponId != null && couponId > 0) {
            lambdaQueryWrapper.eq(WriteOffRecord::getCouponId, couponId);
        }
        Integer merchantId = confirmLogPage.getMerchantId();
        if (merchantId != null && merchantId > 0) {
            lambdaQueryWrapper.eq(WriteOffRecord::getMerchantId, merchantId);
        }
        Integer storeId = confirmLogPage.getStoreId();
        if (storeId != null && storeId > 0) {
            lambdaQueryWrapper.eq(WriteOffRecord::getStoreId, storeId);
        }

        lambdaQueryWrapper.orderByDesc(WriteOffRecord::getId);
        List<WriteOffRecord> confirmLogList = writeOffRecordMapper.selectList(lambdaQueryWrapper);
        List<ConfirmLogDto> dataList = new ArrayList<>();

        for (WriteOffRecord log : confirmLogList) {
             MtUser userInfo = memberService.queryMemberById(log.getUserId());
             MtStore storeInfo = storeService.queryStoreById(log.getStoreId());
             MtCoupon couponInfo = couponService.queryCouponById(log.getCouponId());
             ConfirmLogDto item = new ConfirmLogDto();
             item.setId(log.getId());
             item.setCode(log.getCode());
             item.setUserInfo(userInfo);
             item.setStoreInfo(storeInfo);
             item.setCouponInfo(couponInfo);
             item.setUserCouponId(log.getUserCouponId());
             item.setAmount(log.getAmount());
             item.setCreateTime(log.getCreateTime());
             item.setUpdateTime(log.getUpdateTime());
             item.setStatus(log.getStatus());
             item.setRemark(log.getRemark());
             item.setOperator(log.getOperator());
             dataList.add(item);
        }

        PageRequest pageRequest = PageRequest.of(confirmLogPage.getPage(), confirmLogPage.getPageSize());
        PageImpl pageImpl = new PageImpl(dataList, pageRequest, pageHelper.getTotal());
        PaginationResponse<ConfirmLogDto> paginationResponse = new PaginationResponse(pageImpl, ConfirmLogDto.class);
        paginationResponse.setTotalPages(pageHelper.getPages());
        paginationResponse.setTotalElements(pageHelper.getTotal());
        paginationResponse.setContent(dataList);

        return paginationResponse;
    }

    /**
     * 获取卡券（计次卡）核销次数
     * @param userCouponId 会员卡券ID
     * @return
     */
    @Override
    public Long getConfirmNum(Integer userCouponId) {
        if (userCouponId > 0) {
            return writeOffRecordMapper.getConfirmNum(userCouponId);
        } else {
            return 0L;
        }
    }

    /**
     * 获取卡券核销列表
     * @param userCouponId
     * @return
     */
    @Override
    public List<WriteOffRecord> getConfirmList(Integer userCouponId) {
        if (userCouponId == null || userCouponId <= 0) {
            return new ArrayList<>();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("status", StatusEnum.ENABLED.getKey());
        params.put("USER_COUPON_ID", userCouponId.toString());
        return writeOffRecordMapper.selectByMap(params);
    }

    /**
     * 获取卡券核销数量
     * @param merchantId 商户ID
     * @param storeId    店铺ID
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    @Override
    public Long getConfirmCount(Integer merchantId, Integer storeId, Date beginTime, Date endTime) {
        return writeOffRecordMapper.getConfirmLogCount(merchantId, storeId, beginTime, endTime);
    }
}
