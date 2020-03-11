// 1.ArrayList+Map
Map<Integer, Set<Integer>> map;
ArrayList<Integer> list;
Random rand = new Random();

public RandomizedCollection() {
    // 键:存储随机元素、值:存储随机元素在list集合中的位置(索引集合)
    // 使用map是为了用O(1)的时间复杂度找到随机元素在list集合中的位置
    map = new HashMap<>();
    // 使用list是为了可以随机返回集合中的一个数
    // 将随机元素保存在list集合中
    list = new ArrayList<>();
}

public boolean insert(int val) {
    // 插入随机元素
    list.add(list.size(), val);        
    if (!map.containsKey(val)) {
        Set<Integer> set = new HashSet<>();
        set.add(list.size() - 1);
        map.put(val, set);
        return true;
    }
    else {
        Set<Integer> set = map.get(val);
        set.add(list.size() - 1);
        return false;
    }
}

public boolean remove(int val) {
    if (map.containsKey(val) && map.get(val).size() != 0) {
        // 获取随机元素在list集合中的索引集合
        Set set = map.get(val);
        // 获取索引集合中的第一个索引
        int pos = (int) set.iterator().next();
        // 获取list集合中最后一个随机元素的值
        int value = list.get(list.size() - 1);
        // 将list集合中的最后一个随机元素移到被删除随机元素的位置(覆盖)
        list.set(pos, value);
        list.remove(list.size() - 1);
        // 删除map中存储的被删除随机元素
        set.remove(pos);
        if (map.containsKey(value) && map.get(value).size() != 0) {
            // 更新map中原最后一个随机元素的位置
            map.get(value).remove(list.size());
            map.get(value).add(pos);
        }
        return true;
    }
    return false;
}

public int getRandom() {
    // 随机返回集合中的一个数
    return list.get(rand.nextInt(list.size()));
}