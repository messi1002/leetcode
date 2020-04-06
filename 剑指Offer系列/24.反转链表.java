// 1.迭代
// p: 将当前节点反转(从head上断开并加到q的头部)
// q: 记录反转后的链表
// head: 遍历当前链表
public ListNode reverseList(ListNode head) {
    ListNode p = head, q = null;
    while (head != null) {
        head = head.next;
        p.next = q;
        q = p;
        p = head;
    }
    return q;
}

// 2.递归
// 递归函数定义: 从后向前依次反转head节点和head.next节点的位置。
// q: 记录反转后的链表
public ListNode reverseList(ListNode head) {
    // 递归终止条件        
    if (head == null || head.next == null) {
        return head;
    }
    // 递归过程
    // 先递归到最后一个节点，它是反转后的链表的头。
    ListNode q = reverseList(head.next);
    // 后一个节点指向前一个节点(反转)
    head.next.next = head;
    // 前一个节点取消指向后一个节点(断链)
    head.next = null;
    // 将反转后的子链表返回，递归过程中q指向的节点一直没有变(其是原链表的最后一个节点，反转后的链表的第一个节点)。
    return q;
}