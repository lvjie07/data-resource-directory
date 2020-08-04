package com.ruizhi.data.dal.entitys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 作业记录表
 * </p>
 *
 * @author lvjie
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FlwInfo implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作业名
     */
    private String flwNam;

    /**
     * 数据库类型
     */
    private String srcTyp;

    /**
     * 数据源名称
     */
    private String srcNam;

    /**
     * 抽样行数
     */
    private String smpNum;

    /**
     * 定时种类
     */
    private String tmgTyp;

    /**
     * 执行频率
     */
    private Integer extFqy;

    /**
     * 启动时间
     */
    private Date strTme;

    /**
     * 任务创建人
     */
    private String flwAddName;

    /**
     * 采集状态
     */
    private String flwColTyp;

    /**
     * 创建时间
     */
    private Date flwAddTime;

    /**
     * 分级分类状态
     */
    private String wrkTyp;

    /**
     * 分级分类时间
     */
    private Date wrkTime;

    /**
     * 关联状态
     */
    private String conTyp;



}
