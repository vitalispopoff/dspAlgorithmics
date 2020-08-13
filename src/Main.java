import java.util.Arrays;

import static algorithms.processors.ConvolutionMachine.convolutionMachine;
import static algorithms.generators.Generators.generateNoise;
import static algorithms.processors.Normalization.normalize;

public class Main {




//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

	int
		bitDepth = 1,
		signalLength = 12,
		impulseLength = 5,

		signal[] = generateNoise(signalLength, bitDepth),
		impulse[] = generateNoise(impulseLength, bitDepth),
		dry[] = convolutionMachine(signal, impulse),
		nor[] = normalize(dry, bitDepth);

	System.out.println("\n . imp: "+Arrays.toString(impulse));
	System.out.println("\n . sig: "+Arrays.toString(signal));
	System.out.println("\n . dry: "+Arrays.toString(dry));
	System.out.println("\n . nor: "+Arrays.toString(nor));






	}
}