package ru.list.sorfe.rzia.util;

import ru.list.sorfe.rzia.beans.TpIc;

class CommonUtil {
    static int ParseInt(String stdNumbered) {
        String strNumber = stdNumbered.replace("\u0000", "");
        if (strNumber.length() > 0) {
            try {
                return Integer.parseInt(strNumber);
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0;
    }

    static double ParseDouble(String stdNumbered) {
        String strNumber = stdNumbered.replace("\u0000", "");
        if (strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber.replace(",", "."));
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0;
    }
    static TpIc ConvertToTpIc(String s) {
        String input = s.replace("\u0000", "");
        String noSpaceStr = input.replaceAll("\\s", "");
        int       index = lowestIndexOf(noSpaceStr, "IC");
        String tp = (index > 0) ? noSpaceStr.substring(0, index) : s;
        String ic = (index > 0) ? noSpaceStr.substring(index + 2) : "";
        index = lowestIndexOf(tp, "P");
        String type =(index > 0) ? tp.substring(0, index+1) : s;
        String numb = (index > 0) ? tp.substring(index + 1) : "";
        return new TpIc(
                ParseInt(numb),
                TpIc.TypeOfStation.valueOf(type),
                ParseInt(ic));
    }

    static int lowestIndexOf(String property, String... candidates) {
        int i = -1;
        for (String candidate : candidates) {
            int candidateIndex = property.lastIndexOf(candidate);
            if (candidateIndex > 0) {
                i = (i != -1) ? Math.min(i, candidateIndex) : candidateIndex;
            }
        }
        return i;
    }
}
