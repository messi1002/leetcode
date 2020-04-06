// 1.用被删除节点的前驱节点(是其左子树中的最大值所在的节点)替换被删除的节点
// 返回以root为根节点的二分搜索树中的最大节点
public TreeNode findMax(TreeNode root) {
    if (root == null) {
        return null;
    }
    if (root.right == null) {
        return root;
    }
    return findMax(root.right);
}

public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) {
        return null;
    }
    if (key < root.val) {
        root.left = deleteNode(root.left, key);
    }
    else if (key > root.val) {
        root.right = deleteNode(root.right, key);
    }
    else {
        if (root.left == null) {
            return root.right;
        }
        else if (root.right == null) {
            return root.left;
        }
        else {
            // 此时root是被删除的节点
            // maxNode(node)是被删除节点的前驱节点
            TreeNode maxNode = findMax(root.left);
            // 将被删除节点的前驱节点放在被删除节点的位置
            TreeNode node = new TreeNode(maxNode.val);
            // 删除原来的前驱节点
            node.left = deleteNode(root.left, maxNode.val);
            node.right = root.right;
            return node;
        }
    }
    return root;
}

// 2.用被删除节点的后继节点(是其右子树中的最小值所在的节点)替换被删除的节点
// 返回以root为根节点的二分搜索树中的最小节点
public TreeNode findMin(TreeNode root) {
    if (root == null) {
        return null;
    }
    if (root.left == null) {
        return root;
    }
    return findMin(root.left);
}

public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) {
        return null;
    }
    if (key < root.val) {
        root.left = deleteNode(root.left, key);
    }
    else if (key > root.val) {
        root.right = deleteNode(root.right, key);
    }
    else {
        if (root.left == null) {
            return root.right;
        }
        else if (root.right == null) {
            return root.left;
        }
        else {
            // 此时root是被删除的节点
            // minNode(node)是被删除节点的后继节点
            TreeNode minNode = findMin(root.right);
            // 将被删除节点的后继节点放在被删除节点的位置
            TreeNode node = new TreeNode(minNode.val);
            node.left = root.left;
            // 删除原来的后继节点
            node.right = deleteNode(root.right, minNode.val);
            return node;
        }
    }
    return root;
}