package com.demo;

/**
 * 构造器参数类型
 * @author shenguangyang
 * @date 2022-01-03 10:15
 */
public class ConstructorParamTypeNode {
    /**
     * 参数类型
     */
    private Class<?> type;
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 头节点
     */
    private ConstructorParamTypeNode head;
    /**
     * 下一个node, 如果type为非基本类型就会有下一个
     */
    private ConstructorParamTypeNode next;

    public ConstructorParamTypeNode() {
    }

    private ConstructorParamTypeNode(Class<?> type, String paramName) {
        this.type = type;
        this.paramName = paramName;
    }

    /**
     * 添加 node
     * @param type
     */
    public void addLastNode(Class<?> type, String paramName) {
        // 初始化要加入的节点
        ConstructorParamTypeNode newNode = new ConstructorParamTypeNode(type, paramName);
        if (head == null) {
            head = newNode;
            this.paramName = newNode.paramName;
            this.type = newNode.type;
            return;
        }

        if (next == null) {
            next = newNode;
            next.head = head;
            return;
        }

        ConstructorParamTypeNode temp = next;
        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
        temp.next.head = head;
    }

}
