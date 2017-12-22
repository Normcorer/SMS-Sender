package com.zuma.sms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zuma.sms.enums.db.IntToBoolEnum;
import com.zuma.sms.enums.db.IsDeleteEnum;
import com.zuma.sms.enums.db.SendTaskRecordStatusEnum;
import com.zuma.sms.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 发送任务
 */
@Entity
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Data
public class SendTaskRecord implements Cloneable{

    /**
     * 深拷贝.重写Object.clone方法
     * Date/Integer/String 全为值传递,暂不重写该方法
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    /**
     * id
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 任务名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建任务的用户id
     */
    private Long userId;

    /**
     * 通道id
     */
    private Long channelId;

    /**
     * 通道名
     */
    private String channelName;

    /**
     * 号码组id
     */
    private Long numberGroupId;

    /**
     * 号码组名字
     */
    private String numberGroupName;

    /**
     * 短信内容id
     */
    private Long smsContentId;

    /**
     * 短信内容名
     */
    private String smsContentName;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 开启线程数
     */
    private Integer threadCount;

    /**
     * 期望发送时间,默认当前时间
     */
    private Date expectStartTime = new Date();

    /**
     * 期望结束时间
     */
    private Date expectEndTime;

    /**
     * 实际发送时间
     */
    private Date realStartTime;

    /**
     * 实际结束时间
     */
    private Date realEndTime;

    /**
     * 总用时(秒)
     */
    private Integer totalTime;

    /**
     * 状态. 0:等待中;1:运行中;2:成功;-1:部分失败;-2:全部失败;
     */
    private Integer status = SendTaskRecordStatusEnum.WAIT.getCode();

    /**
     * 号码总数
     */
    private Integer numberNum;

    /**
     * 成功数,异步成功
     */
    private Integer successNum;

    /**
     * 失败数,异步失败
     */
    private Integer failedNum;

    /**
     * 未响应数
     */
    private Integer unResponse;

    /**
     * 是否分片处理
     * 默认否
     */
    private Integer isShard = IntToBoolEnum.FALSE.getCode();


    /**2
     * 已操作总数(可能被中断导致后续号码未操作)
     */
    private Integer usedNum;

    /**
     * 失败号码路径,可供下载
     */
    private String errorNumberPath;

    /**
     * 失败信息,可能为空
     */
    private String errorInfo;

    /**
     * 是否被删除. 0:否;1:是
     */
    private Integer isDelete = IsDeleteEnum.NOT_DELETE.getCode();

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    /**
     * 当前任务的DateHourPair 非DB
     */
    @Transient
    @JsonIgnore
    private List<DateHourPair> dateHourPairs;




    /**
     * 时间小时对
     * 将开始时间-结束时间转换
     * 例如 2017-11-11 12:32:21 - 2017-11-11 15:02:34
     * 转换为一个list:
     * 2017-11-11 12:32:21 - 2017-11-11 13:00:00
     * 2017-11-11 13:00:00 - 2017-11-11 14:00:00
     * 2017-11-11 14:00:00 - 2017-11-11 15:00:00
     * 2017-11-11 15:00:00 - 2017-11-11 15:02:34
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DateHourPair{
        private Date startTime;
        private Date endTime;


        /**
         * 计算指定时间是否在该区间内
         * -1: 小于startTime
         * 0: 在范围内
         * 1: 大于endTime
         */
        public int compareRange(Date time) {
            //如果早于开始时间
            if(time.before(startTime))
                return -1;
            //如果晚于结束时间
            if(time.after(endTime))
                return 1;
            //否则就是在范围内
            return 0;
        }

    }

}