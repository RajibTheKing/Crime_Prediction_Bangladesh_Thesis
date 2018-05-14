package test_potter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        //CheckSuffix cs = new CheckSuffix();
         //String result_pos = "";
        //GuessPoS  g=new GuessPoS();

//
//        EvaluatePoS e = new EvaluatePoS();
//        e.kar_replace("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/pos_stem_stemed.txt", "C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/final_count.txt");
//

       try {
           // FileOutputStream fos = new FileOutputStream("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/pos_stem_stemed.txt");
             FileOutputStream fos = new FileOutputStream("E://ThesisResource_pos/another_tagger/output/pos_stem_stemed.txt");
            OutputStreamWriter ows = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bOutbuff = new BufferedWriter(ows);

            //Stemmer d = new Stemmer();
            CheckSuffix d = new CheckSuffix();
            EvaluatePoS ep = new EvaluatePoS();
            //ep.kar_replace("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/26.02.11/pos_stem_stemed.txt", "C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/final_count.txt");
           ep.kar_replace("E://ThesisResource_pos/another_tagger/26/pos_stem_stemed.txt", "E://ThesisResource_pos/another_tagger/k_p_b/final_count.txt");

            if (!d.init()) {
            System.out.println("Stem Failed :");
            //System.exit(0);
        }
            Stemmer s = new Stemmer(d);
            String str;
            int strt;
                    //            if (d.init()) {
                    //                System.out.println("initialized");
                    //            }

           // File directory = new File("C:/Users/rakib/Desktop/Thesis-15.01.11/Temporary/Sports");
            File directory = new File("E://ThesisResource_pos/another_tagger/input");
            File childfile[] = directory.listFiles();
            for (File f : childfile) {
                if (f.isFile()) {
                    try {

                        //FileInputStream fis = new FileInputStream("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/test/unique22.txt");

                        FileInputStream fis = new FileInputStream(f);
                        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                        BufferedReader br = new BufferedReader(isr);
                        strt = br.read();

                        //FileOutputStream stemmed = new FileOutputStream("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/Word_PosNewDstemmed.txt");
                         FileOutputStream stemmed = new FileOutputStream("E://ThesisResource_pos/another_tagger/k_p_b/Word_PosNewDstemmed.txt");
                        OutputStreamWriter stemOut = new OutputStreamWriter(stemmed, "UTF-8");
                        BufferedWriter stemOutbuff = new BufferedWriter(stemOut);

                        //FileOutputStream notStemmed = new FileOutputStream("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_b/Word_PosNotStemmed.txt");
                          FileOutputStream notStemmed = new FileOutputStream("E://ThesisResource_pos/another_tagger/k_p_b/Word_PosNotStemmed.txt");
                        OutputStreamWriter notStemmedOut = new OutputStreamWriter(notStemmed);
                        BufferedWriter notstemOutbuff = new BufferedWriter(notStemmedOut);

                        //FileOutputStream stemndic = new FileOutputStream("C:/Users/rakib/Desktop/Thesis-15.01.11/Test/k_p_bWord_PosStemNotContein.txt");
                         FileOutputStream stemndic = new FileOutputStream("E://ThesisResource_pos/another_tagger/k_p_b/k_p_bWord_PosStemNotContein.txt");
                        OutputStreamWriter outstemndic = new OutputStreamWriter(stemndic);
                        BufferedWriter stemOutdicbuff = new BufferedWriter(outstemndic);
                        //out.write(sout);
                        //out.close();
                        
/*      For multiple file Reading*********************************
                        while ((str = br.readLine()) != null) {
                            if (str.length() != 0) {
                                
                                int c_position = str.indexOf(':');
                                if (c_position > 0) {
                                    str = str.substring(0, c_position);
                                }

                                str = str.trim();
                                //System.out.println(str);

                                if (d.checkDictionary(str))// check whether it is in dictionary or not
                                {
                                    System.out.println("found");
                                } else {
                                    String str2 = s.stem(str);//attempt to stem if word is not in dictionary
                                    if (str == str2) {// tried to stemm but failure
                                        System.out.println("Not Stemmed ---" + str2);
                                        //str = strt + str;
                                        notstemOutbuff.append("\n" + str);

                                        //  Again check with Dictionary by adding RULES

                                    } else if (d.checkDictionary(str2)) {// stemmed successful and stemmed word found in dictionary
                                        System.out.println("matched: " + str + " --" + str2);
                                        //str2 +=  str2;
                                        stemOutbuff.append("\n" + str + " " + str2);
                                    } else {// stemmed but not in dictionary
                                        //System.out.println(str);
                                        str2 = strt + str2;
                                        stemOutdicbuff.append("\n" + str + " " + str2);
                                    }
                                }
                            }
                        }*/
                        
                        while ((str = br.readLine()) != null) {
                            if (str.length() != 0) {

                                StringTokenizer tok = new StringTokenizer(str, " \n।’!?,-");

                                while (tok.hasMoreTokens()) {
                                    str = tok.nextToken();
                                    System.out.println(str);
//                                    if (str.charAt(0) > 'অ' && str.charAt(0) <= 'য়') {
//
//                                    }


                                    str = str.trim();
                                    //System.out.println(str);

                                    if (d.checkDictionary(str))// check whether it is in dictionary or not
                                    {
                                        System.out.println("fund ---- "+str + d.result_pos);
                                        //String t=s.stem(str);
                                       // result_pos = cs.dictionary_tree.get(str).toString();
                                        s.result += str+"/"+ d.result_pos + " ";

                                    } else {
                                        String result[] = new String[2];
                                        //String str2 = s.stem(str);//attempt to stem if word is not in dictionary
                                        result = s.stem(str).split(" ", 2);
                                        String str2 = result[0];

                                        // System.out.println("result"+result[1]);
                                        //System.out.println("str2"+str2);
                                      //  String stemPart = result[1];//
                                        if (str == null ? str2 == null : str.equals(str2)) {// tried to stemm but failure
                                            s.result += str+"/"+"unknown" + " ";
                                            String suff = "";
                                            System.out.println("Not Stemmed ---" + str2);
                                            //String suff = d.getSuffBivokti(str2);
                                            //if(suuf)
                                            String[] pos;

//                                            if ((suff = d.getSuffBivokti(str2)) != null) {
//                                                suff = ep.replace(suff);
//                                                pos = ep.check(suff);
//                                            } else if ((suff = d.stemProtoy(str2)) != null) {
//                                                suff = ep.replace(suff);
//                                                pos = ep.check(suff);
//                                            } else if ((suff = d.stemKal(str2)) != null) {
//                                                suff = ep.replace(suff);
//                                                pos = ep.check(suff);
//                                            } else if ((suff = d.stemUposorgo(str2)) != null) {
//                                                suff = ep.replace(suff);
//                                                pos = ep.check(suff);
//                                            } else {
//                                                pos = new String[0];
//                                            }
                                            System.out.println("suffix :---" + suff + "::::");

//                                            for (int i = 0; i < pos.length; i++) {
//                                                String string = pos[i];
//                                                System.out.println(string);
//
//                                            }


                                            //str = strt + str;
                                            notstemOutbuff.append("\n" + str);

                                            //  Again check with Dictionary by adding RULES

                                        } else if (d.checkDictionary(str2)) {// stemmed successful and stemmed word found in dictionary
                                            System.out.println("matched: " + str + " --" + str2);
                                            //str2 +=  str2;
                                            result[1] = ep.replace(result[1]);
                                            ep.update(str2, result[1]);
                                            stemOutbuff.append("\n" + str + " " + str2);

                                             //result += str + " " + str1 + "+" + cs.stemmed + " " + cs.result_pos + "\n";
                                             
                                        } else {// stemmed but not in dictionary
                                            System.out.println(str);
                                            str2 = strt + str2;
                                            stemOutdicbuff.append("\n" + str + " " + str2);
                                        }
                                    }
                                }
                            }
                             s.result +="\n";
                        }
                        notstemOutbuff.close();
                        stemOutbuff.close();
                        stemOutdicbuff.close();

                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            bOutbuff.write(s.result);
            bOutbuff.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
