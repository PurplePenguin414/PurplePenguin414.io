// Delta College - CST 283 - Klingler                       
// This program use of the ArrayList class for an array of fractions

import java.util.ArrayList;
import java.util.Collections; 
import javax.swing.JOptionPane;

public class FractionList
{
   public static void main(String[] args)
   {
      int num[] = { 5, 6, 3,  8,  2};
      int den[] = {10, 7, 9, 12, 10};
   
      // Create an ArrayList to store Strings
      ArrayList<Rational> fractions = new ArrayList<Rational>();
      
      // Add fractions to list
      for (int i = 0; i < num.length; i++)
      {
         fractions.add(new Rational(num[i],den[i]));
      }

      // Display the items in list 
      String output = "";
      for (Rational aFraction : fractions)
         output += aFraction.toString() + "\n";
      JOptionPane.showMessageDialog(null,"My Fractions:\n" + output);     

   }
}
