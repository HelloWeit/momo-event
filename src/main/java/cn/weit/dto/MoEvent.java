package cn.weit.dto;

import cn.weit.constant.EventName;
import cn.weit.constant.Module;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author weitong
 */
@Data
public class MoEvent implements Serializable {
    private static final long serialVersionUID = -8511851386590201649L;
    //可以用对象替换，这样事件信息更丰富
    private String operator;
    private Module module;
    private EventName name;
    private Date operateTime;
    private String description;
    private boolean	sync;
    private boolean succeed;
}
