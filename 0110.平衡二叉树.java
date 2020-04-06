// 1.递归函数定义:判断以root节点为根节点的二叉树是否是平衡二叉树
private int recursion(TreeNode root) {
    // 递归终止条件
    if (root == null) {
        return 0;
    }
    // 递归过程
    int left = recursion(root.left);
    int right = recursion(root.right);
    // 不是平衡二叉树(某个节点的左右两个子树的高度差的绝对值超过1)
    if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
        return -1;
    }
    return Math.max(left, right) + 1;
}
public boolean isBalanced(TreeNode root) {
    return recursion(root) != -1;
}