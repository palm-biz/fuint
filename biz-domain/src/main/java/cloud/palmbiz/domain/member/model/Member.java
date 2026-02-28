package cloud.palmbiz.domain.member.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员聚合根
 * 封装会员相关的业务规则和行为
 *
 * @author DDD Refactoring
 */
@Getter
public class Member {

    /**
     * 会员ID
     */
    private MemberId memberId;

    /**
     * 会员号
     */
    private MemberNo memberNo;

    /**
     * 手机号
     */
    private Mobile mobile;

    /**
     * 密码
     */
    private MemberPassword password;

    /**
     * 会员名称
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 微信OpenID
     */
    private String openId;

    /**
     * 证件号
     */
    private String idcard;

    /**
     * 等级ID
     */
    private Integer gradeId;

    /**
     * 分组ID
     */
    private Integer groupId;

    /**
     * 会员开始时间
     */
    private Date startTime;

    /**
     * 会员结束时间
     */
    private Date endTime;

    /**
     * 余额
     */
    private MemberBalance balance;

    /**
     * 积分
     */
    private MemberPoint point;

    /**
     * 性别 1:男 0:女
     */
    private Integer sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 车牌号
     */
    private String carNo;

    /**
     * 来源渠道
     */
    private String source;

    /**
     * 地址
     */
    private String address;

    /**
     * 所属商户ID
     */
    private Integer merchantId;

    /**
     * 默认店铺ID
     */
    private Integer storeId;

    /**
     * 是否员工
     */
    private String isStaff;

    /**
     * 状态
     */
    private MemberStatus status;

    /**
     * 备注
     */
    private String description;

    /**
     * 注册IP
     */
    private String ip;

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

    // ==================== 构造方法 ====================

    /**
     * 创建新会员（用于注册）
     */
    public static Member create(
            String memberNo,
            String name,
            String mobile,
            Integer gradeId,
            Integer merchantId,
            Integer storeId,
            String source,
            String ip) {
        Member member = new Member();
        member.memberNo = MemberNo.of(memberNo);
        member.name = name;
        if (mobile != null && !mobile.isEmpty()) {
            member.mobile = Mobile.of(mobile);
        }
        member.gradeId = gradeId;
        member.merchantId = merchantId;
        member.storeId = storeId;
        member.source = source;
        member.ip = ip;
        member.balance = MemberBalance.zero();
        member.point = MemberPoint.zero();
        member.status = MemberStatus.ENABLED;
        member.idcard = "";
        member.isStaff = "N";
        Date now = new Date();
        member.createTime = now;
        member.updateTime = now;
        member.startTime = now;
        return member;
    }

    /**
     * 从持久化数据重建
     */
    public static Member reconstitute(
            Integer id,
            String userNo,
            String avatar,
            Integer groupId,
            String name,
            String openId,
            String mobile,
            String idcard,
            Integer gradeId,
            Date startTime,
            Date endTime,
            BigDecimal balance,
            Integer point,
            Integer sex,
            String birthday,
            String carNo,
            String source,
            String password,
            String salt,
            String address,
            Integer merchantId,
            Integer storeId,
            String isStaff,
            Date createTime,
            Date updateTime,
            String status,
            String description,
            String ip,
            String operator) {
        Member member = new Member();
        member.memberId = MemberId.of(id);
        member.memberNo = MemberNo.of(userNo);
        member.avatar = avatar;
        member.groupId = groupId;
        member.name = name;
        member.openId = openId;
        if (mobile != null && !mobile.isEmpty()) {
            member.mobile = Mobile.of(mobile);
        }
        member.idcard = idcard;
        member.gradeId = gradeId;
        member.startTime = startTime;
        member.endTime = endTime;
        member.balance = MemberBalance.of(balance);
        member.point = MemberPoint.of(point);
        member.sex = sex;
        member.birthday = birthday;
        member.carNo = carNo;
        member.source = source;
        if (password != null && !password.isEmpty() && salt != null && !salt.isEmpty()) {
            member.password = MemberPassword.fromEncrypted(password, salt);
        }
        member.address = address;
        member.merchantId = merchantId;
        member.storeId = storeId;
        member.isStaff = isStaff;
        member.createTime = createTime;
        member.updateTime = updateTime;
        member.status = MemberStatus.fromCode(status);
        member.description = description;
        member.ip = ip;
        member.operator = operator;
        return member;
    }

    // ==================== 业务方法 ====================

    /**
     * 设置密码
     */
    public void setPassword(String plainPassword, String salt) {
        this.password = MemberPassword.fromPlainText(plainPassword, salt);
        this.updateTime = new Date();
    }

    /**
     * 验证密码
     */
    public boolean verifyPassword(String plainPassword) {
        if (this.password == null) {
            return false;
        }
        return this.password.matches(plainPassword);
    }

    /**
     * 增加余额
     */
    public void addBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("充值金额必须大于0");
        }
        this.balance = this.balance.add(amount);
        this.updateTime = new Date();
    }

    /**
     * 扣减余额
     */
    public void deductBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("扣减金额必须大于0");
        }
        this.balance = this.balance.deduct(amount);
        this.updateTime = new Date();
    }

    /**
     * 增加积分
     */
    public void addPoint(Integer points) {
        if (points <= 0) {
            throw new IllegalArgumentException("增加积分必须大于0");
        }
        this.point = this.point.add(points);
        this.updateTime = new Date();
    }

    /**
     * 扣减积分
     */
    public void deductPoint(Integer points) {
        if (points <= 0) {
            throw new IllegalArgumentException("扣减积分必须大于0");
        }
        this.point = this.point.deduct(points);
        this.updateTime = new Date();
    }

    /**
     * 激活会员
     */
    public void activate() {
        if (this.status == MemberStatus.DELETED) {
            throw new IllegalStateException("已删除的会员不能被激活");
        }
        this.status = MemberStatus.ENABLED;
        this.updateTime = new Date();
    }

    /**
     * 禁用会员
     */
    public void disable() {
        this.status = MemberStatus.DISABLED;
        this.updateTime = new Date();
    }

    /**
     * 删除会员（软删除）
     */
    public void delete() {
        this.status = MemberStatus.DELETED;
        this.updateTime = new Date();
    }

    /**
     * 升级会员等级
     */
    public void upgradeGrade(Integer newGradeId, Date endTime) {
        if (newGradeId == null || newGradeId <= 0) {
            throw new IllegalArgumentException("新等级ID不能为空");
        }
        this.gradeId = newGradeId;
        if (endTime != null) {
            this.endTime = endTime;
        }
        this.updateTime = new Date();
    }

    /**
     * 检查会员是否过期
     */
    public boolean isExpired() {
        if (this.endTime == null) {
            return false;
        }
        return this.endTime.before(new Date());
    }

    /**
     * 重置为初始等级（会员过期时调用）
     */
    public void resetToInitGrade(Integer initGradeId) {
        if (initGradeId == null || initGradeId <= 0) {
            throw new IllegalArgumentException("初始等级ID不能为空");
        }
        this.gradeId = initGradeId;
        this.updateTime = new Date();
    }

    /**
     * 更新活跃时间
     */
    public void updateActiveTime(String ip) {
        this.updateTime = new Date();
        if (this.ip == null || this.ip.isEmpty()) {
            this.ip = ip;
        }
    }

    /**
     * 更新基本信息
     */
    public void updateBasicInfo(
            String name,
            String avatar,
            Integer sex,
            String birthday,
            String idcard,
            String carNo,
            String address,
            Integer groupId) {
        if (name != null) {
            this.name = name;
        }
        if (avatar != null) {
            this.avatar = avatar;
        }
        if (sex != null) {
            this.sex = sex;
        }
        if (birthday != null) {
            this.birthday = birthday;
        }
        if (idcard != null) {
            this.idcard = idcard;
        }
        if (carNo != null) {
            this.carNo = carNo;
        }
        if (address != null) {
            this.address = address;
        }
        if (groupId != null) {
            this.groupId = groupId;
        }
        this.updateTime = new Date();
    }

    /**
     * 更新手机号
     */
    public void updateMobile(String newMobile) {
        this.mobile = Mobile.of(newMobile);
        this.updateTime = new Date();
    }

    /**
     * 绑定微信OpenID
     */
    public void bindOpenId(String openId) {
        this.openId = openId;
        this.updateTime = new Date();
    }

    /**
     * 设置店铺
     */
    public void setStore(Integer storeId) {
        this.storeId = storeId;
        this.updateTime = new Date();
    }

    /**
     * 设置分组
     */
    public void setGroup(Integer groupId) {
        this.groupId = groupId;
        this.updateTime = new Date();
    }

    /**
     * 是否为激活状态
     */
    public boolean isActive() {
        return this.status.isActive();
    }

    /**
     * 设置会员ID（持久化后调用）
     */
    public void setMemberId(Integer id) {
        if (this.memberId == null) {
            this.memberId = MemberId.of(id);
        }
    }

    // ==================== Getters for Value Objects ====================

    public Integer getMemberIdValue() {
        return memberId != null ? memberId.getValue() : null;
    }

    public String getMemberNoValue() {
        return memberNo.getValue();
    }

    public String getMobileValue() {
        return mobile != null ? mobile.getValue() : null;
    }

    public String getPasswordValue() {
        return password != null ? password.getEncryptedValue() : null;
    }

    public String getSalt() {
        return password != null ? password.getSalt() : null;
    }

    public BigDecimal getBalanceValue() {
        return balance.toBigDecimal();
    }

    public Integer getPointValue() {
        return point.toInteger();
    }

    public String getStatusCode() {
        return status.getCode();
    }
}
