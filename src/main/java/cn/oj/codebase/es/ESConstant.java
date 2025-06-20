package cn.oj.codebase.es;

import org.elasticsearch.client.RequestOptions;

public class ESConstant {

    private ESConstant() {
        throw new IllegalStateException("Utility class");
    }

    //就诊次数查询默认最大值
    public static final int VISIT_INNIT_SIZE = 100;
    //单次就诊多次记录查询默认最大值
    public static final int TIMES_INNIT_SIZE = 100;

    public static final  String ES_TYPE           = "_doc";
    public static final String PATIENT_DIMENSION = "patient_dimension";
    public static final  String VISIT_DIMENSION   = "visit_dimension";
    public static final  String VISIT_FLAG        = "visit_flag";

    public static final String TEXT_ALL         = "Text_All";
    public static final String PATIENT_TEXT_ALL = PATIENT_DIMENSION + "." + TEXT_ALL;
    public static final String VISIT_TEXT_ALL   = VISIT_DIMENSION + "." + TEXT_ALL;

    private static final String KEYWORD = "keyword";

    public static final String SUFFIX_KEYWORD = "." + KEYWORD;

    public static final String PAT_EMPI_INFO = "pat_emp_info";

    public static final RequestOptions REQUEST_OPTIONS = RequestOptions.DEFAULT;

    public static final String ANALYZER = "ik_max_word";


}
