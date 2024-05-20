package com.cyryl.kyu1.chemistry;

import static org.junit.Assert.*;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



@RunWith(Parameterized.class)
public class ParseHerTest {


    /*      String arrays:
     *          { molecule drawing
     *            name
     *            raw formula (string representation of the map)
     *            describe message
     *            additional message       */
    final static private String[][] FIXED_TESTS = {

/*[ 0]*/        {"\nCH4\n",
            "methane",
            "{C: 1, H: 4}",
            "Simple chains / impact of the number of C",
            ""},

/*[ 1]*/        {"\nCH3-CH3\n",
            "ethane",
            "{C: 2, H: 6}",
            "",
            ""},

/*[ 2]*/        {"\nCH3-CH2-CH2-CH3\n",
            "butane",
            "{C: 4, H: 10}",
            "",
            ""},

/*[ 3]*/        {"\nCH3-CH2-CH2-CH2-CH2-CH2-CH2-CH2-CH2-CH3\n",
            "decane",
            "{C: 10, H: 22}",
            "",
            ""},

/*[ 4]*/        {"\nCH3-CH2-CH2-CH2-CH2-CH2-CH2-CH3\n",
            "octane",
            "{C: 8, H: 18}",
            "Simple ramifications",
            "REFERENCE: C8H18 (octane)"},

/*[ 5]*/        {"\n        CH2-CH3\n        |\nCH3-CH2-CH-CH2-CH2-CH3\n",
            "3-ethylhexane",
            "{C: 8, H: 18}",
            "",
            "One ramification"},

/*[ 6]*/        {"\n   CH3  CH2-CH3\n    |   |\nCH3-CH-CH-CH2-CH3\n",
            "3-ethyl-2-methylpentane",
            "{C: 8, H: 18}",
            "",
            "Two ramifications"},

/*[ 7]*/        {"\n        CH2-CH3\n        |\nCH3-CH2-C-CH2-CH3\n        |\n        CH3\n",
            "3-ethyl-3-methylpentane",
            "{C: 8, H: 18}",
            "",
            "Two ramifications on the same C"},

/*[ 8]*/        {"\n        CH3\n        |\nCH3-CH2-C-CH2-CH2-CH3\n        |\n        CH3\n",
            "3,3-dimethylhexane",
            "{C: 8, H: 18}",
            "",
            "Handle multipliers"},

/*[ 9]*/        {"\n CH3   CH3\n   \\   /\nCH3-C-C-CH3\n   /   \\\n CH3   CH3 \n",
            "2,2,3,3-tetramethylbutane",
            "{C: 8, H: 18}",
            "",
            "Handle multipliers"},

/*[10]*/        {"\n CH2-CH2-CH2-CH2\n |           |\n CH2-CH2-CH2-CH2\n",
            "cyclooctane",
            "{C: 8, H: 16}",
            "Effect of cycles and multiple bounds",
            "REFERENCE: C8H16 (cyclooctane)"},

/*[11]*/        {"\n CH2-CH2-CH-CH2-CH3\n |       |\n CH2-CH2-CH2  \n",
            "1-ethylcyclohexane",
            "{C: 8, H: 16}",
            "",
            "One cycle of size 6 and one ramification"},

/*[12]*/        {"\n CH2-CH-CH2-CH3\n |   |\n CH2-C-CH3\n     |\n     CH3 \n",
            "1-ethyl-2,2-dimethylcyclobutane",
            "{C: 8, H: 16}",
            "",
            "One cycle of size 4 and several ramifications"},

/*[13]*/        {"\nCH2=CH-CH2-CH2-CH2-CH2-CH2-CH3\n",
            "oct-1-ene",
            "{C: 8, H: 16}",
            "",
            "One double bound: at an extremity"},

/*[14]*/        {"\nCH3-CH2-CH=CH-CH2-CH2-CH2-CH3 \n",
            "oct-3-ene",
            "{C: 8, H: 16}",
            "",
            "One double bound: anywhere in the chain"},

/*[15]*/        {"\nCH2=CH-CH2-CH2-CH2-CH2-CH2-CH3\n",
            "octene",
            "{C: 8, H: 16}",
            "",
            "One double bound: elision of the position '-1-'"},

/*[16]*/        {"\nCH3-CH=CH-CH2-CH=CH-CH2-CH3\n",
            "oct-2,5-diene",
            "{C: 8, H: 14}",
            "Effect of mutliple bounds and cycles, part 2",
            "Double bounds"},

/*[17]*/        {"\nCH{=}C-CH2-CH2-CH2-CH2-CH2-CH3      \"{=}\" used as triple bound (should be 3 lines)\n",
            "oct-1-yne",
            "{C: 8, H: 14}",
            "",
            "Triple bound: at an extremity"},

/*[18]*/        {"\nCH3-C{=}C-CH2-CH2-CH2-CH2-CH3\n",
            "oct-2-yne",
            "{C: 8, H: 14}",
            "",
            "Triple bound: in the chain"},

/*[19]*/        {"\nCH{=}C-CH2-CH2-CH2-CH2-CH2-CH3\n",
            "octyne",
            "{C: 8, H: 14}",
            "",
            "Triple bound: elision of the position"},

/*[20]*/        {"\n CH2-CH2-CH-CH2-CH3\n |       |\n CH=CH-CH2   \n",
            "3-ethylcyclohexene",
            "{C: 8, H: 14}",
            "",
            "Mix of cycles and multiple bounds"},

/*[21]*/        {"\nCH3-CH2-CH2-CH2-CH3\n",
            "pentane",
            "{C: 5, H: 12}",
            "Simple functions: oxygen",
            "REFERENCE: C5H12 (pentane)"},

/*[22]*/        {"\nCH3-CH2-CH2-CH2-CH2-OH\n",
            "pentanol",
            "{C: 5, H: 12, O: 1}",
            "",
            ""},

/*[23]*/        {"\n    OH\n    |\nCH3-CH-CH2-CH2-CH3\n",
            "pentan-2-ol",
            "{C: 5, H: 12, O: 1}",
            "",
            ""},

/*[24]*/        {"\n    OH     OH\n    |      |\nCH3-CH-CH2-CH-CH3\n",
            "pentan-2,4-diol",
            "{C: 5, H: 12, O: 2}",
            "",
            ""},

/*[25]*/        {"\nCH3-CH2-CH2-CH2-CH=O\n",
            "pentanal",
            "{C: 5, H: 10, O: 1}",
            "",
            ""},

/*[26]*/        {"\n    O\n    ||\nCH3-C-CH2-CH2-CH3\n",
            "pentan-2-one",
            "{C: 5, H: 10, O: 1}",
            "",
            ""},

/*[27]*/        {"\nO=CH-CH2-CH2-CH2-CH=O\n",
            "pentandial",
            "{C: 5, H: 8, O: 2}",
            "",
            ""},

/*[28]*/        {"\n    O     O\n    ||    ||\nCH3-C-CH2-C-CH3\n",
            "pentan-2,4-dione",
            "{C: 5, H: 8, O: 2}",
            "",
            ""},

/*[29]*/        {"\nCH3-CH2-CH2-CH2-CH3\n",
            "pentane",
            "{C: 5, H: 12}",
            "Simple functions: halogens",
            "REFERENCE: C5H12 (pentane)"},

/*[30]*/        {"\nCH3-CH2-CH2-CH2-CH2-F\n",
            "1-fluoropentane",
            "{C: 5, H: 11, F: 1}",
            "",
            ""},

/*[31]*/        {"\n    Cl\n    |\nCH3-CH-CH2-CH2-CH3\n",
            "2-chloropentane",
            "{C: 5, H: 11, Cl: 1}",
            "",
            ""},

/*[32]*/        {"\n    Cl\n    |\nCH3-CH-CH2-CH2-CH2-Br\n",
            "1-bromo-4-chloropentane",
            "{C: 5, H: 10, Br: 1, Cl: 1}",
            "",
            ""},

/*[33]*/        {"\nCH3-CH2-CH2-CH2-CH2-CH3\n",
            "hexane",
            "{C: 6, H: 14}",
            "Simple functions: nitrogen",
            "REFERENCE: C6H14 (hexane)"},

/*[34]*/        {"\nCH3-CH2-CH2-CH2-CH2-CH2-NH2\n",
            "hexylamine",
            "{C: 6, H: 15, N: 1}",
            "",
            ""},

/*[35]*/        {"\nCH3-CH2-CH2-CH2-NH-CH2-CH3\n",
            "butylethylamine",
            "{C: 6, H: 15, N: 1}",
            "",
            ""},

/*[36]*/        {"\n            CH3\n            |\nCH3-CH2-CH2-N-CH2-CH3\n",
            "ethylmethylpropylamine",
            "{C: 6, H: 15, N: 1}",
            "",
            ""},

/*[37]*/        {"\nN(CH2-CH3)3\n",
            "triethylamine",
            "{C: 6, H: 15, N: 1}",
            "",
            ""},

/*[38]*/        {"\nNH2-CH2-CH2-CH2-CH2-CH2-CH2-NH2\n",
            "hexan-1,6-diamine",
            "{C: 6, H: 16, N: 2}",
            "",
            "Alternative nomenclature: hexan-1,6-diamine"},

/*[39]*/        {"\n                    O\n                    ||\nCH3-CH2-CH2-CH2-CH2-C-NH2\n",
            "hexanamide",
            "{C: 6, H: 13, N: 1, O: 1}",
            "",
            "WARNING: amiDe, not amiNe, here!"},
    };





    final static private int[][] TEST_CONFIG = {
            {0, 4},       //  Simple chains / impact of the number of C
            {4, 10},      //  Simple ramifications
            {10, 16},     //  Effect of cycles and multiple bounds
            {16, 21},     //  Effect of mutliple bounds and cycles, part 2
            {21, 29},     //  Simple functions: oxygen
            {29, 33},     //  Simple functions: halogens
            {33, 40},     //  Simple functions: nitrogen
    };



    @Parameters
    public static Collection<Object[]> squareNumbers() {
        return Arrays.asList( IntStream.range(0, TEST_CONFIG.length)
                .mapToObj( i -> new Object[] {i})
                .toArray(Object[][]::new)
        );
    }


    private int testIdx;

    public ParseHerTest(int testIdx) { this.testIdx = testIdx; }



    final private static Pattern P_EXP = Pattern.compile("([a-zA-Z]+).+?(\\d+)");


    private static <T> void display(T o) { System.out.println(o); }



    private Map<String,Integer> parseExpToMap(String exp) {         // Convert the strings of the fixed tests as actual Maps

        Map<String,Integer> res = new HashMap<String,Integer>();

        Matcher m = P_EXP.matcher(exp);
        while (m.find())
            res.put(m.group(1), Integer.parseInt(m.group(2)));

        return res;
    }






    @Test
    public void doTestSuite() {

        int start = TEST_CONFIG[testIdx][0],
                end   = TEST_CONFIG[testIdx][1];

        for (int i = start ; i < end ; i++) {

            String draw  = FIXED_TESTS[i][0],
                    molec = FIXED_TESTS[i][1],
                    exp   = FIXED_TESTS[i][2],
                    desc  = FIXED_TESTS[i][3],
                    msg   = FIXED_TESTS[i][4];


            if (!desc.isEmpty()) {
                display("********************");
                display("     TEST GOAL:      ");
                display("********************\n");
                display(desc + "\n\n");
            }
            display("\n---------------------------\n");
            display(msg.isEmpty() ? molec : msg);
            display("---");
            display(draw);
            display("\""+molec+"\" should lead to: " + exp);

            Map<String,Integer> act = new ParseHer(molec).parse();

            assertEquals( parseExpToMap(exp), act );
        }
    }
}