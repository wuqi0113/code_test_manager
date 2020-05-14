package com.chainfuture.code.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
public class SysMenu implements Serializable {
    private static final long serialVersionUID = -5085254110476178291L;

    private Integer  menuId;
    private String   title;
    private String   href;
    private Integer  parentId;
    private String   spread;
    private List<SysMenu> children;

    public SysMenu() {super();
    }

    public SysMenu(Integer menuId, String title, String href, Integer parentId, String spread, List<SysMenu> children) {
        super();
        this.menuId = menuId;
        this.title = title;
        this.href = href;
        this.parentId = parentId;
        this.spread = spread;
        this.children = children;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
                "menuId=" + menuId +
                ", title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", parentId=" + parentId +
                ", spread='" + spread + '\'' +
                ", children=" + children +
                '}';
    }
}
