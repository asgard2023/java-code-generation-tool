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
 * @Description: 菜单资源管理 实体
 * @author chenjh
 * @Date: 2022-8-6 23:03:15
 * @Company: opendfl
 * @Copyright: 2022 opendfl Inc. All rights reserved.
 */
@Data
@Table(name = "dfl_resource")
@JsonInclude(Include.ALWAYS)
public class DflResourcePo implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 接口uri
     */
    @Column(name = "uri")
    @NotBlank(message = "uri不能为空")
    @Length(message = "uri超出最大长度255限制", max = 255)
    private String uri;

    private String name;
    /**
     * uri_id
     */
    @Column(name = "uri_id")
    private Integer uriId;
    /**
     * 请求类型(GET/POST/PUT)
     */
    @Column(name = "method")
    @Length(message = "method超出最大长度10限制", max = 10)
    private String method;
    /**
     * 资源类型，0接口,1功能
     */
    @Column(name = "res_type")
    private Integer resType;
    /**
     * 是否删除
     */
    @Column(name = "if_del")
    private Integer ifDel;

    private Integer status;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;
    /**
     * 创建人
     */
    @Column(name = "create_user")
    private Integer createUser;
    /**
     * 修改人
     */
    @Column(name = "modify_user")
    private Integer modifyUser;
}