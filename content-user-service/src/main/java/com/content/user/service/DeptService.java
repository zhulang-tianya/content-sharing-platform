package com.content.user.service;

import com.content.entity.Dept;

import java.util.List;

/**
 * 部门服务接口
 */
public interface DeptService {

    /**
     * 根据ID查询部门
     */
    Dept getById(Long id);

    /**
     * 查询所有部门
     */
    List<Dept> listAll();

    /**
     * 查询部门树
     */
    List<Dept> treeDepts();

    /**
     * 新增部门
     */
    boolean save(Dept dept);

    /**
     * 更新部门
     */
    boolean update(Dept dept);

    /**
     * 删除部门
     */
    boolean delete(Long id);

    /**
     * 修改部门状态
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 检查部门名称是否存在
     */
    boolean checkNameExists(String name, Long parentId, Long tenantId);
}
