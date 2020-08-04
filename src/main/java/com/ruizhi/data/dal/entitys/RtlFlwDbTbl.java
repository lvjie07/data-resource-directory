package com.ruizhi.data.dal.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 采集结果数据源库库表
 * </p>
 *
 * @author lvjie
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RtlFlwDbTbl implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据源库id
     */
    private Integer srcId;

    /**
     * 表格名称
     */
    private String tblName;

    /**
     * 表格注释
     */
    private String tblCmt;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 物理规模
     */
    private String psySize;

    /**
     * 表格行数
     */
    private String dataNum;

    /**
     * 列数量
     */
    private String fldNum;

    /**
     * 包含时间数量
     */
    private String fldTimeNum;

    /**
     * 首个时间序列位置
     */
    private String frtTime;

    /**
     * 字符串列数量
     */
    private String fldStrNum;

    /**
     * 数值列数量
     */
    private String fldNmlNum;

    /**
     * 主键数量
     */
    private String fldPkNum;

    /**
     * 联合主键最大列数
     */
    private String fldCpkNum;

    /**
     * 外键数量
     */
    private String fldFkNum;

    /**
     * 索引数量
     */
    private String fldIdxNum;

    /**
     * 其他表对应主键
     */
    private String fldFpkNum;

    /**
     * 一级业务类型
     */
    private String wrkFstType;

    /**
     * 二级业务类型
     */
    private String wrkSedType;

    /**
     * 三级业务类型
     */
    private String wrkThdType;

    /**
     * 四级业务类型
     */
    private String wrkForType;

    /**
     * 更新时间
     */
    private Date updTime;


}
