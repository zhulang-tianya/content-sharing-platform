package com.content.common.utils;

import java.util.List;

public interface TreeNode<T> {
    
    Long getId();
    
    void setId(Long id);
    
    Long getParentId();
    
    void setParentId(Long parentId);
    
    List<T> getChildren();
    
    void setChildren(List<T> children);
    
    Integer getSort();
    
    void setSort(Integer sort);
    
    Integer getLevel();
    
    void setLevel(Integer level);
}
