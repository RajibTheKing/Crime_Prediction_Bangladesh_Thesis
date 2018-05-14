
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.Integer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class TestCharReplace {
    // String summary = "";

    public void TestCharReplace() {
    }
    HashMap hm_fcount = new HashMap();

    public void kar_replace(String inputFilePath, String outputFilePath) {
        try {

            String str, token;

            FileInputStream fis = new FileInputStream(inputFilePath);
            //FileInputStream fis = new FileInputStream("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/bbbb_final.txt.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader buf = new BufferedReader(isr);

            FileOutputStream fos = new FileOutputStream(outputFilePath);
            //FileOutputStream fos = new FileOutputStream("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/freq_count.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos ,"UTF-8");
            BufferedWriter bwr = new BufferedWriter(osw);



            while ((str = buf.readLine()) != null) {
                //System.out.print(str);
//                if(str.indexOf('+') == -1)
//                    return;
                str = str.substring(str.indexOf('+') + 1);
                if(str.length()==0)
                    break;
                //System.out.println(str);
                str = replace(str);     // Replace func. calld
//                else
//                {
//                    System.out.println("-----------"+ str);
//                return;
//                }
                count_freq(str);         // Frequency count func called


            }

            String summary = getFreq();
            bwr.write(summary);
            bwr.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFreq() {
        String key1 = "", key2 = "", summary = "";
        Iterator it1 = hm_fcount.keySet().iterator();
        while (it1.hasNext()) {
            key1 = it1.next().toString();
            HashMap get_val = new HashMap((HashMap) hm_fcount.get(key1));
            Iterator it2 = get_val.keySet().iterator();
            int sum = 0, max = 0;
            String maxSuffix = null;
            while (it2.hasNext()) {
                key2 = it2.next().toString();
                int val = new Integer(get_val.get(key2).toString());
                sum += val;
                if (val > max) {
                    max = val;
                    maxSuffix = key2;
                }
                //System.out.println(key1 +" "+ key2 + " "+ val);
                //          Percentance calculation


            }

            //System.out.println(key1 + " " + maxSuffix + " " + max*100/sum);
            double f = (double) max / sum;
            //String maxR = ""+f;
            summary += key1 + " " + maxSuffix + " " + String.format("%.4g%n", f);
        }
        return summary;
    }

    public String count_freq(String str) {
        String summary = "";

        StringTokenizer tokenizer = new StringTokenizer(str.trim());
        String key1 = tokenizer.nextToken().trim();
        String key2 = tokenizer.nextToken().trim().toLowerCase();

        if (hm_fcount.containsKey(key1)) {
            HashMap pos_c = new HashMap((HashMap) hm_fcount.get(key1));
            if (pos_c.containsKey(key2)) {

                String freq = pos_c.get(key2).toString();
                int freq_value = Integer.parseInt(freq) + 1;
                pos_c.put(key2, freq_value);
                hm_fcount.put(key1, pos_c);
                //System.out.println(key1 + " " + key2 + " " + freq_value);
            } else {
                pos_c.put(key2, 1);
                hm_fcount.put(key1, pos_c);
            }


        } else {
            HashMap pos_c = new HashMap();
            pos_c.put(key2, 1);
            hm_fcount.put(key1, pos_c);
        }




        return summary;


        //System.out.println(" ---"+hm_fcount.size());
    }

    public String replace(String str) {
        if (str.charAt(0) == 'ি') {
            str = str.replace('ি', 'ই'); // Replacing E-kar
        } else if (str.charAt(0) == 'া') {
            str = str.replace('া', 'আ'); // Replacing A-kar
        } else if (str.charAt(0) == 'ী') {
            str = str.replace('ী', 'ঈ'); // Replacing Derghoi-kar
        } else if (str.charAt(0) == 'ু') {
            str = str.replace('ু', 'উ'); // Replacing U-kar
        } else if (str.charAt(0) == 'ূ') {
            str = str.replace('ূ', 'ঊ'); // Replacing DeerU-kar
        } else if (str.charAt(0) == 'ো') {
            str = str.replace('ো', 'ও'); // Replacing U-kar
        } else if (str.charAt(0) == 'ে') {
            str = str.replace('ে', 'এ'); // Replacing e-kar
        }
        return str;
    }
}
