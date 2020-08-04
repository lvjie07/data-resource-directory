package com.ruizhi.data.dal.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 级联分类管理
 * </p>
 *
 * @author lvjie
 * @since 2020-07-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CascadeSortInfo implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分级名称
     */
    private String levName;

    /**
     * 父ID
     */
    private Integer parentId;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 全路径
     */
    private String path;


}
