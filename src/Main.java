/**
 * @author Чашников Михаил
 * @version dated 22 june 2017
 */
public class Main {

    public static void main(String[] args){
        first();
        second();
    }

    public static void first(){
        final int size = 10;
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println(System.currentTimeMillis() - a);

        //Проверка содержимого массива
        for (int i = 0; i < 5; i++) {
            System.out.println(arr[i]);
        }
        System.out.println("------------");
        for (int i = size - 5; i < size; i++) {
            System.out.println(arr[i]);
        }
        System.out.println();
    }

    public static void second(){
        final int size = 10;
        final int half = size / 2;
        float[] arr = new float[size];
        float[] arr0 = new float[half];
        float[] arr1 = new float[half];

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, arr0, 0, half);
        System.arraycopy(arr, half, arr1, 0, half);

        MyThread thread0 = new MyThread(arr0, half);
        MyThread thread1 = new MyThread(arr1, half);
        thread0.start();
        thread1.start();

        try{
            thread0.join();
            thread1.join();
        }catch (InterruptedException e){}

        System.arraycopy(arr0, 0, arr, 0, half);
        System.arraycopy(arr1, 0, arr, half, half);

        System.out.println(System.currentTimeMillis() - a);

        //Проверка содержимого массива
        for (int i = 0; i < 5; i++) {
            System.out.println(arr[i]);
        }
        System.out.println("------------");
        for (int i = size - 5; i < size; i++) {
            System.out.println(arr[i]);
        }
    }

}

class MyThread extends Thread {
        float[] arr;
        int size;

        MyThread(float[] arr, int size){
            this.arr = arr;
            this.size = size;
        }

    public void run() {
        try {
            for (int i = 0; i < size; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        } catch(Exception е) {}
    }
}