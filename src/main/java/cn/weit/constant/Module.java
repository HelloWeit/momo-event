package cn.weit.constant;

import lombok.Getter;

/**
 * @author weitong
 */
public enum Module {
    /**
     * 登录
     */
    LOGIN("登录"),
    /**
     * 用户管理
     */
    USER("用户管理"),

    ;
    @Getter
    private String name;
    Module(String name) {
        this.name = name;
   }

}
