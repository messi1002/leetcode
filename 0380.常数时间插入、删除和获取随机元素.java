// 1.ArrayList+Map
Map<Integer, Integer> map;
ArrayList<Integer> list;
Random rand = new Random();

public RandomizedSet() {
    // 键:存储随机元素、值:存储随机元素在list集合中的位置(索引)
    // 使用map是为了用O(1)的时间复杂度找到随机元素在list集合中的位置
    map = new HashMap<>();
    // 使用list是为了可以随机返回集合中的一个数
    // 将随机元素保存在list集合中
    list = new ArrayList<>();
}

public boolean insert(int val) {
    if (!map.containsKey(val)) {
        // 插入随机元素
        map.put(val, list.size());
        list.add(list.size(), val);
        return true;
    }
    return false;
}

public boolean remove(int val) {
    if (map.containsKey(val)) {
        // 获取随机元素在list集合中的索引
        int pos = map.get(val);
        // 获取list集合中最后一个随机元素的值
        int value = list.get(list.size() - 1);
        // 将list集合中的最后一个随机元素移到被删除随机元素的位置(覆盖)
        list.set(pos, value);
        list.remove(list.size() - 1);
        // 更新map中原最后一个随机元素的位置
        map.put(value, pos);
        // 删除map中存储的被删除随机元素
        map.remove(val);
        return true;
    }
    return false;
}

public int getRandom() {
    // 随机返回集合中的一个数
    return list.get(rand.nextInt(list.size()));
}