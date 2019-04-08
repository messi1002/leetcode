1.哈希表(uthash)

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
struct hash {
    int key;
    // 保存key在数组arr中的下标值——便于删除元素。
    int value[10];
    // 记录相同值的个数。
    int size;
    UT_hash_handle hh; 
};

typedef struct {
    // 数组(存放所有已插入的元素)——便于O(1)时间获取随机元素。
    int* arr;
    // 记录数组中元素的个数。
    int size;
    // 哈希表——便于O(1)时间插入和删除元素。
    struct hash* hashTable; 
} RandomizedCollection;

RandomizedCollection* randomizedCollectionCreate() {
    RandomizedCollection* r = malloc(sizeof(RandomizedCollection));
    r->arr = malloc(sizeof(int)*5000);
    r->size = 0;
    r->hashTable = NULL;
    
    return r;
}

bool randomizedCollectionInsert(RandomizedCollection* obj, int val) {
    struct hash *h; 
    HASH_FIND_INT(obj->hashTable, &val, h);
    
    // 如果val不存在，则添加。
    if (!h) {    
        h = malloc(sizeof(struct hash));
        h->key = val;
        // 存入数组、并记录其在数组中存储的下标值——便于删除元素。
        obj->arr[obj->size] = val;
        h->value[0] = obj->size;
        obj->size++;
        h->size = 1;
        HASH_ADD_INT(obj->hashTable, key, h);
        
        return true;
    }
    // 因为允许重复，所以记录重复次数。
    // 因为每个元素被返回的概率应该与其在集合中的数量呈线性相关，所以必须在数组中保存重复元素。
    else {
        obj->arr[obj->size] = val;
        h->value[h->size] = obj->size;
        obj->size++;
        h->size++;
        
        return false;
    }
}

// O(N)复杂度，暂时没有想到优化方法。
bool randomizedCollectionRemove(RandomizedCollection* obj, int val) {
    struct hash *h1, *h2; 
    HASH_FIND_INT(obj->hashTable, &val, h1);
    
    // 如果存在，则将数组中最后一个元素移到此元素的位置(保证0~obj->size的数组值有效)，并且更新记录最后一个元素下标的obj->value数组。
    // 注意:被删除的元素存在多个时，默认删除数组下标值最大的那个。
    if (h1) {
        obj->arr[h1->value[h1->size-1]] = obj->arr[obj->size-1]; 
        HASH_FIND_INT(obj->hashTable, &obj->arr[obj->size-1], h2);
       
        for (int i = 0; i < h2->size;i++) {
            if (h2->value[i] == obj->size-1) {
                h2->value[i] = h1->value[h1->size-1];
                break;
            }
        }
        
        obj->size--;
        h1->size--;
        
        if (h1->size == 0)
            HASH_DEL(obj->hashTable, h1);

        return true;
    }
    
    return false;
}

// 因为所有插入的元素都保存在数组中，所以随机返回的数字满足题意概率。
int randomizedCollectionGetRandom(RandomizedCollection* obj) {
    return obj->arr[rand()%obj->size];
}

void randomizedCollectionFree(RandomizedCollection* obj) {
    free(obj->arr);
    free(obj);
}