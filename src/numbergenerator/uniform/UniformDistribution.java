package numbergenerator.uniform;

import numbergenerator.RandomNumberGenerator;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;

public class UniformDistribution extends RandomNumberGenerator {

    private int lowerBounds;
    private int upperBounds;

    public UniformDistribution(int lowerBounds, int upperBounds){
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
    }

    @Override
    public int sample() {
        UniformIntegerDistribution d = new UniformIntegerDistribution(
                this.lowerBounds,
                this.upperBounds
        );
        return d.sample();
    }
}
