// 1.尾递归
public TreeNode searchBST(TreeNode root, int val) {
    if (root == null) {
        return null;
    }
    if (val == root.val) {
        return root;
    }
    else if (val < root.val) {
        return searchBST(root.left, val);
    }
    else {
        return searchBST(root.right, val);
    }
}

// 2.迭代
public TreeNode searchBST(TreeNode root, int val) {
    while (root != null) {
        if (val == root.val) {
            return root;
        }
        else if (val < root.val) {
            root = root.left;
        }
        else {
            root = root.right;
        }
    }
    return null;
}