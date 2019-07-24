package cn.weit.constant;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author weitong
 */

public enum EventName {
    /**
     * 事件 修改个人密码
     */
    SSO_MY_CHANGE_PASS("修改密码", "个人中心修改密码") {
        @Override
        public List<ParamDescription> getParamDesc() {
            return this.baseParamDesc();
        }
    },

    ;


    @Getter
    private String name;
    @Getter
    private String description;

    EventName(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract List<ParamDescription> getParamDesc();

    protected List<ParamDescription> baseParamDesc() {
        List<ParamDescription> paramDescriptions = Lists.newArrayList();
        Map<String, String> map = Maps.newHashMap();
        map.put("name", "名称");
        paramDescriptions.add(new ParamDescription("operator", "操作者", map));
        paramDescriptions.add(new ParamDescription("module", "模块"));
        paramDescriptions.add(new ParamDescription("name", "事件名称"));
        paramDescriptions.add(new ParamDescription("operateTime", "操作时间"));
        paramDescriptions.add(new ParamDescription("description", "描述"));
        paramDescriptions.add(new ParamDescription("succeed", "成功/失败"));
        return paramDescriptions;
    }
}
