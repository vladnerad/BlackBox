public class Joystick {

    private int forward,
            backward,
            left,
            right;

    public Joystick(int X, int Y) {
        if(X>120){
            forward = X-125;
            backward = 0;
        } else{
            forward = 0;
            backward = X;
        }

        if(Y>120){
            right = Y-125;
            left = 0;
        } else{
            right = 0;
            left = Y;
        }
    }

    public int getForward() {
        return forward;
    }

    public int getBackward() {
        return backward;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
