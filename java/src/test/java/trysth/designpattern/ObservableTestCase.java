package trysth.designpattern;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hefei
 * @date 2017/6/6
 * 观察者模式其实主要分为两个角色：观察者和被观察者，被观察者维护了一组监听者列表
 * 当发生需要通知其他系统的操作时，通过回调的方式（通过event对象回传监听者需要的参数）来调用监听者的监听方法
 * 几个重要的角色：
 *      1. observeable: 可被观察的对象，维护一组监听者(例子并没有对监听者的类型做区分，实际应用时，listener可以有不同的类型，对应的observerable 对象应该提供不同类型listener的注册方法)
 *      2. observer：    观察者，监听某种类型的事件，并对其作出相应
 *      3. event:        其实就是对回调所需的参数的封装。
 * 优点： 解耦了被观察者和观察者，使得被观察者依赖于抽象的listener
 */
public class ObservableTestCase {
    public static void main(String[] args) {
        ObservableObj observable = new ObservableObj();
        observable.addListener(event -> {
            System.out.println(event);
            return null;
        });
        observable.someOpsMayCauseOtherSystemOps();
    }

}

class ObservableObj {
    private List<Listener> listeners = new ArrayList<>();

    synchronized public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    synchronized public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public void someOpsMayCauseOtherSystemOps() {
        System.out.println("一些可能触发监听的操作内容");
        //通知监听者
        listeners.forEach(listener -> listener.callback(new Event("需要回传到监听者的一些数据")));
    }
}

interface Listener {
    Object callback(Event event);
}

class Event {
    private Object data;

    public Event(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "data=" + data +
                '}';
    }
}