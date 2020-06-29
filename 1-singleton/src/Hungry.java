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
