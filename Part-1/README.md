#### 1. 如果不缺内存，如何使用一个具有库的语言来实现一种排序算法以表示和排序集合？

涉及到5个JDK集合工具类中的排序方法：

- `Collections.sort(List<T> list)`
- `Arrays.sort(Object[] a)`
- `Arrays.parallelSort(Object[] a)`
- `Arrays.sort(int[] a)`
- `Arrays.parallelSort(int[] a)`

每个方法的默认签名如下：

```java
// Collections.sort(List<T> list)
// 本质上还是使用了Arrays.sort(Object[] a)
public static <T extends Comparable<? super T>> void sort(List<T> list) {
    list.sort(null);
}
```

```java
// Arrays.sort(Object[] a)
public static void sort(Object[] a) {
    if (LegacyMergeSort.userRequested)
        legacyMergeSort(a);
    else
        ComparableTimSort.sort(a, 0, a.length, null, 0, 0);
}
```

```java
// Arrays.parallelSort(Object[] a)
public static <T extends Comparable<? super T>> void parallelSort(T[] a) {
    int n = a.length, p, g;
    if (n <= MIN_ARRAY_SORT_GRAN ||
        (p = ForkJoinPool.getCommonPoolParallelism()) == 1)
        TimSort.sort(a, 0, n, NaturalOrder.INSTANCE, null, 0, 0);
    else
        new ArraysParallelSortHelpers.FJObject.Sorter<T>
        (null, a,
         (T[])Array.newInstance(a.getClass().getComponentType(), n),
         0, n, 0, ((g = n / (p << 2)) <= MIN_ARRAY_SORT_GRAN) ?
         MIN_ARRAY_SORT_GRAN : g, NaturalOrder.INSTANCE).invoke();
}
```

```java
// Arrays.sort(int[] a)
public static void sort(int[] a) {
    DualPivotQuicksort.sort(a, 0, a.length - 1, null, 0, 0);
}
```

```java
// Arrays.parallelSort(int[] a)
public static void parallelSort(int[] a) {
    int n = a.length, p, g;
    if (n <= MIN_ARRAY_SORT_GRAN ||
        (p = ForkJoinPool.getCommonPoolParallelism()) == 1)
        DualPivotQuicksort.sort(a, 0, n - 1, null, 0, 0);
    else
        new ArraysParallelSortHelpers.FJInt.Sorter
        (null, a, new int[n], 0, n, 0,
         ((g = n / (p << 2)) <= MIN_ARRAY_SORT_GRAN) ?
         MIN_ARRAY_SORT_GRAN : g).invoke();
}
```



对相同一份1千万条无序的数据进行排序，耗时如下：(i5 8400)

| 方法                              | 耗时   |
| --------------------------------- | ------ |
| `Collections.sort(List<T> list)`  | 3.724s |
| `Arrays.sort(Object[] a)`         | 3.965s |
| `Arrays.parallelSort(Object[] a)` | 1.813s |
| `Arrays.sort(int[] a)`            | 926ms  |
| `Arrays.parallelSort(int[] a)`    | 263ms  |

由图可得，当使用基本数据类型`int`时，排序耗时为926ms，并行排序仅有264ms。而使用`Integer`对象进行排序时，耗时发生了显著的变化。使用`int`时使用`DualPivotQuicksort`排序算法，使用`Integer`时使用`ComparableTimSort`排序算法。除去两种算法本身不同的性能外，`int`和`Integer`之间的自动开箱和自动装箱也会消耗大量的时间。

具体算法和测试代码[点击跳转](./src/question/Q1.java)

涉及到的基本排序算法有：

- 归并排序
- Tim排序
- 快速排序

todo 具体的排序算法说明，暂时不做说明，有空再更新

参考资料：

- [TimSort的实现](https://mp.weixin.qq.com/s?__biz=MzI2MTY0OTg2Nw==&mid=2247483816&idx=1&sn=079af3d70efcb68efa7400f09decb59c&chksm=ea56650cdd21ec1ace7c8fd168d62feb636e4b32f9a4d90329fe479489d8e7a70e612df8920b&token=2074049324&lang=zh_CN)
- [Java源码解析-DualPivotQuicksort](https://blog.csdn.net/xjyzxx/article/details/18465661)
- [Timsort原理介绍](https://blog.csdn.net/yangzhongblog/article/details/8184707)
- [Timsort排序算法](https://www.zybuluo.com/zero1036/note/618233)



#### 2. 如何使用位逻辑运算（如与、或、移位）来实现位向量？

位向量的作用就是用“位”来存储一个数，以N=1000_0000个数为例，则每一位代表一个数。通常情况下，我们使用数组来存储对应位置的数，例如：`nums[5]=1`说明当前序列中存在数值5。但是当数据量急剧增加或者最大数较大或者序列过于稀疏，当前方式的空间开销非常大且容易造成严重的浪费。

此时可以引入位向量的概念， int型的数有4个字节，也就是32位，那么我们可以用[N/32]个int型数来表示这N个数。（注：[x]表示向上取整。）

a[0]表示第1~32个数（0~31）

a[1]表示第33~64个数（32~63）

…

这样，每当输入一个数字`i`，我们应该先找到该数字在数组的第几个元素中，也就是`a[?]`，然后再确定在这个元素的第几位中。

举个例子来说，比如输入35，那么35/32为1余3，则应该将a[1]的第4位置为1。

```java
public static final int BIT_SIZE = 32;
public static final int SHIFT = 5;
public static final int MASK = 0x1F;
public static final int N = 1000_0000;
public static final int[] arr = new int[1 + N / BIT_SIZE];

// 设置
public static void set(int i) {
    arr[i >> SHIFT] |= (1 << (i & MASK));
}
// 清除
public static void clear(int i) {
    arr[i >> SHIFT] &= ~(1 << (i & MASK));
}
// 测试
public static int test(int i) {
    return arr[i >> SHIFT] & (1 << (i & MASK));
}
```

具体如何使用逻辑运算符实现位向量，查阅参考资料：

> 根据题目的要求，我们不可以用/运算符来设计程序，那除的话我们可以用右移来替代：
>
> `m >> n`，表示m往右移动n位
>
> 输入i，除以32相当于往右移动5位，则`i >> SHIFT`代表i/32得到应该放在数组的第几个元素中，然后要置相应的位置位1了：
>
> 先来看看`1 << (i & MASK)`是什么意思。`i & MASK`相当于取i右移掉的部分，说白了就是取余数。
>
> > 比如35的二进制表示是：… 0010 0011
> >
> > MASK的二进制是0001 1111
> >
> > 两个相与操作得到0 0011
> >
> > 而右移5位，移掉的数是0 0011，换算成10进制是3，正是余数，与上面的操作值相等，都是0 0011。
>
> 因此`1 << (i & MASK)`就变成了1<<3，也就是将1右移3位，变成了1000。
>
> 然后在做一个`|`操作就将a[1]的第4位置1了。
>
> 
>
> 对于clear函数，就是找到位置，然后清零
>
> 对于test函数，就是找到位置，做一个与操作，如果存在这个数，则返回1，不存在的话，因为是`&`操作，所以返回0。

具体算法和测试代码[点击跳转](./src/question/Q2.java)

参考资料：

- [如何使用位逻辑运算来实现位向量的理解](https://www.cnblogs.com/marsdu/p/3181734.html)



#### 3. 运行效率是设计目标的一个重要的组成部分，所得到的程序需要足够高效。在你自己的系统上实现位图排序并度量其运行时间。该时间与系统排序的运行时间以及习题1中的排序运行时间相比如何？假设n为1000_0000，且输入文件包含100_0000个整数。

本题中采用习题1中系统内置的`int`类型排序的并行方案作为对照组，具体测试代码[点击跳转](./src/question/Q3.java)：

结果如下：

- 输入内容：100W个随机整数

| 方法                           | 耗时 |
| ------------------------------ | ---- |
| `Arrays.parallelSort(int[] a)` | 79ms |
| 位排序                         | 4ms  |

- 由于书籍出版时设备性能有限，此处再进行1000W个随机整数进行对照

| 方法                           | 耗时  |
| ------------------------------ | ----- |
| `Arrays.parallelSort(int[] a)` | 283ms |
| 位排序                         | 14ms  |





#### 4. 如果认真考虑了习题3，你将会面对生成小于n且没有重复的k个整数的问题。最简单的方法就是使用前k个正整数。这个极端的数据集合将不会明显地改变位图方法的运行时间，但是可能会歪曲系统排序的运行时间。如何生成位于0至n-1之间的k个不同的随机顺序的随机整数？尽量使你的程序简短且高效。

具体Java算法和测试代码[点击跳转](./src/question/Q4.java)

其中`ThreadLocalRandom.current().nextInt(int origin, int bound)`用于生成[origin, bound)之间的随机整数。

书中答案给的伪代码：下面的代码假定`randint(l, u)`返回`l .. u`中的随机整数。swap函数作用是交换x中的两个元素。

```java
for i = [0, n)
     x[i] = i
for i = [0, k)
     swap(i, randint(i, n-1))
     print x[i]
```



// todo 参考12章取样问题，尤其是习题12.8



#### 5. 那个程序员说他有1MB的可用存储空间，但是我们概要描述的代码需要1.25MB的空间。他可以不费力气地索取到额外的空间。如果1MB空间是严格的边界，你会推荐如何处理呢？你的算法的运行时间又是多少？

注：习题描述的“需要1.25MB的空间”这个计算并不是很严格，对1000W的数据排序需要1000W个位，也就是125W个字节（1字节=8位），换算后约为1.192MB。考虑到书籍的发行时间，在此处做备注即可。关于额外内存空间限制，日常个人PC或者服务器已经不差这一点内存，但是考虑到嵌入式设备等还是值得深思的问题。比如我的Huawei Watch GT2就只有32MB的内存，这种情况下对内存的利用率就显得非常重要。

使用位图表示1000万个数需要1000万个位，或者说125万个字节。考虑到以数字0或1开头的电话号码，我们可以将内存需求降低为100万字节。（注：这种情况适用于作者所在的美国，做选择的时候还是需要考虑到实际的应用场景，比如我国就有数字1开头的电话号码。）

另一种做法是采用两趟算法，首先使用500_0000/8=62_5000个字节的存储空间来排序0~499_9999之间的整数，然后在第二趟排序500_0000~999_9999的整数。k趟算法可以在kn的时间开销和n/k的空间开销完成对最多n个小于n的无重复正整数的排序。



#### 6. 如果那个程序员说的不是每个整数最多出现一次，而是每个整数最多出现时10次，你又如何建议他呢？你的解决方案如何随着可用存储空间总量的变化而变化？

如果每个整数最多出现10次，那么我们就可以使用4位的半字节来统计它出现的次数。利用习题5的答案，我们可以使用1000_0000/2个字节在1趟内完成对整个文件的排序，或使用1000_0000/2k个字节在k趟内完成对整个文件的排序。

- 为什么使用4位？因为4位二进制数最多可以表示0~15共16个数，刚好大于10次。
- 共1000_0000/2个字节？习题3中的算法使用每一位表示一个数，那实际上需要的空间数为`[1 + 1000_0000 / (4 * 8)]`，其中int为4字节，1字节=8位。那么在本题中，每半个字节（4位）表示一个数出现的次数，所需要的空间数增大。即a[0]表示第1~8位的数（0~7），后续依此推类。

具体Java算法和测试代码[点击跳转](./src/question/Q6.java)