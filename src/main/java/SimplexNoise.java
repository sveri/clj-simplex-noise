import java.util.Random;

/**
 * Created by sveri on 27.12.2014.
 */
public class SimplexNoise {
    SimplexNoise_octave[] octaves;
    double[] frequencys;
    double[] amplitudes;

    int largestFeature;
    double persistence;
    int seed;

    public SimplexNoise(int largestFeature,double persistence, int seed){
        this.largestFeature=largestFeature;
        this.persistence=persistence;
        this.seed=seed;

        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves=(int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));

        octaves=new SimplexNoise_octave[numberOfOctaves];
        frequencys=new double[numberOfOctaves];
        amplitudes=new double[numberOfOctaves];

        Random rnd=new Random(seed);

        for(int i=0;i<numberOfOctaves;i++){
            octaves[i]=new SimplexNoise_octave(rnd.nextInt());

            frequencys[i] = Math.pow(2,i);
            amplitudes[i] = Math.pow(persistence,octaves.length-i);




        }

    }


    public double getNoise(int x, int y){

        double result=0;

        for(int i=0;i<octaves.length;i++){
            //double frequency = Math.pow(2,i);
            //double amplitude = Math.pow(persistence,octaves.length-i);

            result=result+octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
        }


        return result;

    }

    public double getNoise(int x,int y, int z){

        double result=0;

        for(int i=0;i<octaves.length;i++){
            double frequency = Math.pow(2,i);
            double amplitude = Math.pow(persistence,octaves.length-i);

            result=result+octaves[i].noise(x/frequency, y/frequency,z/frequency)* amplitude;
        }


        return result;

    }
    public static void main(String[] args) {
        SimplexNoise noise = new SimplexNoise(100,0.1,5000);


        double xStart=0;
        double XEnd=500;
        double yStart=0;
        double yEnd=500;

        int xResolution=200;
        int yResolution=200;

        double[][] result=new double[xResolution][yResolution];

        for(int i=0;i<xResolution;i++){
            for(int j=0;j<yResolution;j++){
                int x=(int)(xStart+i*((XEnd-xStart)/xResolution));
                int y=(int)(yStart+j*((yEnd-yStart)/yResolution));
                result[i][j]=0.5*(1+noise.getNoise(x,y));
            }
        }

        ImageWriter.greyWriteImage(result);
    }
}
