package cloud.palmbiz.application.member.command;

import lombok.Data;

/**
 * 注册会员命令
 *
 * @author DDD Refactoring
 */
@Data
public class RegisterMemberCommand {

    /**
     * 会员号
     */
    private String memberNo;

    /**
     * 会员名称
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 会员等级ID
     */
    private Integer gradeId;

    /**
     * 商户ID
     */
    private Integer merchantId;

    /**
     * 店铺ID
     */
    private Integer storeId;

    /**
     * 来源渠道
     */
    private String source;

    /**
     * 注册IP
     */
    private String ip;

    /**
     * 分享用户ID
     */
    private String shareId;

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
     * OpenID
     */
    private String openId;
}
