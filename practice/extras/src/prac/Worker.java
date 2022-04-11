package prac;

public class Worker {
    public static void main(String[] args) {
        Animal animal=new Animal(); // only animal methods
        Animal dog =new Dog();//only method overridden by dog of animals
        Dog orgDog=new Dog();//all dog methods

        animal.voice();
        dog.voice();
        dog.eat();
        orgDog.voice();
        orgDog.bark();
        orgDog.eat();
    }
}
