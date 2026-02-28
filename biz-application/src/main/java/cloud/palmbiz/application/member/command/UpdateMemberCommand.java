package cloud.palmbiz.application.member.command;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 更新会员命令
 *
 * @author DDD Refactoring
 */
@Data
public class UpdateMemberCommand {

    /**
     * 会员ID
     */
    private Integer memberId;

    /**
     * 会员名称
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 会员号
     */
    private String memberNo;

    /**
     * 会员等级ID
     */
    private Integer gradeId;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 分组ID
     */
    private Integer groupId;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 证件号
     */
    private String idcard;

    /**
     * 车牌号
     */
    private String carNo;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 地址
     */
    private String address;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否修改密码
     */
    private Boolean modifyPassword;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 积分
     */
    private Integer point;
}
