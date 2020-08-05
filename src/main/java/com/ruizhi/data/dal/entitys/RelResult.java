package com.ruizhi.data.dal.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 关联结果表
 * </p>
 *
 * @author lvjie
 * @since 2020-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RelResult implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务id
     */
    private Integer flwId;

    /**
     * 关联主表名称
     */
    private String tblName;

    /**
     * 关联字段数
     */
    private Integer rtlFldNum;

    /**
     * 关联表格数量
     */
    private Integer rtlTblNum;

    /**
     * 表格类型
     */
    private String tblTyp;

    /**
     * 确认情况
     */
    private String cfmTyp;


    /****************新加字段***********************/
    /**
     * 被关联结果集
     */
    @TableField(exist = false)
    private List<RtlTbl> rtlTblList;


}
