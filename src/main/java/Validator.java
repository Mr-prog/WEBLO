public class Validator {

    public static boolean checkX(int x){
        return x < -5 || x > 3;
    }

    public static boolean checkY(float y){
        return y < -3 || y > 5;
    }

    public static boolean checkR(float r){
        return r < 1 || r > 4;
    }
}
