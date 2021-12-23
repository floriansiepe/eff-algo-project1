public class BinCo {

    public int binCof (int n, int k){
        if (k == 0 || k == n || n<k){
            return 0;
        }
        return binCof(n-1, k-1)+binCof(n-1,k);
    }
}
