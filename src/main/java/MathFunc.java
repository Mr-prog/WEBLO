public class MathFunc {

    public static boolean hitCheck(int x, float y, float r){
        return (checkCircle(x, y, r) || checkRectangle(x, y, r) || checkTriangle(x, y, r));
    }

    private static boolean checkCircle(int x, float y, float r){
        return (x <= 0 && y >= 0 && (x * x + y * y <= r * r));
    }

    private static boolean checkRectangle(int x, float y, float r){
        return (x >= 0 && x <= r / 2 && y <= 0 && y >= -r / 2);
    }

    private static boolean checkTriangle(int x, float y, float r){
        return (x <= 0 && y <= 0 && y >= (2 * x / r + r));
    }
}
