# GOF23-study
设计模式学习

*笔记内容参考自https://space.bilibili.com/95256449?spm_id_from=333.788.b_765f7570696e666f.1*
### 1-单例模式
#### 饿汉式
```java
// 单例模式-饿汉式
// 因为一开始就创建了对象，因此容易造成内存的浪费
public class Hungry {

    private Hungry(){

    }

    private static Hungry hungry = new Hungry();

    public static Hungry getInstance(){
        return hungry;
    }

}
```
#### 懒汉式
```java
// 单例模式--懒汉式
// 单线程下运行无问题
public class LazyMan {
    private LazyMan(){
//        System.out.println(Thread.currentThread().getName() + "ok"); // 输出当前线程状态--多线程并发测试用
    }

    private static LazyMan lazyMan;

    public static LazyMan getInstance(){
        if (lazyMan == null)
            lazyMan = new LazyMan();
        return lazyMan;
    }

    // 多线程并发测试-会出现问题
//    public static void main(String[] args) {
//        for (int i=0;i<10;i++){
//            new Thread(()->{
//                LazyMan.getInstance();
//            }).start();
//        }
//    }
}
```
##### 懒汉式优化
```java
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// 单例模式--懒汉式
// 多线程状态下的优化
public class LazyMan2 {
    private LazyMan2(){
//        System.out.println(Thread.currentThread().getName() + "ok");
    }

    private volatile static LazyMan2 lazyMan2; // 避免指令重排

    // 双重检测锁模式的懒汉式单例--DCL懒汉式
    public static LazyMan2 getInstance(){
        if (lazyMan2 == null){
            synchronized (LazyMan2.class){
                if (lazyMan2 == null)
                    lazyMan2 = new LazyMan2(); // 这个语句本身极端情况下也有问题。
                    // 因为不是原子性操作，分三步：分配内存空间->执行构造方法，初始化对象->对象指向内存空间。有可能发生指令重排的现象。
                    // 因此上面要用volatile
            }
        }
        return lazyMan2;
    }

    // 验证多线程下的安全性
//    public static void main(String[] args) {
//        for (int i=0;i<10;i++){
//            new Thread(()->{
//                lazyMan2.getInstance();
//            }).start();
//        }
//    }

    // 使用反射破解
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        LazyMan2 instance = LazyMan2.getInstance();
        Constructor<LazyMan2> constructor = LazyMan2.class.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        LazyMan2 instance2 = constructor.newInstance();

        // 结果是false
        System.out.println(instance == instance2);

    }

}
```
#### 静态内部类实现单例模式
```java
// 静态内部类实现单例模式
public class Holder {
    private Holder(){

    }

    public static Holder getInstance(){
        return InnerClass.holder;
    }

    public static class InnerClass{
        private static final Holder holder = new Holder();
    }
}
```
