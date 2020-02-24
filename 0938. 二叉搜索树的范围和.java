// 明确题意: 求出所有满足 节点值>=L且节点值<=R 的节点的值的和
// 1.递归解法
public int rangeSumBST(TreeNode root, int L, int R) {
    if (root == null) {
        return 0;
    }
    if (root.val < L) {
        // root.val<L，说明其左子树所有的值都小于L，排除这些节点。
        return rangeSumBST(root.right, L, R);
    }
    else if (root.val > R) {
        // root.val>R，说明其右子树所有的值都大于R，排除这些节点。
        return rangeSumBST(root.left, L, R);
    }
    else {
        return root.val + rangeSumBST(root.right, L, R) + rangeSumBST(root.left, L, R);
    }
}

// 2.迭代
public int rangeSumBST(TreeNode root, int L, int R) {
    Stack<TreeNode> stack = new Stack<>();
    int sum = 0;
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        if (node != null) {
            // root.val<L，说明其左子树所有的值都小于L，排除这些节点。
            if (node.val < L) {
                stack.push(node.right);
            }
            // root.val>R，说明其右子树所有的值都大于R，排除这些节点。
            else if (node.val > R) {
                stack.push(node.left);
            }
            else {
                sum += node.val;
                stack.push(node.left);
                stack.push(node.right);
            }
        }
    }
    return sum;
}