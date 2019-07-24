package cn.weit.constant;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * @author weitong
 */
@Getter
public class ParamDescription {
    /**
     * 参数名称
     */
    private String name;
    /**
     * 可选属性列表
     */
    private Map<String, String> attributes	= Maps.newHashMap();
    /**
     * 参数说明
     */
    private String description;

    ParamDescription(String name, String description) {
        this.name = name;
        this.description = description;
    }

    ParamDescription(String name, String description, Map<String, String> attributes) {
        this.name = name;
        this.description = description;
        this.attributes = attributes;
    }

}
