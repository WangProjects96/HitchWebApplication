package bottomnav.hitherejoe.com.bottomnavigationsample.algo;

public enum Temperature {
    CHAUD(18.1, 50), DOUX(12.1, 18), FRAIS(6.1, 12), FROID(-5.1, 6), TRESFROID(-50, -5);
    private double min;
    private double max;

    Temperature(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public boolean isOkay(double temp) {
        if (temp <= max) {
            return temp >= min;
        } else
            return false;
    }

    public double getMax() {
        // TODO Auto-generated method stub
        return max;
    }

    public double getMin() {
        // TODO Auto-generated method stub
        return min;
    }

}