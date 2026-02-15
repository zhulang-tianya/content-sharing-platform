package com.content.user.service.impl;

import com.content.common.exception.BusinessException;
import com.content.entity.Dept;
import com.content.user.mapper.DeptMapper;
import com.content.user.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptMapper;

    @Override
    public Dept getById(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<Dept> listAll() {
        return deptMapper.selectAllDepts();
    }

    @Override
    public List<Dept> treeDepts() {
        List<Dept> allDepts = listAll();
        return buildDeptTree(allDepts, 0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Dept dept) {
        if (checkNameExists(dept.getName(), dept.getParentId(), dept.getTenantId())) {
            throw new BusinessException("部门名称已存在");
        }
        
        Dept parent = null;
        if (dept.getParentId() != null && dept.getParentId() > 0) {
            parent = deptMapper.selectById(dept.getParentId());
            if (parent == null) {
                throw new BusinessException("父部门不存在");
            }
        }
        
        if (parent != null) {
            dept.setAncestors(parent.getAncestors() + "," + parent.getId());
        } else {
            dept.setAncestors("0");
        }
        
        dept.setDeleted(0);
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.insert(dept) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Dept dept) {
        Dept existing = deptMapper.selectById(dept.getId());
        if (existing == null) {
            throw new BusinessException("部门不存在");
        }
        
        if (!existing.getName().equals(dept.getName()) && 
            checkNameExists(dept.getName(), dept.getParentId(), dept.getTenantId())) {
            throw new BusinessException("部门名称已存在");
        }
        
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.updateById(dept) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        Dept dept = deptMapper.selectById(id);
        if (dept == null) {
            throw new BusinessException("部门不存在");
        }
        
        int childCount = deptMapper.countByParentId(id);
        if (childCount > 0) {
            throw new BusinessException("存在子部门，不能删除");
        }
        
        int userCount = deptMapper.countUsersByDeptId(id);
        if (userCount > 0) {
            throw new BusinessException("部门存在用户，不能删除");
        }
        
        dept.setDeleted(1);
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.updateById(dept) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        Dept dept = deptMapper.selectById(id);
        if (dept == null) {
            throw new BusinessException("部门不存在");
        }
        dept.setStatus(status);
        dept.setUpdateTime(LocalDateTime.now());
        return deptMapper.updateById(dept) > 0;
    }

    @Override
    public boolean checkNameExists(String name, Long parentId, Long tenantId) {
        return false;
    }

    private List<Dept> buildDeptTree(List<Dept> depts, Long parentId) {
        List<Dept> result = new ArrayList<>();
        Map<Long, List<Dept>> groupMap = depts.stream()
            .collect(Collectors.groupingBy(Dept::getParentId));
        
        List<Dept> children = groupMap.get(parentId);
        if (children == null) {
            return result;
        }
        
        for (Dept dept : children) {
            dept.setChildren(buildDeptTree(depts, dept.getId()));
            result.add(dept);
        }
        return result;
    }
}
