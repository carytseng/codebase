package cn.oj.codebase.alg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName MergeArrray.java
 * @Description TODO
 * @createTime 2021年12月02日 08:49:00
 */
public class MergeArrray {

    public static class Data {
        String start;
        String end;

        public Data(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<Data> list = new ArrayList<>();
        Data a = new Data("2021-11-08T13:00:03Z", "2021-11-08T13:00:09Z");
        Data b = new Data("2021-11-08T13:00:09Z", "2021-11-08T13:05:14Z");
        Data c = new Data("2021-11-08T13:10:17Z", "2021-11-08T13:15:21Z");
        Data d = new Data("2021-11-08T13:15:21Z", "2021-11-08T13:20:25Z");
    /*    Data e = new Data("08", "10");
        Data f = new Data("13", "14");
        Data g = new Data("14", "17");*/
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
       /* list.add(e);
        list.add(f);
        list.add(g);*/

        List<Data> nlist = new ArrayList<>();
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            Data pp = new Data(list.get(i).getStart(), list.get(i).getEnd());
            if(i==list.size()-1){
                nlist.add(pp);
            }
            for (int j = i + 1; j <list.size(); j++) {

                if(pp.getEnd().equals(list.get(j).getStart())){
                    pp.setEnd(list.get(j).getEnd());
                }else{
                    nlist.add(pp);
                    i= j-1;
                    break;
                }
            }

        }
        System.out.println(nlist);
    }

}
