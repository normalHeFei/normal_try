package trysth.designpattern;

/**
 *
 * @author hefei
 * @date 2017/4/25
 * 命令模式就是对一个已经实现的功能(在命令模式中称为Receiver)进行包装，包装成一个统一的命令对象（ICommand)
 * 除此之外还有一个叫做Invoker的角色，它操作的对象是命令对象，作用就是对具体操作做近一步的封装，使得客户端调用更加友好
 *
 * 优点： 可以构成组合命令，可以对“操作”进行管理和复用
 */
public class CommandTestCase {
    public static void main(String[] args) {
        Invoker invoker = new Invoker(new ConcertCommand(new Receiver()));
        invoker.invoke();
    }
}

class Receiver {
    public void sthAlreadyImplement() {
        System.out.println("一些已有的功能");
    }
}

interface ICommand {
    void execute();
}

class ConcertCommand implements ICommand {
    private Receiver receiver;

    public ConcertCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.sthAlreadyImplement();
    }
}

class Invoker {
    private ICommand command;

    public Invoker(ICommand command) {
        this.command = command;
    }

    public void invoke() {
        System.out.println("自己的一些其他操作");
        command.execute();
    }
}

