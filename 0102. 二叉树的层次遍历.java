// 1.迭代
public List<List<Integer>> levelOrder(TreeNode root)   {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) {
        return res;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    // 将根节点入队
    queue.add(root);
    while (!queue.isEmpty()) {
        // 使用count分层
        int count = queue.size();
        List<Integer> list = new ArrayList<>();
        while (count-- > 0) {
            // 节点出队
            TreeNode node = queue.poll();
            // 访问该节点
            list.add(node.val);
            // 其左右孩子入队
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        res.add(list);
    }
    return res;
}