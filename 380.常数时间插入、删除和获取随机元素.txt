1.哈希表(uthash)

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
struct hash {
    int key;
    // 保存key在数组arr中的下标值——便于删除元素。
    int value;
    UT_hash_handle hh; 
};

typedef struct {
    // 数组(存放所有已插入的元素)——便于O(1)时间获取随机元素。
    int* arr;
    // 记录数组中元素的个数。
    int size;
    // 哈希表——便于O(1)时间插入和删除元素。
    struct hash* hashTable; 
} RandomizedSet;

/** Initialize your data structure here. */
RandomizedSet* randomizedSetCreate() {
    RandomizedSet* r = malloc(sizeof(RandomizedSet));
    r->arr = malloc(sizeof(int)*5000);
    r->size = 0;
    r->hashTable = NULL;

    return r;
}

bool randomizedSetInsert(RandomizedSet* obj, int val) {
    struct hash *h; 
    HASH_FIND_INT(obj->hashTable, &val, h);
    
    if (!h) {
        h = malloc(sizeof(struct hash));
        h->key = val;
        // 存入数组、并记录其在数组中存储的下标值——便于删除元素。
        obj->arr[obj->size] = val;
        h->value = obj->size;
        obj->size++;
        HASH_ADD_INT(obj->hashTable, key, h);
        
        return true;
    }
    
    return false;
}

bool randomizedSetRemove(RandomizedSet* obj, int val) {
    struct hash *h1, *h2; 
    HASH_FIND_INT(obj->hashTable, &val, h1);
    
    // 如果存在，则将数组中最后一个元素移到此元素的位置，保证0~obj->size的数组值有效。
    if (h1) {
        obj->arr[h1->value] = obj->arr[obj->size-1]; 
        HASH_FIND_INT(obj->hashTable, &obj->arr[obj->size-1], h2);
        h2->value = h1->value;
        obj->size--;
        HASH_DEL(obj->hashTable, h1);
        
        return true;
    }
    
    return false;
}

// 获取随机元素。
int randomizedSetGetRandom(RandomizedSet* obj) {
    return obj->arr[rand()%obj->size;];
}

void randomizedSetFree(RandomizedSet* obj) {
    free(obj->arr);
    free(obj);
}