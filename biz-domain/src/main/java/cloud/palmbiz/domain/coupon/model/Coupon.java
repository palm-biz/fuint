package cloud.palmbiz.domain.coupon.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 卡券聚合根
 * 封装卡券的核心业务逻辑
 *
 * @author DDD Refactoring
 */
@Getter
public class Coupon {

    /**
     * 卡券ID
     */
    private Integer id;

    /**
     * 券组ID
     */
    private Integer groupId;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 券类型
     */
    private CouponType type;

    /**
     * 券内容（0满减券、1折扣券）
     */
    private Integer content;

    /**
     * 券名称
     */
    private String name;

    /**
     * 是否允许转赠
     */
    private Boolean isGive;

    /**
     * 获得卡券所消耗积分
     */
    private Integer point;

    /**
     * 适用商品
     */
    private String applyGoods;

    /**
     * 领取码
     */
    private String receiveCode;

    /**
     * 使用专项
     */
    private String useFor;

    /**
     * 过期类型
     */
    private String expireType;

    /**
     * 有效天数
     */
    private Integer expireTime;

    /**
     * 开始有效期
     */
    private Date beginTime;

    /**
     * 结束有效期
     */
    private Date endTime;

    /**
     * 面额
     */
    private CouponAmount amount;

    /**
     * 发放方式
     */
    private String sendWay;

    /**
     * 每次发放数量
     */
    private Integer sendNum;

    /**
     * 发行数量
     */
    private Integer total;

    /**
     * 每人拥有数量限制
     */
    private Integer limitNum;

    /**
     * 不可用日期
     */
    private String exceptTime;

    /**
     * 适用店铺ID
     */
    private String storeIds;

    /**
     * 适用会员等级
     */
    private String gradeIds;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 效果图片
     */
    private String image;

    /**
     * 后台备注
     */
    private String remarks;

    /**
     * 获取券的规则
     */
    private String inRule;

    /**
     * 核销券的规则
     */
    private String outRule;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 最后操作人
     */
    private String operator;

    /**
     * 状态
     */
    private CouponStatus status;

    // ==================== 构造方法 ====================

    private Coupon() {
    }

    /**
     * 创建新卡券
     */
    public static Coupon create(String name, CouponType type, Integer merchantId,
                               BigDecimal amount, Integer total) {
        Coupon coupon = new Coupon();
        coupon.name = name;
        coupon.type = type;
        coupon.merchantId = merchantId;
        coupon.amount = CouponAmount.of(amount);
        coupon.total = total;
        coupon.status = CouponStatus.ACTIVE;
        coupon.isGive = false;
        coupon.sendNum = 1;
        coupon.limitNum = 1;
        Date now = new Date();
        coupon.createTime = now;
        coupon.updateTime = now;
        return coupon;
    }

    /**
     * 从持久化数据重建
     */
    public static Coupon reconstitute(Integer id, Integer groupId, Integer merchantId,
                                     Integer storeId, String type, Integer content, String name,
                                     Boolean isGive, Integer point, String applyGoods,
                                     String receiveCode, String useFor, String expireType,
                                     Integer expireTime, Date beginTime, Date endTime,
                                     BigDecimal amount, String sendWay, Integer sendNum,
                                     Integer total, Integer limitNum, String exceptTime,
                                     String storeIds, String gradeIds, String description,
                                     String image, String remarks, String inRule, String outRule,
                                     Date createTime, Date updateTime, String operator,
                                     String status) {
        Coupon coupon = new Coupon();
        coupon.id = id;
        coupon.groupId = groupId;
        coupon.merchantId = merchantId;
        coupon.storeId = storeId;
        coupon.type = CouponType.fromCode(type);
        coupon.content = content;
        coupon.name = name;
        coupon.isGive = isGive;
        coupon.point = point;
        coupon.applyGoods = applyGoods;
        coupon.receiveCode = receiveCode;
        coupon.useFor = useFor;
        coupon.expireType = expireType;
        coupon.expireTime = expireTime;
        coupon.beginTime = beginTime;
        coupon.endTime = endTime;
        coupon.amount = CouponAmount.of(amount);
        coupon.sendWay = sendWay;
        coupon.sendNum = sendNum;
        coupon.total = total;
        coupon.limitNum = limitNum;
        coupon.exceptTime = exceptTime;
        coupon.storeIds = storeIds;
        coupon.gradeIds = gradeIds;
        coupon.description = description;
        coupon.image = image;
        coupon.remarks = remarks;
        coupon.inRule = inRule;
        coupon.outRule = outRule;
        coupon.createTime = createTime;
        coupon.updateTime = updateTime;
        coupon.operator = operator;
        coupon.status = CouponStatus.fromCode(status);
        return coupon;
    }

    // ==================== 业务方法 ====================

    /**
     * 设置有效期（固定时间段）
     */
    public void setValidPeriod(Date beginTime, Date endTime) {
        if (beginTime == null || endTime == null) {
            throw new IllegalArgumentException("有效期开始时间和结束时间不能为空");
        }
        if (beginTime.after(endTime)) {
            throw new IllegalArgumentException("有效期开始时间不能晚于结束时间");
        }
        this.expireType = "FIX";
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.updateTime = new Date();
    }

    /**
     * 设置有效期（领取后N天）
     */
    public void setValidDays(Integer days) {
        if (days == null || days <= 0) {
            throw new IllegalArgumentException("有效天数必须大于0");
        }
        this.expireType = "FLEX";
        this.expireTime = days;
        this.updateTime = new Date();
    }

    /**
     * 是否在有效期内
     */
    public boolean isInValidPeriod() {
        Date now = new Date();
        if ("FIX".equals(expireType)) {
            return beginTime != null && endTime != null
                    && !now.before(beginTime) && !now.after(endTime);
        }
        return true;
    }

    /**
     * 是否已过期
     */
    public boolean isExpired() {
        if ("FIX".equals(expireType)) {
            Date now = new Date();
            return endTime != null && now.after(endTime);
        }
        return false;
    }

    /**
     * 设置适用商品
     */
    public void setApplicableGoods(String applyGoods) {
        this.applyGoods = applyGoods;
        this.updateTime = new Date();
    }

    /**
     * 设置适用店铺
     */
    public void setApplicableStores(String storeIds) {
        this.storeIds = storeIds;
        this.updateTime = new Date();
    }

    /**
     * 设置适用会员等级
     */
    public void setApplicableGrades(String gradeIds) {
        this.gradeIds = gradeIds;
        this.updateTime = new Date();
    }

    /**
     * 设置每人限领数量
     */
    public void setLimitNum(Integer limitNum) {
        if (limitNum != null && limitNum < 0) {
            throw new IllegalArgumentException("限领数量不能为负数");
        }
        this.limitNum = limitNum;
        this.updateTime = new Date();
    }

    /**
     * 设置发行总量
     */
    public void setTotal(Integer total) {
        if (total != null && total < 0) {
            throw new IllegalArgumentException("发行总量不能为负数");
        }
        this.total = total;
        this.updateTime = new Date();
    }

    /**
     * 设置领取码
     */
    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
        this.updateTime = new Date();
    }

    /**
     * 设置是否允许转赠
     */
    public void setIsGive(Boolean isGive) {
        this.isGive = isGive;
        this.updateTime = new Date();
    }

    /**
     * 激活
     */
    public void activate(String operator) {
        this.status = CouponStatus.ACTIVE;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 禁用
     */
    public void disable(String operator) {
        this.status = CouponStatus.DISABLED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 删除
     */
    public void delete(String operator) {
        this.status = CouponStatus.DELETED;
        this.operator = operator;
        this.updateTime = new Date();
    }

    /**
     * 是否可用
     */
    public boolean isAvailable() {
        return status.isAvailable() && isInValidPeriod();
    }

    /**
     * 是否为优惠券
     */
    public boolean isCoupon() {
        return type.isCoupon();
    }

    /**
     * 是否为储值卡
     */
    public boolean isPrestore() {
        return type.isPrestore();
    }

    /**
     * 是否为计次卡
     */
    public boolean isTimer() {
        return type.isTimer();
    }

    /**
     * 是否为满减券
     */
    public boolean isFullReduction() {
        return content != null && content == 0;
    }

    /**
     * 是否为折扣券
     */
    public boolean isDiscount() {
        return content != null && content == 1;
    }

    /**
     * 获取面额
     */
    public BigDecimal getAmountValue() {
        return amount.toBigDecimal();
    }

    /**
     * 获取类型码
     */
    public String getTypeCode() {
        return type.getCode();
    }

    /**
     * 获取状态码
     */
    public String getStatusCode() {
        return status.getCode();
    }

    /**
     * 设置卡券信息
     */
    public void setInfo(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.updateTime = new Date();
    }

    /**
     * 设置使用规则
     */
    public void setRules(String inRule, String outRule) {
        this.inRule = inRule;
        this.outRule = outRule;
        this.updateTime = new Date();
    }

    /**
     * 设置备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
        this.updateTime = new Date();
    }
}
