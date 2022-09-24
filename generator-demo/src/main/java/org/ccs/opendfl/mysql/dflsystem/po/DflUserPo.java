package org.ccs.opendfl.mysql.dflsystem.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Version V1.0
 * @Description: dfl_user 实体
 * @author chenjh
 * @Date: 2022-8-3 22:44:41
 * @Company: opendfl
 * @Copyright: 2022 opendfl Inc. All rights reserved.
 */
@Data
@Table(name = "dfl_user")
@JsonInclude(Include.ALWAYS)
public class DflUserPo implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Integer id;
    /**
     * 昵称
     */
    @Column(name = "nickname")
    @NotBlank(message = "nickname不能为空")
    @Length(message = "nickname超出最大长度64限制", max = 64)
    private String nickname;
    /**
     * 用户名
     */
    @Column(name = "username")
    @NotBlank(message = "username不能为空")
    @Length(message = "username超出最大长度64限制", max = 64)
    private String username;
    /**
     * 电话
     */
    @Column(name = "telephone")
    @Length(message = "telephone超出最大长度32限制", max = 32)
    private String telephone;
    /**
     * 邮箱
     */
    @Column(name = "email")
    @Length(message = "email超出最大长度64限制", max = 64)
    private String email;
    /**
     * 密码
     */
    @Column(name = "pwd")
    @Length(message = "pwd超出最大长度64限制", max = 64)
    private String pwd;
    /**
     * 是否删除
     */
    @Column(name = "if_del")
    private Integer ifDel;
    /**
     * 状态:是否有效0无效，1有效
     */
    @Column(name = "status")
    private Integer status;
    /**
     * remark
     */
    @Column(name = "remark")
    @Length(message = "remark超出最大长度255限制", max = 255)
    private String remark;
    /**
     * create_time
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * modify_time
     */
    @Column(name = "modify_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;
    /**
     * create_user
     */
    @Column(name = "create_user")
    private Integer createUser;
    /**
     * modify_user
     */
    @Column(name = "modify_user")
    private Integer modifyUser;
    /**
     * register_ip
     */
    @Column(name = "register_ip")
    @Length(message = "registerIp超出最大长度64限制", max = 64)
    private String registerIp;
    /**
     * sys_type
     */
    @Column(name = "sys_type")
    @Length(message = "sysType超出最大长度1限制", max = 1)
    private String sysType;
}