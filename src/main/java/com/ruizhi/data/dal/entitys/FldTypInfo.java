package com.ruizhi.data.dal.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字段分级分类表
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FldTypInfo implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 一级业务类型ID
     */
    private Integer firstBnsTypeId;

    /**
     * 一级业务类型名称
     */
    private String firstBnsTypeName;

    /**
     * 二级业务类型ID
     */
    private Integer secondBnsTypeId;

    /**
     * 二级业务类型名称
     */
    private String secondBnsTypeName;

    /**
     * 三级业务类型ID
     */
    private Integer threeBnsTypeId;

    /**
     * 三级业务类型名称
     */
    private String threeBnsTypeName;

    /**
     * 敏感等级
     */
    private Integer senId;

    /**
     * 匹配表达式
     */
    private String prt;

    /**
     * 匹配样本
     */
    private String istNam;


}
