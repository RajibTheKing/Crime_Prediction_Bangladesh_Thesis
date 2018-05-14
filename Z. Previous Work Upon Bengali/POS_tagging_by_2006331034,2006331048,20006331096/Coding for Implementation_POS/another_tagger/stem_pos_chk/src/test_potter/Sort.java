package test_potter;
public class Sort {
    public Sort(){
    }

    public String[] sort(String[] wordList , int[] freq){
        int n = wordList.length;
        for(int i = 0;i<n-1;i++){
            for(int j = i+1;j<n;j++){
                if(freq[i]<freq[j]){
                   int tf = freq[j];
                   freq[j]= freq[i];
                   freq[i] = tf;
                   String ts = wordList[j];
                   wordList[j] = wordList[i];
                   wordList[i]= ts;
                }
            }
        }
        return wordList;
    }
}
