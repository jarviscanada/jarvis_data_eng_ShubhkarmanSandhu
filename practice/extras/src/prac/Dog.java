package prac;

public class Dog extends Animal {
    public Dog() {
        super();
    }

    @Override
    public void eat() {
        super.eat();
    }

    @Override
    public void voice() {
        System.out.println("Dog class");
    }
    public  void bark(){
        System.out.println("Dog class only");
    }
}
