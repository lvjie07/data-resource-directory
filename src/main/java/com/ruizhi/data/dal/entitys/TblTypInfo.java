package com.ruizhi.data.dal.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 业务类型表
 * </p>
 *
 * @author lvjie
 * @since 2020-07-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TblTypInfo implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 一级业务类型
     */
    private String tblBnsTypOne;

    /**
     * 二级业务类型
     */
    private String tblBnsTypTwo;

    /**
     * 三级业务类型
     */
    private String tblBnsTypThree;

    /**
     * 四级业务类型
     */
    private String tblBnsTypFour;


}
