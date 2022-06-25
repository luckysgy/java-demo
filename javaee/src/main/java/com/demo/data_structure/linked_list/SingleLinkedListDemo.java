package com.demo.data_structure.linked_list;

/**
 * @author shenguangyang
 * @date 2022-02-27 16:52
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        // 进行测试
        // 先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        SingleLinkedList singleLinkedList = new SingleLinkedList();
//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);
//        singleLinkedList.add(hero4);

        // 加入按照编号的顺序
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.list();

        // 测试修改节点的代码
        HeroNode newHeroNode = new HeroNode(2, "小卢", "玉麒麟~~");
        singleLinkedList.update(newHeroNode);
        System.out.println("修改之后的链表数据: ");
        singleLinkedList.list();

        // 删除节点
//        singleLinkedList.delete(1);
        singleLinkedList.delete(4);
//        singleLinkedList.delete(2);
        System.out.println("删除之后的链表: ");
        singleLinkedList.list();

        System.out.println("链表长度: " + singleLinkedList.length());
        System.out.println("查找倒数第n个node: " + singleLinkedList.findLastIndexNode(2));
    }
}

/**
 * 单链表
 */
class SingleLinkedList {
    // 因为head节点不能动，因此我们需要一个辅助遍历 temp
    private final HeroNode head = new HeroNode(0, "", "");

    // 添加节点到单向链表
    // 思路，当不考虑编号顺序时
    // 1. 找到当前链表的最后节点
    // 2. 将最后这个节点的next 指向 新的节点
    public void add(HeroNode node) {
        if (node == null) {
            return;
        }
        HeroNode temp = head;
        // 遍历链表，找到最后
        while (temp.next != null) {
            // 如果没有找到最后, 将temp后移
            temp = temp.next;
        }
        // 当退出while循环时，temp就指向了链表的最后
        // 将最后这个节点的next 指向 新的节点
        temp.next = node;
    }

    // 第二种方式在添加英雄时，根据排名将英雄插入到指定位置
    // (如果有这个排名，则添加失败，并给出提示)
    public void addByOrder(HeroNode node) {
        if (node == null) {
            return;
        }
        // 因为头节点不能动，因此我们仍然通过一个辅助指针(变量)来帮助找到添加的位置
        // 因为单链表，因为我们找的temp 是位于 添加位置的前一个节点，否则插入不了
        HeroNode temp = head;
        // flag标志添加的编号是否存在，默认为false
        boolean flag = false;
        // 遍历链表，找到最后
        while (true) {
            // 说明temp已经在链表的最后
            if (temp.next == null) {
                break;
            }
            // 位置找到，就在temp的后面插入
            if (temp.next.no > node.no) {
                break;
            } else if (temp.no == node.no) {
                // 说明希望添加的heroNode的编号已然存在
                flag = true;
                break;
            }
            // 后移，遍历当前链表
            temp = temp.next;
        }
        if (flag) {
            System.out.printf("准备插入的英雄的编号 %d 已经存在了, 不能加入\n", node.no);
        } else {
            // 插入到链表中, temp的后面
            node.next = temp.next;
            temp.next = node;
        }
    }

    // 显示链表[遍历]
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为头节点，不能动，因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;
        // 判断是否到链表最后
        while (temp != null) {
            // 输出节点的信息
            System.out.println(temp);
            // 将temp后移， 一定小心
            temp = temp.next;
        }
    }

    // 修改节点的信息, 根据no编号来修改，即no编号不能改.
    // 说明
    // 1. 根据 newHeroNode 的 no 来修改即可
    public void update(HeroNode newNode) {
        if (newNode == null) {
            return;
        }
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 找到需要修改的节点, 根据no编号
        // 定义一个辅助变量
        HeroNode temp = head.next;
        // 表示是否找到该节点
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == newNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        // 根据flag 判断是否找到要修改的节点
        if (flag) {
            temp.name = newNode.name;
            temp.nickName = newNode.nickName;
        } else {
            System.out.printf("没有找到 编号 %d 的节点，不能修改\n", newNode.no);
        }
    }

    // 删除节点
    // 思路
    // 1. head 不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点
    // 2. 说明我们在比较时，是temp.next.no 和 需要删除的节点的no比较
    public void delete(int no) {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head;
        // 标志是否找到待删除节点的
        boolean flag = false;
        while (true) {
            // 已经到链表的最后
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == no) {
                // 找到的待删除节点的前一个节点temp
                flag = true;
                break;
            }
            // temp后移，遍历
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的 %d 节点不存在\n", no);
        }
    }

    /**
     * 查找单链表中的倒数第k个结点
     *
     * 查找单链表中的倒数第k个结点 【新浪面试题】
     * 思路
     * 1. 编写一个方法，接收head节点，同时接收一个index
     * 2. index 表示是倒数第index个节点
     * 3. 先把链表从头到尾遍历，得到链表的总的长度 getLength
     * 4. 得到size 后，我们从链表的第一个开始遍历 (size-index)个，就可以得到
     * 5. 如果找到了，则返回该节点，否则返回null
     */
    public HeroNode findLastIndexNode(int index) {
        // 判断如果链表为空，返回null
        if (this.head.next == null) {
            System.out.println("链表为空");
            return null;
        }

        // 第一个遍历得到链表的长度(节点个数)
        int size = length();
        // 第二次遍历 size-index 位置，就是我们倒数的第K个节点
        // 先做一个index的校验
        if (index <= 0 || index > size) {
            return null;
        }
        HeroNode cur = this.head.next;
        // 定义给辅助变量， for 循环定位到倒数的index
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 方法：获取到单链表的节点的个数(如果是带头结点的链表，需求不统计头节点)
     * @return 返回的就是有效节点的个数
     */
    public int length() {
        if (this.head.next == null) {
            System.out.println("链表为空");
            return 0;
        }
        // 定义一个辅助的变量, 这里我们没有统计头节点
        HeroNode cur = this.head.next;
        int length = 0;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }
}

//定义HeroNode ， 每个HeroNode 对象就是一个节点
class HeroNode {
    // 英雄编号
    public int no;
    // 英雄名字
    public String name;
    // 英雄昵称
    public String nickName;
    // 指向下一个节点
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickName = nickname;
    }

    // 为了显示方法，我们重写toString
    @Override
    public String toString() {
        return "HeroNode [no=" + no + ", name=" + name + ", nickName=" + nickName + "]";
    }
}