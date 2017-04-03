package Main;

import java.io.*;

import java.lang.Math;

/**
 * Created by SJ Song on 2017-03-02.
 */
public class Main {

    static double[][] WC = new double[2][7];
    static double[][] WF1 = new double[256][32];
    static double[][] WF2 = new double[32][12];
    static double[] BF1 = new double[32];
    static double[] BF2 = new double[12];
    static double[] BN = new double[2];
    static double[][] sample1 = new double[2][4096];
    static double[][][] fsample = new double[35][2][4096];

    public static void main (String[] arge) throws Exception{


        File fileWC = new File("C:/Users/SJ Song/Dropbox/ML/STAP/toAndroid/WC");
        File fileWF1 = new File("C:/Users/SJ Song/Dropbox/ML/STAP/toAndroid/WF1");
        File fileWF2 = new File("C:/Users/SJ Song/Dropbox/ML/STAP/toAndroid/WF2");
        File fileBF1 = new File("C:/Users/SJ Song/Dropbox/ML/STAP/toAndroid/BF1");
        File fileBF2 = new File("C:/Users/SJ Song/Dropbox/ML/STAP/toAndroid/BF2");
        File fileSample = new File("C:/Users/SJ Song/Dropbox/ML/STAP/toAndroid/Sample3");
        File filefullsample = new File("C:/Users/SJ Song/Dropbox/ML/STAP/toAndroid/fullsample");
        File fileOut = new File("C:/Users/SJ Song/Dropbox/ML/STAP/toAndroid//outfile");

        double[] WC_t = new double[2*7];
        double[] WF1_t = new double[1024*32];
        double[] WF2_t = new double[32*12];
        double[] Sample_t = new double[8192];
        double[] fullsample = new double[8192*35];




        try {
            FileInputStream fWC = new FileInputStream(fileWC);
            FileInputStream fWF1 = new FileInputStream(fileWF1);
            FileInputStream fWF2 = new FileInputStream(fileWF2);
            FileInputStream fBF1 = new FileInputStream(fileBF1);
            FileInputStream fBF2 = new FileInputStream(fileBF2);
            FileInputStream fSample = new FileInputStream(fileSample);
            FileInputStream ffullsample = new FileInputStream(filefullsample);


            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(fWC));

            String temp = "";
            int num = 0;

            while((temp=bufferedReader1.readLine()) != null){
                WC_t[num] = Double.parseDouble(temp);
                num += 1;
            }

            bufferedReader1.close();

            for(int i=0;i<2;i++) {
                for (int q = 0; q < 7; q++) {
                    WC[i][q]=WC_t[i*7+q];
                }
            }

            num = 0;
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(fWF1));

            while((temp=bufferedReader2.readLine()) != null){
                WF1_t[num] = Double.parseDouble(temp);
                num += 1;
            }
            bufferedReader2.close();

            for(int q=0;q<256;q++) {
                for (int p = 0; p < 32; p++) {
                    WF1[q][p]=WF1_t[p*256 + q];
                }
            }

            num = 0;
            BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(fWF2));

            while((temp=bufferedReader3.readLine()) != null){
                WF2_t[num] = Double.parseDouble(temp);
                num += 1;
            }
            bufferedReader3.close();

            for(int q=0;q<32;q++) {
                for (int p = 0; p < 12; p++) {
                    WF2[q][p]=WF2_t[p*32 + q];
                }
            }

            num = 0;
            BufferedReader bufferedReader4 = new BufferedReader(new InputStreamReader(fBF1));

            while((temp=bufferedReader4.readLine()) != null){
                BF1[num] = Double.parseDouble(temp);
                num += 1;
            }
            bufferedReader4.close();


            num = 0;
            BufferedReader bufferedReader5 = new BufferedReader(new InputStreamReader(fBF2));

            while((temp=bufferedReader5.readLine()) != null){
                BF2[num] = Double.parseDouble(temp);
                num += 1;
            }
            bufferedReader5.close();




            num = 0;
            BufferedReader bufferedReader7 = new BufferedReader(new InputStreamReader(fSample));

            while((temp=bufferedReader7.readLine()) != null){
                Sample_t[num] = Double.parseDouble(temp);
                num += 1;
            }
            bufferedReader7.close();

            for(int q=0;q<2;q++) {
                for (int p = 0; p < 4096; p++) {
                    sample1[q][p]=Sample_t[q*4096 + p];
                }
            }

            num = 0;
            BufferedReader bufferedReader8 = new BufferedReader(new InputStreamReader(ffullsample));

            while((temp=bufferedReader8.readLine()) != null){
                fullsample[num] = Double.parseDouble(temp);
                num += 1;
            }
            bufferedReader8.close();

            for(int r=0;r<35;r++){
                for(int q=0;q<2;q++) {
                    for (int p = 0; p < 4096; p++) {
                       fsample[r][q][p] = fullsample[r*8192 +q * 4096 + p];
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<35;i++) {
            double minsig = 0;
            double maxsig = 0;
            double max_can = 0;
            double min_can = 0;
            for (int a = 0; a < 4096; a++) {
                min_can = Math.min(fsample[i][0][a], fsample[i][1][a]);
                max_can = Math.max(fsample[i][0][a], fsample[i][1][a]);
                minsig = Math.min(minsig, min_can);
                maxsig = Math.max(maxsig, max_can);
            }
            for (int a = 0; a < 4096; a++) {
                fsample[i][0][a] = (fsample[i][0][a] - minsig) / (maxsig - minsig);
                fsample[i][1][a] = (fsample[i][1][a] - minsig) / (maxsig - minsig);
            }

            int aaa = Network(fsample[i]);

            System.out.print(String.valueOf(i+1)+ ": " + String.valueOf(aaa) + "\n");
        }
/*
        int fsize = aaa.length;


        System.out.print(String.valueOf(fsize)+"\n");
        FileOutputStream fOut = new FileOutputStream(fileOut);

        int s1 = WF1.length;
        int s2 = WF1[0].length;
        System.out.print(String.valueOf(WF2[0][0]+ "\n"+WF2[1][0]));

        BufferedWriter bufferedWriter1 = new BufferedWriter(new OutputStreamWriter(fOut));
        for(int a=0;a<fsize;a++){

                String fstring = String.valueOf(aaa[a]);
                bufferedWriter1.write(String.valueOf(fstring));
                bufferedWriter1.newLine();

        }
        bufferedWriter1.close();
*/
    }




    public static double[] Conv(double[] input1, double[] input2, double[][]WC){
        int size = input1.length;
        double[] conv_out = new double[size];

        for (int k = 0; k < size; k++) {
            if (k == 0) {
                conv_out[k] = input1[0] * WC[0][3] + input1[1] * WC[0][4] + input1[2] * WC[0][5] + input1[3] * WC[0][6] +
                        input2[0] * WC[1][3] + input2[1] * WC[1][4] + input2[2] * WC[1][5] + input2[3] * WC[1][6];
            } else if (k == 1) {
                conv_out[k] = input1[0] * WC[0][2] + input1[1] * WC[0][3] + input1[2] * WC[0][4] + input1[3] * WC[0][5] + input1[4] * WC[0][6] +
                        input2[0] * WC[1][2] + input2[1] * WC[1][3] + input2[2] * WC[1][4] + input2[3] * WC[1][5] + input2[4] * WC[1][6];
            } else if (k == 2) {
                conv_out[k] = input1[0] * WC[0][1] + input1[1] * WC[0][2] + input1[2] * WC[0][3] + input1[3] * WC[0][4] + input1[4] * WC[0][5] + input1[5] * WC[0][6] +
                        input2[0] * WC[1][1] + input2[1] * WC[1][2] + input2[2] * WC[1][3] + input2[3] * WC[1][4] + input2[4] * WC[1][5] + input2[5] * WC[1][6];
            } else if (k == size-3) {
                conv_out[k] = input1[size-6] * WC[0][0] + input1[size-5] * WC[0][1] + input1[size-4] * WC[0][2] + input1[size-3] * WC[0][3] + input1[size-2] * WC[0][4] + input1[size-1] * WC[0][5] +
                        input2[size-6] * WC[1][0] + input2[size-5] * WC[1][1] + input2[size-4] * WC[1][2] + input2[size-3] * WC[1][3] + input2[size-2] * WC[1][4] + input2[size-1] * WC[1][5];
            } else if (k == size-2) {
                conv_out[k] = input1[size-5] * WC[0][0] + input1[size-4] * WC[0][1] + input1[size-3] * WC[0][2] + input1[size-2] * WC[0][3] + input1[size-1] * WC[0][4] +
                        input2[size-5] * WC[1][0] + input2[size-4] * WC[1][1] + input2[size-3] * WC[1][2] + input2[size-2] * WC[1][3] + input2[size-1] * WC[1][4];
            } else if (k == size-1) {
                conv_out[k] = input1[size-4] * WC[0][0] + input1[size-3] * WC[0][1] + input1[size-2] * WC[0][2] + input1[size-1] * WC[0][3] +
                        input2[size-4] * WC[1][0] + input2[size-3] * WC[1][1] + input2[size-2] * WC[1][2] + input2[size-1] * WC[1][3];
            } else {
                conv_out[k] = input1[k - 3] * WC[0][0] + input1[k - 2] * WC[0][1] + input1[k - 1] * WC[0][2] + input1[k] * WC[0][3] + input1[k + 1] * WC[0][4] + input1[k + 2] * WC[0][5] + input1[k + 3] * WC[0][6] +
                        input2[k - 3] * WC[1][0] + input2[k - 2] * WC[1][1] + input2[k - 1] * WC[1][2] + input2[k] * WC[1][3] + input2[k + 1] * WC[1][4] + input2[k + 2] * WC[1][5] + input2[k + 3] * WC[1][6];
            }
        }
        return conv_out;
    }

    public static double[] Max_Pool(double[] input, int stride){

        int size = input.length;
        double[] out = new double[size/stride];

        for (int j=0; j< size/stride; j++){

            double tmpout = 0;
            for (int i=0; i<stride; i++) {
                if (input[i+j*stride] >= tmpout) {
                    tmpout = input[i+j*stride];
                }
            }
            out[j]=tmpout;
        }

        return out;
    }

    public static double[] Avg_Pool(double[] input, int stride){

        int size = input.length;
        double[] out = new double[size/stride];

        for (int j=0; j< size/stride; j++){

            double tmpout = 0;
            for (int i=0; i<stride; i++) {
                tmpout = tmpout + input[j*stride + i];
            }
            out[j]=tmpout/stride;
        }

        return out;
    }

    public static double[] Batch_Normalize(double[] input, double mean, double var){
        int size = input.length;
        double[] out = new double[size];
        for (int i=0;i<size; i++){
            if(var==0){
                out[i] = (input[i]-mean)/0.0001;
            }
            else {
                out[i] = (input[i]-mean)/Math.sqrt(var);
            }
        }
        return out;
    }

    public static double[] reLU(double[] input){
        int size = input.length;
        double[] out = new double[size];
        for (int i=0;i<size;i++){
            out[i] = Math.max(input[i],0);
        }
        return out;
    }



    public static double[] Fully_Connected(double[] input, double[][] W, double[] B, int outsize){
        int size = input.length; //256
        double[] out = new double[outsize];

        for (int i=0;i<outsize;i++){// i = 32 / j = 256
            out[i] = 0;
            for (int j=0;j<size;j++){

                out[i]=out[i]+(input[j]*W[j][i]);
            }
            out[i]=out[i]+B[i];
        }
        return out;
    }

    public static double[] Soft_Max(double[] input){
        int size = input.length;
        double[] out = new double[size];
        double sum = 0;
        double min =0;
        for (int i=0;i<size;i++){
            if(input[i]<min){
                min=input[i];
            }
        }
        if(min<0){
            min=-min;
        }
        else{
            min=0;
        }
        for (int i=0;i<size;i++){
            sum = sum + input[i] + min;
        }

        for (int i=0;i<size;i++){
            out[i]=(input[i]+min)/sum;
        }
        return out;

    }

    public static int Network(double[][] input){
        double[] Conv_out = new double[1024];
        double[] Apool_out1 = new double[1024];
        double[] Apool_out2 = new double[1024];
        double[] BN_out = new double[1024];
        double[] Act_out = new double[1024];
        double[] Mpool_out = new double[256];
        double[] FC1_out = new double[32];
        double[] FC2_out = new double[12];
        double[] SM_out = new double[12];
        double BNT= 3337025.75;

        int maxClass = 0;


        Apool_out1 = Avg_Pool(input[0], 4);
        Apool_out2 = Avg_Pool(input[1], 4);
        Conv_out = Conv(Apool_out1, Apool_out2, WC);
//        BN_out = Batch_Normalize(Conv_out, BN[0],BNT);
        Act_out = reLU(Conv_out);
        Mpool_out = Max_Pool(Act_out, 4);
        FC1_out = Fully_Connected(Mpool_out, WF1, BF1, 32);
        FC1_out = reLU(FC1_out);
        FC2_out = Fully_Connected(FC1_out, WF2, BF2, 12);
        SM_out = Soft_Max(FC2_out);

        double maxSig=0;


        for(int k=0;k<12;k++){
            if(SM_out[k]>maxSig){
                maxSig=SM_out[k];
                maxClass=k+1;
            }
        }
        return maxClass;
    }
}
