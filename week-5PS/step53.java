public class step53 {

    static class PatientVitals {
        private double[] readings;
        private int count;

        PatientVitals(double[] initialReadings) {
            readings = new double[500];
            count = 0;

            if (initialReadings != null) {
                for (double r : initialReadings)
                    recordReading(r);
            }
        }

        void recordReading(double reading) {
            if (reading <= 0 || reading > 45 || count >= 500)
                return;

            readings[count++] = reading;
        }

        double getAverage() {
            if (count == 0)
                return 0;

            double sum = 0;

            for (int i = 0; i < count; i++)
                sum += readings[i];

            return sum / count;
        }

        double[] getAllReadings() {
            double[] copy = new double[count];

            for (int i = 0; i < count; i++)
                copy[i] = readings[i];

            return copy;
        }
    }

    public static void main(String[] args) {
        PatientVitals v = new PatientVitals(new double[] { 36.5, -2, 37.1 });

        double[] a = v.getAllReadings();

        for (double x : a)
            System.out.print(x + " ");

        System.out.println();
        System.out.println(v.getAverage());

        a[0] = 999;

        System.out.println(v.getAllReadings()[0]);
    }
}