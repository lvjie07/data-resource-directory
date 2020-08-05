package com.ruizhi.data.dal.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 被关联结果表
 * </p>
 *
 * @author lvjie
 * @since 2020-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RtlTbl implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主表主键
     */
    private Integer relResultId;

    /**
     * 关联主表名称
     */
    private String tblName;

    /**
     * 字段名称
     */
    private String rtlFldName;

    /**
     * 字段ID
     */
    private Integer rtlFldId;

    /**
     * 被关联表名称
     */
    private String relTblName;

    /**
     * 关联字段名称
     */
    private String rtlTblRtlName;

    /**
     * 关联字段ID
     */
    private Integer relFldId;

    /**
     * 表格类型
     */
    private String tblTyp;

    /**
     * 关系率
     */
    private String rtlRate;

    /**
     * 确认情况
     */
    private String cfmTyp;


}
