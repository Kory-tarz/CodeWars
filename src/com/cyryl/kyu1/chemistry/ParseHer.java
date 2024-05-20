package com.cyryl.kyu1.chemistry;

import java.util.Map;

public class ParseHer {

    //                            Number      :   1       2      3...
    final static private String[] RADICALS = {"meth", "eth", "prop", "but", "pent", "hex", "hept", "oct", "non", "dec", "undec", "dodec", "tridec", "tetradec", "pentadec", "hexadec", "heptadec", "octadec", "nonadec"};
    final static private String[] MULTIPLIERS = {"di", "tri", "tetra", "penta", "hexa", "hepta", "octa", "nona", "deca", "undeca", "dodeca", "trideca", "tetradeca", "pentadeca", "hexadeca", "heptadeca", "octadeca", "nonadeca"};
    final static private String[] SUFFIXES = {"ol", "al", "one", "oic acid", "carboxylic acid", "oate", "ether", "amide", "amine", "imine", "benzene", "thiol", "phosphine", "arsine"};
    final static private String[] PREFIXES = {"cyclo", "hydroxy", "oxo", "carboxy", "oxycarbonyl", "anoyloxy", "formyl", "oxy", "amido", "amino", "imino", "phenyl", "mercapto", "phosphino", "arsino", "fluoro", "chloro", "bromo", "iodo"};

    // Note that alkanes, alkenes alkynes, and akyles aren't present in these lists


    public ParseHer(String name) {
        // Do whatever you want here...
    }

    public Map<String, Integer> parse() {
        // Parse the name given as argument in the constructor and output the Map representing the raw formula
        return null;
    }
}
