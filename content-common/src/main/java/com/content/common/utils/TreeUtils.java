package com.content.common.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {

    public static <T extends TreeNode<T>> List<T> buildTree(List<T> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>();
        }
        List<T> rootNodes = new ArrayList<>();
        for (T node : nodes) {
            if (node.getParentId() == null || node.getParentId() == 0) {
                rootNodes.add(node);
            }
        }
        for (T rootNode : rootNodes) {
            buildChildren(rootNode, nodes);
        }
        return rootNodes;
    }

    private static <T extends TreeNode<T>> void buildChildren(T parentNode, List<T> nodes) {
        for (T node : nodes) {
            if (node.getParentId() != null && node.getParentId().equals(parentNode.getId())) {
                if (parentNode.getChildren() == null) {
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(node);
                buildChildren(node, nodes);
            }
        }
    }

    public static <T extends TreeNode<T>> List<T> buildTree(List<T> nodes, Long parentId) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>();
        }
        List<T> childNodes = new ArrayList<>();
        for (T node : nodes) {
            if (node.getParentId() != null && node.getParentId().equals(parentId)) {
                childNodes.add(node);
                buildChildren(node, nodes);
            }
        }
        return childNodes;
    }

    public static <T extends TreeNode<T>> List<T> flatTree(List<T> treeNodes) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        List<T> flatNodes = new ArrayList<>();
        for (T node : treeNodes) {
            flatNodes.add(node);
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                flatNodes.addAll(flatTree(node.getChildren()));
            }
        }
        return flatNodes;
    }

    public static <T extends TreeNode<T>> T findNodeById(List<T> treeNodes, Long id) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return null;
        }
        for (T node : treeNodes) {
            if (node.getId() != null && node.getId().equals(id)) {
                return node;
            }
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                T foundNode = findNodeById(node.getChildren(), id);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }
        return null;
    }

    public static <T extends TreeNode<T>> List<T> findNodesByParentId(List<T> treeNodes, Long parentId) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (T node : treeNodes) {
            if (node.getParentId() != null && node.getParentId().equals(parentId)) {
                result.add(node);
            }
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                result.addAll(findNodesByParentId(node.getChildren(), parentId));
            }
        }
        return result;
    }

    public static <T extends TreeNode<T>> List<T> findNodesByLevel(List<T> treeNodes, int level) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (T node : treeNodes) {
            if (node.getLevel() == level) {
                result.add(node);
            }
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                result.addAll(findNodesByLevel(node.getChildren(), level));
            }
        }
        return result;
    }

    public static <T extends TreeNode<T>> int getMaxLevel(List<T> treeNodes) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return 0;
        }
        int maxLevel = 0;
        for (T node : treeNodes) {
            if (node.getLevel() > maxLevel) {
                maxLevel = node.getLevel();
            }
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                int childMaxLevel = getMaxLevel(node.getChildren());
                if (childMaxLevel > maxLevel) {
                    maxLevel = childMaxLevel;
                }
            }
        }
        return maxLevel;
    }

    public static <T extends TreeNode<T>> List<T> getPath(List<T> treeNodes, Long id) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        for (T node : treeNodes) {
            if (node.getId() != null && node.getId().equals(id)) {
                List<T> path = new ArrayList<>();
                path.add(node);
                return path;
            }
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                List<T> path = getPath(node.getChildren(), id);
                if (!path.isEmpty()) {
                    path.add(0, node);
                    return path;
                }
            }
        }
        return new ArrayList<>();
    }

    public static <T extends TreeNode<T>> boolean isLeaf(T node) {
        return node.getChildren() == null || node.getChildren().isEmpty();
    }

    public static <T extends TreeNode<T>> boolean isRoot(T node) {
        return node.getParentId() == null || node.getParentId() == 0;
    }

    public static <T extends TreeNode<T>> int getLeafCount(List<T> treeNodes) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return 0;
        }
        int count = 0;
        for (T node : treeNodes) {
            if (isLeaf(node)) {
                count++;
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                count += getLeafCount(node.getChildren());
            }
        }
        return count;
    }

    public static <T extends TreeNode<T>> int getNodeCount(List<T> treeNodes) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return 0;
        }
        int count = 0;
        for (T node : treeNodes) {
            count++;
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                count += getNodeCount(node.getChildren());
            }
        }
        return count;
    }

    public static <T extends TreeNode<T>> List<T> sortTree(List<T> treeNodes) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        treeNodes.sort((o1, o2) -> {
            if (o1.getSort() == null) {
                return -1;
            }
            if (o2.getSort() == null) {
                return 1;
            }
            return o1.getSort() - o2.getSort();
        });
        for (T node : treeNodes) {
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                sortTree(node.getChildren());
            }
        }
        return treeNodes;
    }

    public static <T extends TreeNode<T>> void setLevels(List<T> treeNodes, int level) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return;
        }
        for (T node : treeNodes) {
            node.setLevel(level);
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                setLevels(node.getChildren(), level + 1);
            }
        }
    }

    public static <T extends TreeNode<T>> void setLevels(List<T> treeNodes) {
        setLevels(treeNodes, 1);
    }
}
