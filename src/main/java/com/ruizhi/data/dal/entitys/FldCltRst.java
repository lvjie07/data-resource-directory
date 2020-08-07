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
 * 字段采集结果表
 * </p>
 *
 * @author lvjie
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FldCltRst implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 结果表主键
     */
    private Integer tblId;

    /**
     * 字段英文名
     */
    private String fldNamEn;

    /**
     * 字段中文名
     */
    private String fldNamCh;

    /**
     * 列注释
     */
    private String fldCmt;

    /**
     * 列类型
     */
    private String fldTyp;

    /**
     * 是否时间列
     */
    private String isTime;

    /**
     * 列长度
     */
    private Integer fldLen;

    /**
     * 列位置
     */
    private Integer fldPoi;

    /**
     * 是否列主键
     */
    private String isPky;

    /**
     * 是否唯一列
     */
    private String isUnique;

    /**
     * 是否索引列
     */
    private String isInd;

    /**
     * 是否外键列
     */
    private String isForKey;

    /**
     * 列在外键位置
     */
    private String forKeyPoi;

    /**
     * 来源表名
     */
    private String fromTbl;

    /**
     * 外键对应表格
     */
    private String forKeyTbl;

    /**
     * 业务级别
     */
    private String wrkLvl;

    /**
     * 敏感级别
     */
    private String senName;

    /**
     * 更新时间
     */
    private Date updTime;


}
