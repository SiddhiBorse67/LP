
import java.io.*;
import java.util.*;

public class mp1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("macro_input.asm"));
        FileWriter mnt = new FileWriter("mnt.txt");
        FileWriter mdt = new FileWriter("mdt.txt");
        FileWriter pnt = new FileWriter("pnt.txt");
        FileWriter kpdt = new FileWriter("kpdt.txt");
        FileWriter ir = new FileWriter("intermediate.txt");
        Map<String, Integer> pntab = new LinkedHashMap<>();

        String line, Macroname = null;
        int mdtp = 1, paramno = 1, kpdtp = 0, pp = 0, kp = 0;
        boolean isMacro = false;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("MACRO")) {
                isMacro = true;
                line = br.readLine();
                Macroname = parts[0];
                parts = line.split("\\s+");
                pp = kp = 0;
                paramno = 1;
                if (parts.length <= 1) {
                    mnt.write(parts[0] + "\t" + pp + "\t" + kp + "\t" + mdtp + "\t" + (kp == 0 ? kpdtp : (kpdtp + 1))
                            + "\n");
                    continue;
                }
                for (int i = 0; i < parts.length; i++) {
                    String param = parts[i].replaceAll("[&,]", "");
                    if (param.contains("=")) {
                        kp++;
                        String[] keywordparam = param.split("=");
                        pntab.put(keywordparam[0], paramno++);
                        if (keywordparam.length == 2) {
                            kpdt.write(keywordparam[0] + "\t" + keywordparam[1] + "\n");
                        } else {
                            kpdt.write(keywordparam[0] + "\t" + "-" + "\n");
                        }
                    } else {
                        pntab.put(param, paramno++);
                        pp++;
                    }
                }
                mnt.write(
                        parts[0] + "\t" + pp + "\t" + kp + "\t" + mdtp + "\t" + (kp == 0 ? kpdtp : (kpdtp + 1)) + "\n");
                kpdtp = kpdtp + kp;

            } else if (parts[0].equalsIgnoreCase("MEND")) {
                mdt.write(line + "\n");
                mdtp++;
                writePNT(pnt, Macroname, pntab);
                pntab.clear();
                isMacro = false;

            } else if (isMacro) {
                for (String part : parts) {
                    if (part.contains("&")) {
                        String param = part.replaceAll("[&,]", "");
                        mdt.write("(p," + pntab.get(param) + ")\t");
                    } else {
                        mdt.write(part + "\t");
                    }
                }
                mdt.write("\n");
                mdtp++;
            } else {
                ir.write(line + "\n");
            }
        }
        br.close();
        mdt.close();
        mnt.close();
        ir.close();
        pnt.close();
        kpdt.close();
        System.out.println("Macro Pass1 Processing done. :)");

    }

    private static void writePNT(FileWriter pnt, String Macroname, Map<String, Integer> pntab) throws IOException {
        pnt.write(Macroname + ":\t");
        for (String param : pntab.keySet()) {
            pnt.write(param + "\t");
        }
        pnt.write("\n");
    }
}
