package com.ruizhi.data.dal.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lvjie
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DataSourceInfo implements Serializable {


    private static final long serialVersionUID = -4860342015679198394L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据源名称
     */
    private String srcName;

    /**
     * 数据源分类
     */
    private String srcCtl;

    /**
     * 数据库子类型
     */
    private String srcTyp;

    /**
     * IP地址
     */
    private String ipAdr;

    /**
     * 端口
     */
    private String prt;

    /**
     * 实例名
     */
    private String istNam;

    /**
     * 用户名
     */
    private String usr;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 添加方式
     */
    private String addTyp;


}
