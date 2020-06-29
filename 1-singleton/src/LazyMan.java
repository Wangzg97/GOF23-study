
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
