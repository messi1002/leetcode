// 1.尾递归
public TreeNode insertIntoBST(TreeNode root, int val) {
    // 找到插入位置
    if (root == null) {
        return new TreeNode(val);
    }
    if (val < root.val) {
        root.left = insertIntoBST(root.left, val);
    }
    else {
        root.right = insertIntoBST(root.right, val);
    }
    return root;
}

// 2.迭代
public TreeNode insertIntoBST(TreeNode root, int val) {
    TreeNode node = root;
    while (node != null) {
        if (val < node.val) {
            // 找到插入位置
            if (node.left == null) {
                node.left = new TreeNode(val);
                break;
            }
            node = node.left;
        }
        else {
            // 找到插入位置
            if (node.right == null) {
                node.right = new TreeNode(val);
                break;
            }
            node = node.right;
        }
    }
    return root;
}