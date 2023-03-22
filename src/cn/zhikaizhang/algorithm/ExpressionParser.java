package cn.zhikaizhang.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Expression analyzer
 */
public class ExpressionParser {


    /**
     * Ανάλυση εκφράσεων σε πίνακες γενικευμένων τελεστών
     */
    public static List<Unit> parse(String expression) throws ExpressionIllegalException {

        String s = "#" + expression + "#";

        List<Unit> units = new ArrayList<Unit>();
        /**
         * Εάν μια μονάδα είναι σύμβολο πολλαπλασιασμού ή διαίρεσης ή αριστερή παρένθεση ή σύμβολο πηγής
         * ή ^ ή E, η σημαία τίθεται σε true, διαφορετικά τίθεται σε false.
         * Εάν μια μονάδα είναι + και η ένδειξη είναι αληθής, το σύμβολο + σημαίνει θετικό πρόσημο.
         * Αν μια μονάδα είναι - και η σημαία είναι αληθής, το σύμβολο - σημαίνει αρνητικό σύμβολο.
         */
        boolean flag = true;

        for(int i = 0; i < s.length(); i++){

            char c = s.charAt(i);
            if(c == '∞' || c == 'N' || c == 'p' ||c == 'e' || c >= '0' && c <= '9'){   //Αριθμός εργασιών
                flag = false;
                if(s.charAt(i) == '∞'){   //Operand, ∞ infinity, για την περίπτωση όπου το αποτέλεσμα της τελευταίας πράξης είναι άπειρο
                    // και η πράξη συνεχίζεται

                    units.add(new Unit(Double.POSITIVE_INFINITY));
                }
                else if(s.charAt(i) == 'N'){   //Ο τελεστής, NaN, υποδηλώνει ένα απροσδιόριστο αποτέλεσμα μιας πράξης, π.χ. 0/0

                    if(extract(i, 3, s).equals("NaN")){
                        units.add(new Unit(Double.NaN));
                        i += 2;
                    }else{
                        throw new ExpressionIllegalException();
                    }

                }
                else if(s.charAt(i) == 'p'){

                    if(extract(i, 2, s).equals("pi")){
                        units.add(new Unit(Math.PI));
                        i += 1;
                    }else{
                        throw new ExpressionIllegalException();
                    }
                }
                else if(s.charAt(i) == 'e'){

                    units.add(new Unit(Math.E));
                }
                else if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){   //Αριθμός εργασιών

                    double val = s.charAt(i) - '0';
                    boolean hasPoint = false;   //Εάν εμφανίζεται δεκαδικό σημείο
                    double level = 0.1;        //Μονάδες του κλασματικού μέρους
                    int k;
                    for(k = i+1; k < s.length(); k++){
                        if(s.charAt(k) >= '0' && s.charAt(k) <= '9'){
                            if(hasPoint){
                                val = val + (s.charAt(k) - '0') * level;
                                level *= 0.1;
                            }else{
                                val = val * 10 + (s.charAt(k) - '0');
                            }
                        }else if(s.charAt(k) == '.'){
                            if(hasPoint){
                                throw new ExpressionIllegalException();
                            }
                            hasPoint = true;
                        }else{
                            break;
                        }
                    }
                    i = k-1;
                    units.add(new Unit(val));

                    //Οι αριθμοί συνδέονται άμεσα με το π, το e και τη συνάρτηση,
                    // συμπληρωμένοι με το πρόσημο του πολλαπλασιασμού.
                    if("pesctl".contains(String.valueOf(s.charAt(i+1)))){
                        units.add(new Unit(Unit.Type.MULTIPLY));
                    }
                }
            }
            else{      //Χειριστές
                if(c == '-' && flag){
                    flag = false;
                    units.add(new Unit(Unit.Type.NEGATIVE));
                }else if(c == '+' && flag){
                    flag = false;
                    units.add(new Unit(Unit.Type.POSITIVE));
                }else{
                    switch(c){
                        case '+':
                            units.add(new Unit(Unit.Type.ADD));
                            break;
                        case '-':
                            units.add(new Unit(Unit.Type.SUBSTRACT));
                            break;
                        case 's':
                            if(extract(i, 4, s).equals("sin(")){
                                units.add(new Unit(Unit.Type.SIN));
                                i += 2;
                            }else if(extract(i, 5, s).equals("sqrt(")){
                                units.add(new Unit(Unit.Type.SQRT));
                                i += 3;
                            }else{
                                throw new ExpressionIllegalException();
                            }
                            break;
                        case 'c':
                            if(extract(i, 4, s).equals("cos(")){
                                units.add(new Unit(Unit.Type.COS));
                                i += 2;
                            }else{
                                throw new ExpressionIllegalException();
                            }
                            break;
                        case 't':
                            if(extract(i, 4, s).equals("tan(")){
                                units.add(new Unit(Unit.Type.TAN));
                                i += 2;
                            }else{
                                throw new ExpressionIllegalException();
                            }
                            break;
                        case 'l':
                            if(extract(i, 3, s).equals("ln(")){
                                units.add(new Unit(Unit.Type.LN));
                                i += 1;
                            }else if(extract(i, 4, s).equals("log(")){
                                units.add(new Unit(Unit.Type.LOG));
                                i += 2;
                            }else{
                                throw new ExpressionIllegalException();
                            }
                            break;
                        case '*':
                            units.add(new Unit(Unit.Type.MULTIPLY));
                            break;
                        case '/':
                            units.add(new Unit(Unit.Type.DIVIDE));
                            break;
                        case 'E':
                            units.add(new Unit(Unit.Type.EE));
                            break;
                        case '^':
                            units.add(new Unit(Unit.Type.POWER));
                            break;
                        case '!':
                            units.add(new Unit(Unit.Type.FACTORIAL));
                            break;
                        case '(':
                            units.add(new Unit(Unit.Type.LEFT_BRACKET));
                            break;
                        case ')':
                            units.add(new Unit(Unit.Type.RIGHT_BRACKET));
                            break;
                        case '#':
                            units.add(new Unit(Unit.Type.START_STOP_SIGN));
                            break;
                        case '.':
                        default:
                            throw new ExpressionIllegalException();
                    }
                    flag = (c == '*' || c == '/' || c == '^' || c == '(' || c == '#' || c == 'E');
                }
            }
        }

        return units;
    }

    private static String extract(int i, int num, String s) throws ExpressionIllegalException{
        if(i+num-1 > s.length()-1){
            throw new ExpressionIllegalException();
        }
        return s.substring(i, i+num);
    }

}
